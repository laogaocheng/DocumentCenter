package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;
import java.util.List;

public class RequestUploadByPathResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Boolean existed;
	private String fileUploadId;
	private List<Node> nodes;
	private FileInfo fileInfo;
	private String stat;
	
	public Boolean getExisted() {
		return existed;
	}
	public void setExisted(Boolean existed) {
		this.existed = existed;
	}
	public String getFileUploadId() {
		return fileUploadId;
	}
	public void setFileUploadId(String fileUploadId) {
		this.fileUploadId = fileUploadId;
	}
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
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
