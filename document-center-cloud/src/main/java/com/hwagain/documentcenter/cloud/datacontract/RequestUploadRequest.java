package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;
import java.util.List;

public class RequestUploadRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String token;
	private long parent;
	private String name;
	private long size;
	private String relativepath;
	private long gid;
	private Boolean overWrite;
	private List<PartInfo> partsInfo;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getParent() {
		return parent;
	}
	public void setParent(long parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getRelativepath() {
		return relativepath;
	}
	public void setRelativepath(String relativepath) {
		this.relativepath = relativepath;
	}
	public long getGid() {
		return gid;
	}
	public void setGid(long gid) {
		this.gid = gid;
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
