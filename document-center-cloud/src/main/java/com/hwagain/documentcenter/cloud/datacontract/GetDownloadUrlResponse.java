package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class GetDownloadUrlResponse  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String stat;
	private String url;
	private String errText;
	
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getErrText() {
		return errText;
	}
	public void setErrText(String errText) {
		this.errText = errText;
	}

}
