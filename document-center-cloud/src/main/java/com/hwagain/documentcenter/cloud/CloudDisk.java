package com.hwagain.documentcenter.cloud;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hwagain.documentcenter.cloud.datacontract.*;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.util.StringUtil;

@Component
public class CloudDisk {
	static String X_SERVER_URL = "http://10.0.82.233";
	static String SERVER_USER = "laogc";
	static String SERVER_PASSWORD = "1zxcvbnm1";

	static String token = null;
	static String loginErrText = null;

	static ConfigInfo config;

	static {
		if (config == null)
			login(); // 用于调试，不从配置文件读取参数（没启动主程序，不能读取配置文件内容）
	}

	@PostConstruct
	static void init() {
		X_SERVER_URL = config.getUrl();
		SERVER_USER = config.getUsername();
		SERVER_PASSWORD = config.getPassword();
		login();
	}

	public static boolean isLoginSuccess() {
		return token != null;
	}

	public static String getLoginErrText() {
		return loginErrText;
	}

	@Autowired
	public void setConfigInfo(ConfigInfo configInfo) {
		CloudDisk.config = configInfo;
	}

	public static void uploadFile(String path, File file) throws CustomException {
		if (!file.exists() || !file.isFile())
			throw new CustomException("上传文件失败：文件不存在");

		List<FilePart> fileParts = FilePart.getFilePartsInfo(file);
		if (fileParts == null)
			throw new CustomException("上传文件失败：文件内容不能为空");

		uploadFile(path, fileParts);
	}

	public static void uploadFile(String path, String filename, byte[] fileData) throws CustomException {
		List<FilePart> fileParts = FilePart.getFilePartsInfo(filename, fileData);
		if (fileParts == null)
			throw new CustomException("上传文件失败：文件内容不能为空");

		uploadFile(path, fileParts);
	}

	private static void uploadFile(String path, List<FilePart> fileParts) {

		long size = 0;
		List<PartInfo> partsInfo = new ArrayList<PartInfo>();
		for (FilePart filePart : fileParts) {
			partsInfo.add(filePart.getPartInfo());
			size = size + filePart.getPartSize();
		}

		RequestUploadByPathResponse res = requestUploadByPath(path, size, false, partsInfo);
		String stat = res.getStat();
		if (!stat.equals("OK")) {
			// 如果已经存在
			if (stat.equals("ERR_FILE_ALREADY_EXIST"))
				return;

			String errMsg = "请求上传失败[" + stat + "] ";
			throw new CustomException(errMsg);
		} else {
			// 秒传
			if (res.getExisted() != null && res.getExisted()) {
				return;
			}
			for (FilePart filePart : fileParts) {
				UploadFilePartResponse uploadfile_res = uploadFilePart(res, filePart);
				String uploadfile_stat = uploadfile_res.getStat();
				if (!uploadfile_stat.equals("OK")) {
					// 如果已经存在
					if (uploadfile_stat == "ERR_FILE_ALREADY_EXIST")
						continue;

					String errMsg = "上传文件块失败[" + stat + "][" + filePart.partNumber + "] ";
					throw new CustomException(errMsg);
				} else {
					filePart.setPartCommitId(uploadfile_res.getPartCommitId());
				}
			}

			Boolean allUploaded = fileParts.stream().filter(a -> StringUtil.isNull(a.partCommitId))
					.collect(Collectors.toList()).size() == 0;

			if (allUploaded) {
				CommitUploadByPathRequest req = new CommitUploadByPathRequest();
				req.setToken(token);
				req.setFileUploadId(res.getFileUploadId());
				req.setPath(path);
				req.setSize(size);

				String[] partCommitIds = new String[fileParts.size()];
				for (int i = 0; i < fileParts.size(); i++) {
					partCommitIds[i] = fileParts.get(i).getPartCommitId();
				}
				req.setPartCommitIds(partCommitIds);

				CommitUploadByPathResponse commit_res = commitUploadByPath(req);
				String commit_stat = commit_res.getStat();
				if (!commit_stat.equals("OK")) {
					// 如果已经存在
					if (commit_stat == "ERR_FILE_ALREADY_EXIST")
						return;

					String errMsg = "提交确认失败[" + stat + "] ";
					throw new CustomException(errMsg);
				} else
					return;
			} else {
				String errMsg = "文件数据块没有上传完";
				throw new CustomException(errMsg);
			}
		}
	}

	public static UploadFilePartResponse uploadFilePart(RequestUploadByPathResponse request, FilePart filePart) {
		String api_url = request.getNodes().get(0).getAddr() + "/uploadPart?fileUploadId=" + request.getFileUploadId()
				+ "&partNumber=" + filePart.getPartNumber() + "&partSha1=" + filePart.getPartSha1() + "&partSize="
				+ filePart.getPartSize();

		String result = HttpUtils.PostData(api_url, filePart.getData());
		UploadFilePartResponse res = JSONObject.parseObject(result, UploadFilePartResponse.class);

		return res;
	}

	public static CommitUploadByPathResponse commitUploadByPath(CommitUploadByPathRequest req) {
		String api_url = X_SERVER_URL + "/fs/api/commitUploadByPath";
		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		CommitUploadByPathResponse res = JSONObject.parseObject(result, CommitUploadByPathResponse.class);

		return res;
	}

	public static RequestUploadByPathResponse requestUploadByPath(String path, long size, Boolean overWrite,
			List<PartInfo> partsInfo) {
		String api_url = X_SERVER_URL + "/fs/api/requestUploadByPath";

		RequestUploadByPathRequest req = new RequestUploadByPathRequest();
		req.setToken(token);
		req.setGid(0);
		req.setPath(path);
		req.setSize(size);
		req.setPartsInfo(partsInfo);
		req.setOverWrite(overWrite);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		RequestUploadByPathResponse res = JSONObject.parseObject(result, RequestUploadByPathResponse.class);

		return res;
	}

	public static RequestUploadResponse requestUpload(String path, String name, long size, Boolean overWrite,
			List<PartInfo> partsInfo) {
		String api_url = X_SERVER_URL + "/fs/api/requestUpload";

		long dir_id = 0;
		// 创建目录
		MkdirByPathResponse dir = mkdirByPath(path);
		if (dir.getStat().equals("ERR_FILE_ALREADY_EXIST") || dir.getStat().equals("OK")) {
			FileInfoByPathResponse dirInfo = fileInfoByPath(path);
			// 如果存在
			if (dirInfo.getStat().equals("OK"))
				dir_id = dirInfo.getFileInfo().getFid();
		}

		RequestUploadRequest req = new RequestUploadRequest();
		req.setToken(token);
		req.setParent(dir_id);
		req.setGid(0);
		req.setName(name);
		req.setSize(size);
		req.setPartsInfo(partsInfo);
		req.setOverWrite(overWrite);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		RequestUploadResponse res = JSONObject.parseObject(result, RequestUploadResponse.class);

		return res;
	}

	static void login() {
		//已登录，跳过
		if (token != null)
			return;

		LoginResponse res = getToken(SERVER_USER, SERVER_PASSWORD);
		String stat = res.getStat();
		if (stat.equals("OK")) {
			token = res.getToken();
			loginErrText = null;

		} else {
			loginErrText = res.getErrText();
			System.out.println(loginErrText);
		}
	}

	public static SearchResponse search(SearchRequest req) {
		String api_url = X_SERVER_URL + "/search/api/search";

		req.setToken(token);
		req.setSpaceType("person");

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		System.out.println(result);
		SearchResponse res = JSONObject.parseObject(result, SearchResponse.class);

		return res;
	}

	public static GetDownloadUrlResponse getDownloadUrl(String path, int timeOutMinites) {
		long expires = System.currentTimeMillis() + timeOutMinites * 60 * 1000;
		return getDownloadUrl(path, expires);
	}

	public static GetDownloadUrlResponse getDownloadUrl(String path, long expires) {
		String api_url = X_SERVER_URL + "/fs/api/getDownloadUrl";

		long fid = -1;
		FileInfo fileInfo = fileInfoByPath(path).getFileInfo();
		if (fileInfo != null)
			fid = fileInfo.getFid();

		GetDownloadUrlRequest req = new GetDownloadUrlRequest();
		req.setToken(token);
		req.setGid(0);
		req.setFid(fid);
		req.setExpires(expires);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		GetDownloadUrlResponse res = JSONObject.parseObject(result, GetDownloadUrlResponse.class);

		return res;
	}

	public static RequestDownloadByPathResponse requestDownloadByPath(String path) {
		String api_url = X_SERVER_URL + "/fs/api/requestDownloadByPath";

		RequestDownloadByPathRequest req = new RequestDownloadByPathRequest();
		req.setToken(token);
		req.setGid(0);
		req.setPath(path);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		RequestDownloadByPathResponse res = JSONObject.parseObject(result, RequestDownloadByPathResponse.class);

		// 通过返回的下载地址下载的文件都是 0 字节，不知道什么原因
		return res;
	}

	public static InfoByFidResponse infoByFid(long fid) {
		String api_url = X_SERVER_URL + "/fs/api/infoByFid";

		InfoByFidRequest req = new InfoByFidRequest();
		req.setToken(token);
		req.setGid(0);
		req.setFid(fid);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		InfoByFidResponse res = JSONObject.parseObject(result, InfoByFidResponse.class);

		return res;
	}

	public static LoginResponse getToken(String username, String password) {

		String api_url = X_SERVER_URL + "/auth/api/nameLogin";

		LoginRequest req = new LoginRequest();
		req.setName(username);
		req.setPassword(password);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		LoginResponse res = JSONObject.parseObject(result, LoginResponse.class);

		return res;
	}

	public static ListByPathResponse listByPath(String path) {
		String api_url = X_SERVER_URL + "/fs/api/listByPath";

		ListByPathRequest req = new ListByPathRequest();
		req.setToken(token);
		req.setGid(0);
		req.setPath(path);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		ListByPathResponse res = JSONObject.parseObject(result, ListByPathResponse.class);

		return res;
	}

	public static FileInfoByPathResponse fileInfoByPath(String path) {
		String api_url = X_SERVER_URL + "/fs/api/fileInfoByPath";

		FileInfoByPathRequest req = new FileInfoByPathRequest();
		req.setToken(token);
		req.setGid(0);
		req.setPath(path);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		FileInfoByPathResponse res = JSONObject.parseObject(result, FileInfoByPathResponse.class);

		return res;
	}

	public static RenameByPathResponse renameByPath(String srcPath, String dstPath, Boolean overWrite) {
		String api_url = X_SERVER_URL + "/fs/api/renameByPath";

		RenameByPathRequest req = new RenameByPathRequest();
		req.setToken(token);
		req.setGid(0);
		req.setDstGid(0);
		req.setSrcPath(srcPath);
		req.setDstPath(dstPath);
		req.setOverWrite(overWrite);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		RenameByPathResponse res = JSONObject.parseObject(result, RenameByPathResponse.class);

		return res;
	}

	public static CopyByPathResponse copyByPath(String srcPath, String dstPath, Boolean overWrite) {
		String api_url = X_SERVER_URL + "/fs/api/copyByPath";

		CopyByPathRequest req = new CopyByPathRequest();
		req.setToken(token);
		req.setSrcPath("gid:0" + srcPath);
		req.setDstPath("gid:0" + dstPath);
		req.setOverWrite(overWrite);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		CopyByPathResponse res = JSONObject.parseObject(result, CopyByPathResponse.class);

		return res;
	}

	public static CopyResponse copy(long srcFid, long destParent, String newName, Boolean overWrite) {
		String api_url = X_SERVER_URL + "/fs/api/copy";

		CopyRequest req = new CopyRequest();
		req.setToken(token);
		req.setSrcGid(0);
		req.setDestGid(0);
		req.setSrcFid(srcFid);
		req.setDestParent(destParent);
		req.setOverWrite(overWrite);
		if (newName != null && newName.length() > 0)
			req.setNewName(newName);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		CopyResponse res = JSONObject.parseObject(result, CopyResponse.class);

		return res;
	}

	public static MkdirByPathResponse mkdirByPath(String path) {
		String api_url = X_SERVER_URL + "/fs/api/mkdirByPath";

		MkdirByPathRequest req = new MkdirByPathRequest();
		req.setToken(token);
		req.setGid(0);
		req.setPath(path);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		MkdirByPathResponse res = JSONObject.parseObject(result, MkdirByPathResponse.class);

		return res;
	}

	public static RmByPathResponse rmByPath(String path) {
		String api_url = X_SERVER_URL + "/fs/api/rmByPath";

		RmByPathRequest req = new RmByPathRequest();
		req.setToken(token);
		req.setGid(0);
		req.setPath(path);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		RmByPathResponse res = JSONObject.parseObject(result, RmByPathResponse.class);

		return res;
	}

	public static CreateShareResponse createShare(long fid, int type, long expires) {
		String api_url = X_SERVER_URL + "/s/api/createShare";

		ShareAuth auth = new ShareAuth();
		auth.setType(type);
		auth.setExpires(expires);

		CreateShareRequest req = new CreateShareRequest();
		req.setToken(token);
		req.setFid(fid);
		req.setAuth(auth);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		CreateShareResponse res = JSONObject.parseObject(result, CreateShareResponse.class);

		return res;
	}

	public static UpdateShareResponse updateShare(UpdateShareRequest req) {
		String api_url = X_SERVER_URL + "/s/api/updateShare";

		req.setToken(token);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		System.out.println(result);
		UpdateShareResponse res = JSONObject.parseObject(result, UpdateShareResponse.class);

		return res;
	}

	public static DeleteShareResponse deleteShare(String shareId) {
		String api_url = X_SERVER_URL + "/s/api/deleteShare";

		DeleteShareRequest req = new DeleteShareRequest();
		req.setToken(token);
		req.setShareId(shareId);

		String result = HttpUtils.sendPost(api_url, JSONObject.toJSONString(req));
		System.out.println(result);
		DeleteShareResponse res = JSONObject.parseObject(result, DeleteShareResponse.class);

		return res;
	}
}