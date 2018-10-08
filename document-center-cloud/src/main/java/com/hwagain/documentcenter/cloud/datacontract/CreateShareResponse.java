package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class CreateShareResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String shareId;
	private String password;
	private String url;
	private String stat;
	private String errText;
	
	
	public String getShareId() {
		return shareId;
	}
	public void setShareId(String shareId) {
		this.shareId = shareId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public String getErrText() {
		return errText;
	}
	public void setErrText(String errText) {
		this.errText = errText;
	}
	
	

}
