package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class FileInfoByPathResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private FileInfo fileInfo;
	private String stat;
	
	public FileInfo getFileInfo() {
		return fileInfo;
	}
	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
}
