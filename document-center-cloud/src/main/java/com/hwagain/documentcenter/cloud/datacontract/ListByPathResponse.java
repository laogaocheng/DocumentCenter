package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;
import java.util.List;


public class ListByPathResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private int total;
	private String sort;
	private String order;
	private List<FileInfo> items;
	private FileInfo parentFileInfo;
	private String stat;
	private Long dirRight;
	private List<Long> hasfile;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
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

	public List<FileInfo> getItems() {
		return items;
	}

	public void setItems(List<FileInfo> items) {
		this.items = items;
	}

	public FileInfo getParentFileInfo() {
		return parentFileInfo;
	}

	public void setParentFileInfo(FileInfo parentFileInfo) {
		this.parentFileInfo = parentFileInfo;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public List<Long> getHasfile() {
		return hasfile;
	}

	public void setHasfile(List<Long> hasfile) {
		this.hasfile = hasfile;
	}

	public Long getDirRight() {
		return dirRight;
	}

	public void setDirRight(Long dirRight) {
		this.dirRight = dirRight;
	}
}
