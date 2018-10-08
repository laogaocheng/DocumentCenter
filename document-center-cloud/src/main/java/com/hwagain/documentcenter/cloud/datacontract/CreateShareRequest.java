package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class CreateShareRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	private long fid;
	private ShareAuth auth;
	
	public long getFid() {
		return fid;
	}
	public void setFid(long fid) {
		this.fid = fid;
	}
	public ShareAuth getAuth() {
		return auth;
	}
	public void setAuth(ShareAuth auth) {
		this.auth = auth;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
