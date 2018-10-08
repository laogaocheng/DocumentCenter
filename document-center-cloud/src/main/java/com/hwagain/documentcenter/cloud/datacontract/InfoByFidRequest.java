package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class InfoByFidRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String token;
	private long gid = 0;
	private long fid = 0;
	private Boolean inherit;
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
	public Boolean getInherit() {
		return inherit;
	}
	public void setInherit(Boolean inherit) {
		this.inherit = inherit;
	}
}
