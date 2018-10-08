package com.hwagain.documentcenter.dto;

import java.io.Serializable;

public class ShareAuth implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int type; //1:预览和下载 ： 仅预览               4：仅上传  ：预览、下载、上传 
	private long expires;
	private String password;
	private int downloadLimit;
	private int viewLimit;
	private String ipLimit; //选择限制类型，再输入限制IP，以逗号分隔,例：192.163.1.123,192.168.*.211
	private int ipLimitWhite; //1是允许，2 不允许
	private int listable;
	private int downloadable;  //仅上传时需设置  downloadable=0
	private long uploadFileSizeLimit;
	private String uploadFileTypeLimit;	//填写文件类型将只允许上传此类型文件，允许多种类型，以逗号分隔，例：doc,docx,ppt,pptx,wps
	private int previewOnly; //仅预览时需设置 previewOnly=1
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getExpires() {
		return expires;
	}
	public void setExpires(long expires) {
		this.expires = expires;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getDownloadLimit() {
		return downloadLimit;
	}
	public void setDownloadLimit(int downloadLimit) {
		this.downloadLimit = downloadLimit;
	}
	public int getViewLimit() {
		return viewLimit;
	}
	public void setViewLimit(int viewLimit) {
		this.viewLimit = viewLimit;
	}
	public String getIpLimit() {
		return ipLimit;
	}
	public void setIpLimit(String ipLimit) {
		this.ipLimit = ipLimit;
	}
	public int getIpLimitWhite() {
		return ipLimitWhite;
	}
	public void setIpLimitWhite(int ipLimitWhite) {
		this.ipLimitWhite = ipLimitWhite;
	}
	public int getListable() {
		return listable;
	}
	public void setListable(int listable) {
		this.listable = listable;
	}
	public int getDownloadable() {
		return downloadable;
	}
	public void setDownloadable(int downloadable) {
		this.downloadable = downloadable;
	}
	public long getUploadFileSizeLimit() {
		return uploadFileSizeLimit;
	}
	public void setUploadFileSizeLimit(long uploadFileSizeLimit) {
		this.uploadFileSizeLimit = uploadFileSizeLimit;
	}
	public String getUploadFileTypeLimit() {
		return uploadFileTypeLimit;
	}
	public void setUploadFileTypeLimit(String uploadFileTypeLimit) {
		this.uploadFileTypeLimit = uploadFileTypeLimit;
	}
	public int getPreviewOnly() {
		return previewOnly;
	}
	public void setPreviewOnly(int previewOnly) {
		this.previewOnly = previewOnly;
	}

	
}
