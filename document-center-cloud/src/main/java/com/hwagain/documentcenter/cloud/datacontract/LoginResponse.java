package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class LoginResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String stat;
	private String token;
	private String errText;
	
	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getErrText() {
		return errText;
	}

	public void setErrText(String errText) {
		this.errText = errText;
	}
}
