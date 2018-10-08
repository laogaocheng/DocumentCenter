package com.hwagain.documentcenter.cloud.datacontract;

import java.io.Serializable;

public class DeleteShareResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String stat;
	private String errText;
	
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public String getErrText() {
		return errText;
	}
	public void setErrText(String errText) {
		this.errText = errText;
	}
}
