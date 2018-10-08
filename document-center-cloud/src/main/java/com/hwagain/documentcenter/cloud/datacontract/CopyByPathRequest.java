package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class CopyByPathRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	private String srcPath;
	private String dstPath;
	private Boolean overWrite;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public String getDstPath() {
		return dstPath;
	}
	public void setDstPath(String dstPath) {
		this.dstPath = dstPath;
	}
	public Boolean getOverWrite() {
		return overWrite;
	}
	public void setOverWrite(Boolean overWrite) {
		this.overWrite = overWrite;
	}
	
	
}
