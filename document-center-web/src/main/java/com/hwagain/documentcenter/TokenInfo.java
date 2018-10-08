package com.hwagain.documentcenter;

import com.hwagain.documentcenter.dto.PermissionDto;
import com.hwagain.documentcenter.service.IPermissionService;
import com.hwagain.framework.core.exception.CustomException;

public class TokenInfo {
	private String appId;
	private String secret;
	private String ip;
	private long expiredTime;
	
	IPermissionService permissionService;
	
	public IPermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(IPermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public PermissionDto getPermission(String visitedAppId)
	{
		return permissionService.findOne(appId, visitedAppId);
	}
	
	public void check(String visitedAppId, EnumPermission requestPermission) throws CustomException
	{
		boolean has = appId.equals(visitedAppId);
		//如果不是访问自己的空间
		if(!has)		
		{
			PermissionDto p = getPermission(visitedAppId);
			if(p != null)
			{
				switch(requestPermission)
				{
				case WRITE:
					has = p.isWrite();
					break;
				case READ:
					has = p.isRead();
					break;
				case LIST:
					has = p.isList();
					break;
				case DELETE:
					has = p.isDelete();
					break;
				case REVISE:
					has = p.isRevise();
					break;
				case SHARE:
					has = p.isShare();
					break;
				default:
					break;				
				}
			}
		}
		if(!has)throw new CustomException("权限不足: 没有 " + requestPermission + " 的权限");
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
	}

	public boolean isValid(String requestIP) {
		return System.currentTimeMillis() < expiredTime && ip.equals(requestIP);
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
