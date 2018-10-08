package com.hwagain.documentcenter.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class UserDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private String fdId;
	private String userName;
	private Integer userType;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	private String creator;


	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

}
