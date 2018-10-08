package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class UploadFilePartResponse  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String stat;
	private String partCommitId;
	
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public String getPartCommitId() {
		return partCommitId;
	}
	public void setPartCommitId(String partCommitId) {
		this.partCommitId = partCommitId;
	}
}
