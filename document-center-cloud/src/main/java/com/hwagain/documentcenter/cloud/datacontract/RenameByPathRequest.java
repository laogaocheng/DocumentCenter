package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class RenameByPathRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	private long gid = 0;
	private String srcPath;
	private long dstGid = 0;
	private String dstPath;
	private Boolean overWrite;
	private Boolean keep;
	private long pre_fid;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getGid() {
		return gid;
	}
	public void setGid(long gid) {
		this.gid = gid;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public long getDstGid() {
		return dstGid;
	}
	public void setDstGid(long dstGid) {
		this.dstGid = dstGid;
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
	public Boolean getKeep() {
		return keep;
	}
	public void setKeep(Boolean keep) {
		this.keep = keep;
	}
	public long getPre_fid() {
		return pre_fid;
	}
	public void setPre_fid(long pre_fid) {
		this.pre_fid = pre_fid;
	}
	
	
}
