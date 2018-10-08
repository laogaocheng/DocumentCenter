package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class CommitUploadByPathRequest  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String token;
	private String fileUploadId;
	private String[] partCommitIds;
	private String path;
	private String name;
	private long size;
	private long gid;
	private Boolean overWrite;

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getFileUploadId() {
		return fileUploadId;
	}
	public void setFileUploadId(String fileUploadId) {
		this.fileUploadId = fileUploadId;
	}
	public String[] getPartCommitIds() {
		return partCommitIds;
	}
	public void setPartCommitIds(String[] partCommitIds) {
		this.partCommitIds = partCommitIds;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
