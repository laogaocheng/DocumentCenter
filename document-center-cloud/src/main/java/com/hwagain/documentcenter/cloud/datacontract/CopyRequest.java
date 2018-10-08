package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class CopyRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	private long srcGid;
	private long srcFid;
	private long destGid;
	private long destParent;
	private String newName;
	private String srcPath;
	private String dstPath;
	private Boolean overWrite;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public String getDstPath() {
		return dstPath;
	}
	public void setDstPath(String dstPath) {
		this.dstPath = dstPath;
	}
	public Boolean getOverWrite() {
		return overWrite;
	}
	public void setOverWrite(Boolean overWrite) {
		this.overWrite = overWrite;
	}
	public long getSrcGid() {
		return srcGid;
	}
	public void setSrcGid(long srcGid) {
		this.srcGid = srcGid;
	}
	public long getSrcFid() {
		return srcFid;
	}
	public void setSrcFid(long srcFid) {
		this.srcFid = srcFid;
	}
	public long getDestGid() {
		return destGid;
	}
	public void setDestGid(long destGid) {
		this.destGid = destGid;
	}
	public long getDestParent() {
		return destParent;
	}
	public void setDestParent(long destParent) {
		this.destParent = destParent;
	}
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
}
