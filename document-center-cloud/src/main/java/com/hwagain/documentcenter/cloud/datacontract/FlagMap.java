package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class FlagMap  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Boolean getFileUnlawful() {
		return fileUnlawful;
	}
	public void setFileUnlawful(Boolean fileUnlawful) {
		this.fileUnlawful = fileUnlawful;
	}
	public Boolean getCoop() {
		return coop;
	}
	public void setCoop(Boolean coop) {
		this.coop = coop;
	}
	public Boolean getIsRootCoop() {
		return isRootCoop;
	}
	public void setIsRootCoop(Boolean isRootCoop) {
		this.isRootCoop = isRootCoop;
	}
	private Boolean fileUnlawful;
	private Boolean coop;
	private Boolean isRootCoop;
}
