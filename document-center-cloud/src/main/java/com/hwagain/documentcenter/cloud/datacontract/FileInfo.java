package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class FileInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private long fid;

	private String path;
	
	private long parent;

	private String name;

	private long size;

	private long c_ctime;

	private long c_atime;

	private long c_mtime;

	private long s_mtime;

	private long rs_mtime;

	private int attr;

	private Object exattr;

	private String creator;

	private String editor;

	private long locker;

	private int right;

	// 以下是请求上传文件，已存在文件时才有的数据

	private int diskClientAttr;

	private int fsid;

	private String stor;

	private long xs_mtime;

	private String suffix;

	private long cid;

	private long locker_expired_time;

	private long s_ctime;

	private Boolean isStrongboxFile;

	private Boolean root;

	private Boolean dir;

	private long version;

	public long getFid() {
		return fid;
	}

	public void setFid(long fid) {
		this.fid = fid;
	}

	public long getParent() {
		return parent;
	}

	public void setParent(long parent) {
		this.parent = parent;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getC_ctime() {
		return c_ctime;
	}

	public void setC_ctime(long c_ctime) {
		this.c_ctime = c_ctime;
	}

	public long getC_atime() {
		return c_atime;
	}

	public void setC_atime(long c_atime) {
		this.c_atime = c_atime;
	}

	public long getC_mtime() {
		return c_mtime;
	}

	public void setC_mtime(long c_mtime) {
		this.c_mtime = c_mtime;
	}

	public long getS_mtime() {
		return s_mtime;
	}

	public void setS_mtime(long s_mtime) {
		this.s_mtime = s_mtime;
	}

	public long getRs_mtime() {
		return rs_mtime;
	}

	public void setRs_mtime(long rs_mtime) {
		this.rs_mtime = rs_mtime;
	}

	public int getAttr() {
		return attr;
	}

	public void setAttr(int attr) {
		this.attr = attr;
	}

	public Object getExattr() {
		return exattr;
	}

	public void setExattr(Object exattr) {
		this.exattr = exattr;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public long getLocker() {
		return locker;
	}

	public void setLocker(long locker) {
		this.locker = locker;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getDiskClientAttr() {
		return diskClientAttr;
	}

	public void setDiskClientAttr(int diskClientAttr) {
		this.diskClientAttr = diskClientAttr;
	}

	public int getFsid() {
		return fsid;
	}

	public void setFsid(int fsid) {
		this.fsid = fsid;
	}

	public String getStor() {
		return stor;
	}

	public void setStor(String stor) {
		this.stor = stor;
	}

	public long getXs_mtime() {
		return xs_mtime;
	}

	public void setXs_mtime(long xs_mtime) {
		this.xs_mtime = xs_mtime;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public long getLocker_expired_time() {
		return locker_expired_time;
	}

	public void setLocker_expired_time(long locker_expired_time) {
		this.locker_expired_time = locker_expired_time;
	}

	public long getS_ctime() {
		return s_ctime;
	}

	public void setS_ctime(long s_ctime) {
		this.s_ctime = s_ctime;
	}

	public Boolean getIsStrongboxFile() {
		return isStrongboxFile;
	}

	public void setIsStrongboxFile(Boolean isStrongboxFile) {
		this.isStrongboxFile = isStrongboxFile;
	}

	public Boolean getRoot() {
		return root;
	}

	public void setRoot(Boolean root) {
		this.root = root;
	}

	public Boolean getDir() {
		return dir;
	}

	public void setDir(Boolean dir) {
		this.dir = dir;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
