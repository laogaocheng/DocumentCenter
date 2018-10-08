package com.hwagain.documentcenter.dto;

import java.io.Serializable;

import com.hwagain.framework.core.dto.BaseDto;


public class UpdateShareDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

	
	private String token;
	private String shareId;
	private ShareAuth auth;
	
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
	public String getShareId() {
		return shareId;
	}
	public void setShareId(String shareId) {
		this.shareId = shareId;
	}
	
	
}
