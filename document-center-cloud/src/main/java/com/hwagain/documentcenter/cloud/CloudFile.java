package com.hwagain.documentcenter.cloud;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.hwagain.documentcenter.cloud.datacontract.*;
import com.hwagain.documentcenter.dto.ShareDto;
import com.hwagain.framework.core.dto.BaseDto;
import com.hwagain.framework.core.exception.CustomException;

public class CloudFile extends BaseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private AppInfo appInfo;
	private String uid;
	private String full_path;
	private String name;
	private String fullName;

	private long fid;
	private boolean exists;
	private int attributes;
	private Date creationTime;
	private Date lastAccessTime;
	private Date lastWriteTime;
	private String extension;
	private String directoryName;
	private long length;

	private CloudDirectory directory;

	public CloudFile(AppInfo appInfo, String path) {
		this(appInfo, path, null);
	}

	public CloudFile(AppInfo appInfo, String path, FileInfo fileInfo) {
		this.appInfo = appInfo;
		uid = appInfo.getAppId();
		path = path.replaceAll("\\\\", "/");
		full_path = appInfo.getHome() + StringUtils.strip(path, "/\\");
		fullName = "/" + StringUtils.strip(path, "/\\");
		int pos = fullName.lastIndexOf('/');
		name = fullName.substring(pos + 1);
		directoryName = fullName.substring(0, pos);
		directory = new CloudDirectory(appInfo, directoryName);

		if (fileInfo == null)
			getFileInfo();
		else
			getFileInfo(fileInfo);
	}

	public static ShareDto createShare(long fid, int type, long expires) throws CustomException {
			CreateShareResponse res = CloudDisk.createShare(fid, type, expires);
			String stat = res.getStat();
			if (stat.equals("OK")) {
				ShareDto dto = new ShareDto();
				dto.setPassword(res.getPassword());
				dto.setUrl(res.getUrl());
				return dto;
			} else
				throw new CustomException("创建分享失败：" + res.getErrText());
	}
	
	public void save(byte[] fileData)  throws CustomException {
		save(fileData, false);
	}

	public void save(byte[] fileData, Boolean overWrite)  throws CustomException {
		if (exists && !overWrite)
			throw new CustomException("文件保存失败：文件已存在");

		if (overWrite)
			delete();
		
		CloudDisk.uploadFile(full_path, name, fileData);
	}

	public String getDownloadUrl() throws CustomException {
		return getDownloadUrl(99 * 365 * 24 * 60);
	}

	public String getDownloadUrl(long timeOutMinites) throws CustomException {
		if (exists) {
			long expires = System.currentTimeMillis() + timeOutMinites * 60 * 1000;
			GetDownloadUrlResponse res = CloudDisk.getDownloadUrl(full_path, expires);
			String stat = res.getStat();
			if (stat.equals("OK")) {
				return res.getUrl();
			} else
				throw new CustomException("拷贝文件失败：" + res.getErrText());
		} else
			throw new CustomException("拷贝文件失败：文件不存在");
	}

	public void rename(String destFileName) throws CustomException {
		destFileName = StringUtils.strip(destFileName, "/\\");
		moveTo(destFileName, false);
	}

	public void moveTo(String destFileName, Boolean overWrite) throws CustomException {
		if (exists) {
			destFileName = destFileName.replaceAll("\\\\", "/");
			String dest_path;
			if (destFileName.startsWith("/"))
				dest_path = appInfo.getHome() + destFileName.substring(1);
			else
				dest_path = appInfo.getHome() + directoryName.substring(1) + "/" + destFileName;

			if (full_path.equals(dest_path)) {
				return;
			} else {
				dest_path = StringUtils.stripEnd(dest_path, "/\\");
				int p = dest_path.lastIndexOf('/');
				String new_parent_path = dest_path.substring(0, p).substring(appInfo.getHome().length() - 1);
				String new_fullname = dest_path.substring(appInfo.getHome().length() - 1);
				CloudFile dest_file = new CloudFile(appInfo, new_fullname);
				if (dest_file.isExists()) {
					if (overWrite)
						dest_file.delete();
					else
						throw new CustomException("移动文件失败：已经存在同名文件");
				}

				CloudDirectory new_dir = new CloudDirectory(appInfo, new_parent_path);
				if (!new_dir.isExists())
					new_dir.create();
				RenameByPathResponse res = CloudDisk.renameByPath(full_path, dest_path, overWrite);
				String stat = res.getStat();
				if (stat.equals("OK")) {
					full_path = dest_path;
					fullName = dest_path.substring(appInfo.getHome().length() - 1);
					int pos = fullName.lastIndexOf('/');
					name = fullName.substring(pos + 1);
					directoryName = new_parent_path;
				} else
					throw new CustomException("移动文件失败：" + res.getErrText());
			}
		} else
			throw new CustomException("移动文件失败：文件不存在");
	}

	public void delete() throws CustomException {
		if (exists) {
			RmByPathResponse res = CloudDisk.rmByPath(full_path);
			String stat = res.getStat();
			if (stat.equals("OK")) {
				exists = false;
			} else
				throw new CustomException("删除文件失败：" + res.getErrText());
		}
	}

	public void copyTo(String destFileName, boolean overWrite) throws CustomException {
		if (exists) {
			destFileName = destFileName.replaceAll("\\\\", "/");
			String dest_file_path;
			if (destFileName.startsWith("/"))
				dest_file_path = destFileName.substring(1);
			else
				dest_file_path = directoryName + "/" + destFileName;

			CloudFile dest_file = new CloudFile(appInfo, dest_file_path);
			if (dest_file.isExists()) {
				if (overWrite)
					dest_file.delete();
				else
					throw new CustomException("拷贝文件失败：已经存在同名文件");
			}

			CloudDirectory dest_dir = new CloudDirectory(appInfo, dest_file.directoryName);
			if (!dest_dir.isExists())
				dest_dir.create();

			String dest_path = appInfo.getHome() + dest_file_path;
			CopyByPathResponse res = CloudDisk.copyByPath(full_path, dest_path, overWrite);
			String stat = res.getStat();
			if (stat.equals("OK")) {

			} else
				throw new CustomException("拷贝文件失败：" + res.getErrText());
		} else
			throw new CustomException("拷贝文件失败：文件不存在");
	}

	private void getFileInfo() {
		FileInfoByPathResponse dirInfo = CloudDisk.fileInfoByPath(full_path);
		String stat = dirInfo.getStat();
		if (stat.equals("OK")) {
			exists = true;
			FileInfo fileInfo = dirInfo.getFileInfo();
			getFileInfo(fileInfo);

		} else
			exists = false;
	}

	private void getFileInfo(FileInfo fileInfo) {
		if (fileInfo != null) {
			fid = fileInfo.getFid();
			attributes = fileInfo.getAttr();
			creationTime = new Date(fileInfo.getC_ctime());
			lastAccessTime = new Date(fileInfo.getC_atime());
			lastWriteTime = new Date(fileInfo.getC_mtime());
			length = fileInfo.getSize();
			extension = fileInfo.getSuffix();
			exists = true;
		}
	}

	public AppInfo getAppInfo() {
		return appInfo;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		return fullName;
	}

	public boolean isExists() {
		return exists;
	}

	public int getAttributes() {
		return attributes;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public Date getLastWriteTime() {
		return lastWriteTime;
	}

	public String getExtension() {
		return extension;
	}

	public String getDirectoryName() {
		return directoryName;
	}

	public long getLength() {
		return length;
	}

	public CloudDirectory getDirectory() {
		return directory;
	}

	public String getUid() {
		return uid;
	}

	public long getFid() {
		return fid;
	}

	public void setFid(long fid) {
		this.fid = fid;
	}
}
