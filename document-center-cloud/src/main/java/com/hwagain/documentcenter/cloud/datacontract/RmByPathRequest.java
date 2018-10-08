package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class RmByPathRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	private long gid = 0;
	private String path;
	
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
