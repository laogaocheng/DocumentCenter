package com.hwagain.documentcenter.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.documentcenter.TokenInfo;
import com.hwagain.documentcenter.cloud.*;
import com.hwagain.documentcenter.cloud.datacontract.DeleteShareResponse;
import com.hwagain.documentcenter.cloud.datacontract.UpdateShareRequest;
import com.hwagain.documentcenter.cloud.datacontract.UpdateShareResponse;
import com.hwagain.documentcenter.dto.*;
import com.hwagain.documentcenter.service.IAppService;
import com.hwagain.documentcenter.service.IDocumentService;
import com.hwagain.documentcenter.service.IPermissionService;
import com.hwagain.framework.core.exception.CustomException;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Service("documentService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DocumentServiceImpl implements IDocumentService {

	@Autowired
	IAppService appService;
	@Autowired
	IPermissionService permissionService;
	
	static MapperFacade entityToDtoMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(CloudDirectory.class, CloudDirectoryDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
	}

	@Override
	public TokenInfo getTokenInfo(String token) throws CustomException {
		if (CloudDisk.isLoginSuccess()) {
			token = EncryptUtil.d3esDecode(EncryptUtil.decodeBASE64(token));
			String[] arr = token.split("\\|");
			if (arr.length == 4) {
				long expiredTime = Long.valueOf(arr[3]);
				if (System.currentTimeMillis() < expiredTime) {
					TokenInfo tokenInfo = new TokenInfo();
					tokenInfo.setAppId(arr[0]);
					tokenInfo.setSecret(arr[1]);
					tokenInfo.setIp(arr[2]);
					tokenInfo.setExpiredTime(expiredTime);
					tokenInfo.setPermissionService(permissionService);
					return tokenInfo;
				} else
					throw new CustomException("token 已失效，请重新获取");
			} else
				throw new CustomException("错误的 token");
		} else
			throw new CustomException("连接云盘失败，请联系管理员：" + CloudDisk.getLoginErrText());
	}

	@Override
	public String getToken(String appId, String secret, String ip) throws CustomException {
		if (CloudDisk.isLoginSuccess()) {
			AppDto app = appService.findOne(appId);
			if (app != null && app.getAppSecret().equals(secret)) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.HOUR, 24);
				String token = appId + "|" + secret + "|" + ip + "|" + c.getTime().getTime();
				return EncryptUtil.encodeBASE64(EncryptUtil.d3esEncode(token));
			}
			else
				throw new CustomException("授权失败：应用Id和密钥不匹配.");
		} else
			throw new CustomException("连接云盘失败，请联系管理员：" + CloudDisk.getLoginErrText());
	}

	@Override
	public CloudDirectoryDto createDirectory(String appId, String path) throws CustomException {
		AppInfo app = new AppInfo(appId);
		// 创建目录
		CloudDirectory cDir = new CloudDirectory(app, path);
		if (!cDir.isExists())
			cDir.create();

		return entityToDtoMapper.map(cDir, CloudDirectoryDto.class);
	}

	@Override
	public void deleteDirectory(String appId, String path, boolean recursive) throws CustomException {
		AppInfo app = new AppInfo(appId);
		// 创建目录
		CloudDirectory cDir = new CloudDirectory(app, path);
		if (cDir.isExists())
			cDir.delete(recursive);
		else
			throw new CustomException("要删除的目录不存在");
	}

	@Override
	public CloudDirectoryDto getDirectoryInfo(String appId, String path) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudDirectory dir = new CloudDirectory(app, path);
		return entityToDtoMapper.map(dir, CloudDirectoryDto.class);
	}

	@Override
	public CloudDirectoryDto renameDirectory(String appId, String srcPath, String dstPath) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudDirectory cDir = new CloudDirectory(app, srcPath);
		if (cDir.isExists()) {
			cDir.rename(dstPath);
			return entityToDtoMapper.map(new CloudDirectory(app, dstPath), CloudDirectoryDto.class);
		} else
			throw new CustomException("目录不存在");
	}

	@Override
	public CloudDirectoryDto moveToDirectory(String appId, String srcPath, String dstPath, boolean overWrite)
			throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudDirectory cDir = new CloudDirectory(app, srcPath);
		if (cDir.isExists()) {
			CloudDirectory dDir = new CloudDirectory(app, dstPath);
			if (dDir.isExists())
			{
				cDir.moveTo(dstPath, overWrite);
				return entityToDtoMapper.map(new CloudDirectory(app, dstPath), CloudDirectoryDto.class);
			}
			else
				throw new CustomException("目标目录不存在");
		} else
			throw new CustomException("要移动的目录不存在");
	}

	@Override
	public CloudDirectoryDto copyToDirectory(String appId, String srcPath, String dstPath, boolean overWrite)
			throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudDirectory cDir = new CloudDirectory(app, srcPath);
		if (cDir.isExists()) {
			CloudDirectory dstDir = new CloudDirectory(app, dstPath);
			if (!dstDir.isExists())
				dstDir.create();
			cDir.copyTo(dstPath, overWrite);
			return entityToDtoMapper.map(new CloudDirectory(app, dstPath), CloudDirectoryDto.class);
		} else
			throw new CustomException("要移动的目录不存在");
	}

	@Override
	public List<CloudFileDto> getFiles(String appId, String path) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudDirectory cDir = new CloudDirectory(app, path);
		if (cDir.isExists()) {
			return entityToDtoMapper.mapAsList(cDir.getFiles(), CloudFileDto.class);
		} else
			throw new CustomException("目录不存在");
	}

	@Override
	public List<CloudFileDto> getAllFiles(String appId, String path) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudDirectory cDir = new CloudDirectory(app, path);
		if (cDir.isExists()) {
			return entityToDtoMapper.mapAsList(cDir.getAllFiles(), CloudFileDto.class);
		} else
			throw new CustomException("目录不存在");
	}

	@Override
	public List<CloudDirectoryDto> getDirectories(String appId, String path) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudDirectory cDir = new CloudDirectory(app, path);
		if (cDir.isExists()) {
			return entityToDtoMapper.mapAsList(cDir.getDirectories(), CloudDirectoryDto.class);
		} else
			throw new CustomException("目录不存在");
	}

	@Override
	public List<CloudDirectoryDto> getAllDirectories(String appId, String path) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudDirectory cDir = new CloudDirectory(app, path);
		if (cDir.isExists()) {
			return entityToDtoMapper.mapAsList(cDir.getAllDirectories(), CloudDirectoryDto.class);
		} else
			throw new CustomException("目录不存在");
	}

	@Override
	public CloudDirectoryDto createSubdirectory(String appId, String parentPath, String path) throws CustomException {
		AppInfo app = new AppInfo(appId);
		// 创建目录
		CloudDirectory cDir = new CloudDirectory(app, parentPath);
		if (cDir.isExists()) {
			CloudDirectory newDir = cDir.createSubdirectory(path);
			return entityToDtoMapper.map(newDir, CloudDirectoryDto.class);
		} else
			throw new CustomException("目录不存在");
	}

	@Override
	public CloudFileDto getFileInfo(String appId, String path) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudFile file = new CloudFile(app, path);
		return entityToDtoMapper.map(file, CloudFileDto.class);
	}

	@Override
	public void deleteFile(String appId, String path) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudFile file = new CloudFile(app, path);
		if (file.isExists())
			file.delete();
		else
			throw new CustomException("文件不存在");
	}

	@Override
	public CloudFileDto renameFile(String appId, String path, String dstPath) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudFile file = new CloudFile(app, path);
		if (file.isExists()) {
			file.rename(dstPath);
			return entityToDtoMapper.map(new CloudFile(app, dstPath), CloudFileDto.class);
		} else
			throw new CustomException("文件不存在");
	}

	@Override
	public CloudFileDto moveToFile(String appId, String srcPath, String dstPath, boolean overWrite) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudFile file = new CloudFile(app, srcPath);
		if (file.isExists()) {
			file.moveTo(dstPath, overWrite);
			return entityToDtoMapper.map(new CloudFile(app, dstPath), CloudFileDto.class);
		} else
			throw new CustomException("文件不存在");
	}

	@Override
	public CloudFileDto copyToFile(String appId, String srcPath, String dstPath, boolean overWrite) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudFile file = new CloudFile(app, srcPath);
		if (file.isExists()) {
			file.copyTo(dstPath, overWrite);
			return entityToDtoMapper.map(new CloudFile(app, dstPath), CloudFileDto.class);
		} else
			throw new CustomException("文件不存在");
	}

	@Override
	public String getDownloadUrl(String appId, String path, int timeOutMinites) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudFile file = new CloudFile(app, path);
		if (file.isExists()) {
			return file.getDownloadUrl(timeOutMinites);
		} else
			throw new CustomException("文件不存在");
	}

	@Override
	public String getDownloadUrl(String appId, String path) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudFile file = new CloudFile(app, path);
		if (file.isExists()) {
			return file.getDownloadUrl();
		} else
			throw new CustomException("文件不存在");
	}

	@Override
	public CloudFileDto uploadFile(String appId, String path, byte[] fileData, boolean overWrite) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudFile file = new CloudFile(app, path);
		CloudDirectory dir = file.getDirectory();
		if(!dir.isExists()) dir.create();
		file.save(fileData, overWrite);
		return entityToDtoMapper.map(new CloudFile(app, path), CloudFileDto.class);
	}

	@Override
	public List<CloudFileDto> search(String appId, String path, SearchFileDto searchFileDto) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudDirectory cDir = new CloudDirectory(app, path);
		if (cDir.isExists()) {			
			return entityToDtoMapper.mapAsList(cDir.search(searchFileDto), CloudFileDto.class);
		} else
			throw new CustomException("目录不存在");
	}

	@Override
	public ShareDto createShare(String appId, String path, int type, long expires) throws CustomException {
		AppInfo app = new AppInfo(appId);
		CloudFile file = new CloudFile(app, path);
		if (file.isExists()) {			
			return entityToDtoMapper.map(CloudFile.createShare(file.getFid(), type, expires), ShareDto.class);
		} else
			throw new CustomException("目录或文件不存在");
	}

	@Override
	public void updateShare(UpdateShareDto dto) throws CustomException {
		UpdateShareRequest req = entityToDtoMapper.map(dto, UpdateShareRequest.class);		
		UpdateShareResponse res = CloudDisk.updateShare(req);		
		String stat = res.getStat();
		if (stat.equals("OK")) {
			;
		} else
			throw new CustomException("修改分享失败：" + res.getErrText());
	}

	@Override
	public void deleteShare(String shareId) throws CustomException {
		DeleteShareResponse res = CloudDisk.deleteShare(shareId);		
		String stat = res.getStat();
		if (stat.equals("OK")) {
			;
		} else
			throw new CustomException("删除分享失败：" + res.getErrText());
	}
}
