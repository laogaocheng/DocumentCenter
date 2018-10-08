package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;
import java.util.List;

public class ListByPathRequest  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String token;
	private long gid = 0;
	private String path;
	private String sort;
	private String order;
	private int pageIndex = 1;
	private int pageSize;
	private List<FlagMap> flag;
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getGid() {
		return gid;
	}
	public void setGid(long gid) {
		this.gid = gid;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
	public List<FlagMap> getFlag() {
		return flag;
	}
	public void setFlag(List<FlagMap> flag) {
		this.flag = flag;
	}	
}
