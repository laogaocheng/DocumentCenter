package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;
import java.util.List;

public class RequestUploadByPathRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String token;
	private String path;
	private long size;
	private long gid;
	private long c_mtime;
	private Boolean overWrite;
	private List<PartInfo> partsInfo;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getGid() {
		return gid;
	}
	public void setGid(long gid) {
		this.gid = gid;
	}
	public long getC_mtime() {
		return c_mtime;
	}
	public void setC_mtime(long c_mtime) {
		this.c_mtime = c_mtime;
	}
	public Boolean getOverWrite() {
		return overWrite;
	}
	public void setOverWrite(Boolean overWrite) {
		this.overWrite = overWrite;
	}
	public List<PartInfo> getPartsInfo() {
		return partsInfo;
	}
	public void setPartsInfo(List<PartInfo> partsInfo) {
		this.partsInfo = partsInfo;
	}
	
	
}
