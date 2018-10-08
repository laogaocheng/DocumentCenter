package com.hwagain.documentcenter.cloud;

import com.hwagain.documentcenter.cloud.datacontract.*;

public class AppInfo {
	private String appId = null;
	private String home;

	public AppInfo(String appId) {
		this.appId = appId;
		this.home = "/ROOT/APP-" + appId + "/";

		FileInfoByPathResponse root_req = CloudDisk.fileInfoByPath(home);
		if (root_req.getFileInfo() == null) {
			MkdirByPathResponse res = CloudDisk.mkdirByPath(home);
			System.out.println(res.getStat());
		}
	}

	public String getHome() {
		return home;
	}

	public String getAppId() {
		return appId;
	}
}
