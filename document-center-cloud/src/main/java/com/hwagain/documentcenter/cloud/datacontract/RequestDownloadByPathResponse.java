package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class RequestDownloadByPathResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String downloadUrl;

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
}
