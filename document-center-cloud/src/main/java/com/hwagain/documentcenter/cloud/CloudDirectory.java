package com.hwagain.documentcenter.cloud;

import java.io.Serializable;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import com.hwagain.documentcenter.cloud.datacontract.*;
import com.hwagain.documentcenter.dto.SearchFileDto;
import com.hwagain.framework.core.dto.BaseDto;
import com.hwagain.framework.core.exception.CustomException;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class CloudDirectory extends BaseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private AppInfo appInfo;
	private String full_path;
	private String name;
	private String fullName;

	private long fid;
	private boolean exists;
	private int attributes;
	private Date creationTime;
	private Date lastAccessTime;
	private Date lastWriteTime;
	private CloudDirectory parent;
	private CloudDirectory root;
	private String parent_path;

	public CloudDirectory(AppInfo appInfo, String path) {
		this(appInfo, path, null);
	}

	public CloudDirectory(AppInfo appInfo, String path, FileInfo fileInfo) {
		this.appInfo = appInfo;
		path = path.replaceAll("\\\\", "/");
		full_path = appInfo.getHome() + StringUtils.strip(path, "/\\");
		fullName = "/" + StringUtils.strip(path, "/\\");
		int pos = fullName.lastIndexOf('/');
		name = fullName.substring(pos + 1);
		parent_path = fullName.substring(0, pos);

		System.out.println(full_path);

		if (fileInfo == null)
			getDirectoryInfo();
		else
			getDirectoryInfo(fileInfo);

	}

	public List<CloudFile> search(SearchFileDto dto) throws CustomException {
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(SearchFileDto.class, SearchRequest.class).byDefault().register();
		MapperFacade dtoToEntityMapper = factorytwo.getMapperFacade();		
		SearchRequest req = dtoToEntityMapper.map(dto, SearchRequest.class);
		req.setCurrentDirFid(fid);
		ArrayList<CloudFile> files = new ArrayList<CloudFile>();
		SearchResponse res = CloudDisk.search(req);
		String stat = res.getStat();
		if (stat.equals("OK")) {
			for(FileInfo file : res.getRows())
			{
				String path = StringUtils.strip(file.getPath(), appInfo.getHome());
				files.add(new CloudFile(appInfo, path, file));
			}
			return files;
		} else
			throw new CustomException("搜索失败：" + res.getErrText());		
	}
	public void copyTo(String destDirName, boolean overWrite) throws CustomException {
		if (exists) {
			destDirName = destDirName.replaceAll("\\\\", "/");
			destDirName = StringUtils.stripEnd(destDirName, "/\\");
			String dest_path;
			if (destDirName.startsWith("/"))
				dest_path = appInfo.getHome() + destDirName.substring(1) + "/" + name;
			else
				dest_path = appInfo.getHome() + parent_path.substring(1) + "/" + destDirName + "/" + name;

			if (full_path.equals(dest_path)) {
				throw new CustomException("拷贝目录失败：要拷贝的目录和目标目录具有相同的名称");
			} else {
				if (overWrite) {
					List<CloudFile> files = getAllFiles();
					for (CloudFile file : files) {
						String dstFilename = dest_path.replaceFirst(appInfo.getHome(), "/") + "/" + file.getName();
						file.copyTo(dstFilename, overWrite);
					}

				} else {
					CopyByPathResponse res = CloudDisk.copyByPath(full_path, dest_path, overWrite);
					String stat = res.getStat();
					if (stat.equals("OK")) {
					} else
						throw new CustomException("拷贝目录失败：" + res.getErrText());
				}
			}
		} else
			throw new CustomException("拷贝目录失败：当前目录不存在");
	}

	public void rename(String destDirName) throws CustomException {
		if (exists) {
			destDirName = destDirName.replaceAll("\\\\", "/");
			destDirName = StringUtils.stripEnd(destDirName, "/\\");
			String dest_path;
			if (destDirName.startsWith("/"))
				dest_path = appInfo.getHome() + destDirName.substring(1);
			else
				dest_path = appInfo.getHome() + parent_path.substring(1) + "/" + destDirName;

			if (full_path.equals(dest_path)) {
				throw new CustomException("改名失败：要改名的目录和目标目录具有相同的名称");
			} else {
				int p = dest_path.lastIndexOf('/');
				String new_parent_path = dest_path.substring(0, p).substring(appInfo.getHome().length() - 1);
				CloudDirectory new_dir = new CloudDirectory(appInfo, new_parent_path);
				if (!new_dir.exists)
					new_dir.create();
				RenameByPathResponse res = CloudDisk.renameByPath(full_path, dest_path, true);
				String stat = res.getStat();
				if (stat.equals("OK")) {
					full_path = dest_path;
					fullName = dest_path.substring(appInfo.getHome().length() - 1);
					int pos = fullName.lastIndexOf('/');
					name = fullName.substring(pos + 1);
					parent_path = fullName.substring(0, pos);
				} else
					throw new CustomException("改名失败：" + res.getErrText());
			}
		} else
			throw new CustomException("改名失败：目录不存在");
	}

	public void moveTo(String destDirName, Boolean overWrite) throws CustomException {
		if (exists) {
			destDirName = destDirName.replaceAll("\\\\", "/");
			destDirName = StringUtils.stripEnd(destDirName, "/\\");
			String dest_path;
			if (destDirName.startsWith("/"))
				dest_path = appInfo.getHome() + destDirName.substring(1) + "/" + name;
			else
				dest_path = appInfo.getHome() + parent_path.substring(1) + "/" + destDirName + "/" + name;

			if (full_path.equals(dest_path)) {
				throw new CustomException("移动目录失败：要移动的目录和目标目录具有相同的名称");
			} else {
				int p = dest_path.lastIndexOf('/');
				String new_parent_path = dest_path.substring(0, p).substring(appInfo.getHome().length() - 1);
				CloudDirectory new_dir = new CloudDirectory(appInfo, new_parent_path);
				if (!new_dir.exists)
					new_dir.create();
				RenameByPathResponse res = CloudDisk.renameByPath(full_path, dest_path, overWrite);
				String stat = res.getStat();
				if (stat.equals("OK")) {
					full_path = dest_path;
					fullName = dest_path.substring(appInfo.getHome().length() - 1);
					int pos = fullName.lastIndexOf('/');
					name = fullName.substring(pos + 1);
					parent_path = fullName.substring(0, pos);
				} else
					throw new CustomException("移动目录失败：" + res.getErrText());
			}
		} else
			throw new CustomException("移动目录失败：目录不存在");
	}

	public void delete() throws CustomException {
		delete(false);
	}

	public List<CloudFile> getFiles() {
		ArrayList<CloudFile> files = new ArrayList<CloudFile>();
		ListByPathResponse res = CloudDisk.listByPath(full_path);
		String stat = res.getStat();
		if (stat.equals("OK")) {
			for (FileInfo item : res.getItems()) {
				boolean isFile = (item.getAttr() & 16) == 0;
				if (isFile) {
					String path = fullName + "/" + item.getName();
					CloudFile file = new CloudFile(appInfo, path, item);
					files.add(file);
				}
			}
		}
		return files;
	}

	public List<CloudFile> getAllFiles() {
		ArrayList<CloudFile> files = new ArrayList<CloudFile>();
		List<CloudDirectory> allDirectories = getAllDirectories();
		for (CloudDirectory dir : allDirectories) {
			files.addAll(dir.getFiles());
		}
		files.addAll(getFiles());
		return files;
	}

	public List<CloudDirectory> getDirectories() {
		ArrayList<CloudDirectory> files = new ArrayList<CloudDirectory>();
		ListByPathResponse res = CloudDisk.listByPath(full_path);
		String stat = res.getStat();
		if (stat.equals("OK")) {
			for (FileInfo item : res.getItems()) {
				boolean isDirectory = (item.getAttr() & 16) != 0;
				if (isDirectory) {
					String path = fullName + "/" + item.getName();
					CloudDirectory file = new CloudDirectory(appInfo, path, item);
					files.add(file);
				}
			}
		}
		return files;
	}

	public List<CloudDirectory> getAllDirectories() {
		ArrayList<CloudDirectory> all = new ArrayList<CloudDirectory>();
		getDirectories(full_path, all);
		return all;
	}

	private List<CloudDirectory> getDirectories(String full_path, List<CloudDirectory> all) {
		ArrayList<CloudDirectory> dirs = new ArrayList<CloudDirectory>();
		ListByPathResponse res = CloudDisk.listByPath(full_path);
		String stat = res.getStat();
		if (stat.equals("OK")) {
			for (FileInfo item : res.getItems()) {
				boolean isDirectory = (item.getAttr() & 16) != 0;
				if (isDirectory) {
					String path = full_path.substring(appInfo.getHome().length()) + "/" + item.getName();
					CloudDirectory dir = new CloudDirectory(appInfo, path, item);
					dirs.add(dir);
					all.add(dir);

					String subdir_fullpath = StringUtils.stripEnd(full_path, "/\\") + "/" + item.getName();
					getDirectories(subdir_fullpath, all);
				}
			}
		}
		return dirs;
	}

	public void delete(boolean recursive) throws CustomException {
		if (exists) {
			if (!recursive) {
				boolean hasFile = getAllFiles().size() > 0;
				if (hasFile)
					throw new CustomException("删除目录失败：目录不为空.");
			}
			RmByPathResponse res = CloudDisk.rmByPath(full_path);
			String stat = res.getStat();
			if (stat.equals("OK")) {
				exists = false;
			} else
				throw new CustomException("删除目录失败：" + res.getErrText());
		}
	}

	public CloudDirectory createSubdirectory(String name) throws CustomException {
		if (exists) {
			name = name.replaceAll("\\\\", "/");
			String new_path = full_path + "/" + StringUtils.strip(name, "/\\");
			MkdirByPathResponse res = CloudDisk.mkdirByPath(new_path);
			String stat = res.getStat();
			if (stat.equals("OK")) {
				String new_name = new_path.substring(appInfo.getHome().length());
				return new CloudDirectory(appInfo, new_name);
			} else
				throw new CustomException("创建子目录失败：" + res.getErrText());
		} else
			throw new CustomException("创建目录失败：当前目录不存在");
	}

	public void create() throws CustomException {
		if (exists)
			return;

		MkdirByPathResponse res = CloudDisk.mkdirByPath(full_path);
		String stat = res.getStat();
		if (stat.equals("OK")) {
			exists = true;
			getDirectoryInfo();
		} else
			throw new CustomException("创建目录失败：" + res.getErrText());
	}

	private void getDirectoryInfo() {
		FileInfoByPathResponse dirInfo = CloudDisk.fileInfoByPath(full_path);
		String stat = dirInfo.getStat();
		if (stat.equals("OK")) {
			exists = true;

			FileInfo fileInfo = dirInfo.getFileInfo();
			if (fileInfo != null) {
				getDirectoryInfo(fileInfo);
			}
		} else
			exists = false;
	}

	private void getDirectoryInfo(FileInfo fileInfo) {
		if (fileInfo != null) {
			exists = true;
			fid = fileInfo.getFid();
			attributes = fileInfo.getAttr();
			creationTime = new Date(fileInfo.getC_ctime());
			lastAccessTime = new Date(fileInfo.getC_atime());
			lastWriteTime = new Date(fileInfo.getC_mtime());
			// 如果是根目录
			if (fullName.equals("/"))
				root = this;
			else {
				root = new CloudDirectory(appInfo, "/");
				parent = new CloudDirectory(appInfo, parent_path);
			}
		}
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

	public CloudDirectory getParent() {
		return parent;
	}

	public CloudDirectory getRoot() {
		return root;
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

	public AppInfo getAppInfo() {
		return appInfo;
	}

	public String getFull_path() {
		return full_path;
	}

	public long getFid() {
		return fid;
	}

	public void setFid(long fid) {
		this.fid = fid;
	}
}
