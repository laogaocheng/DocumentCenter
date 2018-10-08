package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class RequestDownloadByPathRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String token;
	private String path;
	private long gid;
	private Boolean directUrl;
	private long pos;
	private long len;
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
	public long getGid() {
		return gid;
	}
	public void setGid(long gid) {
		this.gid = gid;
	}
	public Boolean getDirectUrl() {
		return directUrl;
	}
	public void setDirectUrl(Boolean directUrl) {
		this.directUrl = directUrl;
	}
	public long getPos() {
		return pos;
	}
	public void setPos(long pos) {
		this.pos = pos;
	}
	public long getLen() {
		return len;
	}
	public void setLen(long len) {
		this.len = len;
	}
	
	
}
