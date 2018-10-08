package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;
import java.util.List;

public class SearchResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String stat;
	private int total;
	private List<Long> hasfile;
	private List<FileInfo> rows;
	private String errText;
	
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<FileInfo> getRows() {
		return rows;
	}
	public void setRows(List<FileInfo> rows) {
		this.rows = rows;
	}
	public List<Long> getHasfile() {
		return hasfile;
	}
	public void setHasfile(List<Long> hasfile) {
		this.hasfile = hasfile;
	}
	public String getErrText() {
		return errText;
	}
	public void setErrText(String errText) {
		this.errText = errText;
	}
}
