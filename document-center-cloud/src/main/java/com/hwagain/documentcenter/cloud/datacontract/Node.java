package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class Node implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String addr;

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
}
