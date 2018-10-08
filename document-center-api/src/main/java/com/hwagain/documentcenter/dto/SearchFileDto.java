package com.hwagain.documentcenter.dto;

import java.io.Serializable;

import com.hwagain.framework.core.dto.BaseDto;

public class SearchFileDto  extends BaseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	private String path;
	private String appId;
	private String keyWord;
	private String creator;
	private String editor;
	private String sort;
	private String order;
	private boolean searchChild;
	private String suffix;
	private String sizeMax;
	private String sizeMin;
	private String ctimeStart;
	private String ctimeEnd;
	private String mtimeStart;
	private String mtimeEnd;
	private int pageIndex;
	private int pageSize;

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public boolean isSearchChild() {
		return searchChild;
	}
	public void setSearchChild(boolean searchChild) {
		this.searchChild = searchChild;
	}

	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getSizeMax() {
		return sizeMax;
	}
	public void setSizeMax(String sizeMax) {
		this.sizeMax = sizeMax;
	}
	public String getSizeMin() {
		return sizeMin;
	}
	public void setSizeMin(String sizeMin) {
		this.sizeMin = sizeMin;
	}
	public String getCtimeStart() {
		return ctimeStart;
	}
	public void setCtimeStart(String ctimeStart) {
		this.ctimeStart = ctimeStart;
	}
	public String getCtimeEnd() {
		return ctimeEnd;
	}
	public void setCtimeEnd(String ctimeEnd) {
		this.ctimeEnd = ctimeEnd;
	}
	public String getMtimeStart() {
		return mtimeStart;
	}
	public void setMtimeStart(String mtimeStart) {
		this.mtimeStart = mtimeStart;
	}
	public String getMtimeEnd() {
		return mtimeEnd;
	}
	public void setMtimeEnd(String mtimeEnd) {
		this.mtimeEnd = mtimeEnd;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
