package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;
import java.util.List;

public class RequestUploadResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Boolean exited;
	private String fileUploadId;
	private List<Node> nodes;
	private FileInfo fileInfo;
	private String uploadRateLimit;
	private String stat;
	
	public Boolean getExited() {
		return exited;
	}
	public void setExited(Boolean exited) {
		this.exited = exited;
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
	public String getUploadRateLimit() {
		return uploadRateLimit;
	}
	public void setUploadRateLimit(String uploadRateLimit) {
		this.uploadRateLimit = uploadRateLimit;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
}
