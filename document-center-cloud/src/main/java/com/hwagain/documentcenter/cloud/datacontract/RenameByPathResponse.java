package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class RenameByPathResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private FileInfo fileInfo;
	private String stat;
	private String errText;
	
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
	public String getErrText() {
		return errText;
	}
	public void setErrText(String errText) {
		this.errText = errText;
	}
}
