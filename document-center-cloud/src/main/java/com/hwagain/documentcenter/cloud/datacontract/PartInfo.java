package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class PartInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long partSize;
	private String partSha1;
	
	public long getPartSize() {
		return partSize;
	}
	public void setPartSize(long partSize) {
		this.partSize = partSize;
	}
	public String getPartSha1() {
		return partSha1;
	}
	public void setPartSha1(String partSha1) {
		this.partSha1 = partSha1;
	}
}
