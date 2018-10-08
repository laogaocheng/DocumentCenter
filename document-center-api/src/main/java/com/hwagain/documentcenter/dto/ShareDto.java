package com.hwagain.documentcenter.dto;

import com.hwagain.framework.core.dto.BaseDto;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author laogaocheng
 * @since 2018-08-14
 */
public class ShareDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private String password;
	private String url;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
