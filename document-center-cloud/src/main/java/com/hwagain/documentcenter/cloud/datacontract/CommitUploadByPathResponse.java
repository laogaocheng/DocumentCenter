package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class CommitUploadByPathResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String stat;
	private FileInfo fileInfo;
	
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public FileInfo getFileInfo() {
		return fileInfo;
	}
	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}
	
	
}
