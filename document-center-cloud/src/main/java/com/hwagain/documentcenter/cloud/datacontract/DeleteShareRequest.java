package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class DeleteShareRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	private String shareId;

	public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
