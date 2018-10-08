package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class UploadFilePartRequest  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String token;
	private String fileUploadId;
	private int partNumber;
	private String partSha1;
	private String partUploadId;
	private int partSize;
	
	public String getFileUploadId() {
		return fileUploadId;
	}
	public void setFileUploadId(String fileUploadId) {
		this.fileUploadId = fileUploadId;
	}
	public int getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(int partNumber) {
		this.partNumber = partNumber;
	}
	public String getPartSha1() {
		return partSha1;
	}
	public void setPartSha1(String partSha1) {
		this.partSha1 = partSha1;
	}
	public String getPartUploadId() {
		return partUploadId;
	}
	public void setPartUploadId(String partUploadId) {
		this.partUploadId = partUploadId;
	}
	public int getPartSize() {
		return partSize;
	}
	public void setPartSize(int partSize) {
		this.partSize = partSize;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
