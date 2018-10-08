package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class GetDownloadUrlRequest  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	private long gid ;
	private long fid;
	private long expires;
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
	public long getFid() {
		return fid;
	}
	public void setFid(long fid) {
		this.fid = fid;
	}
	public long getExpires() {
		return expires;
	}
	public void setExpires(long expires) {
		this.expires = expires;
	}

	
}
