package com.hwagain.documentcenter.dto;

import java.io.Serializable;
import java.util.Date;
import com.hwagain.framework.core.dto.BaseDto;

public class CloudDirectoryDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;    
    
    private long fid;
    private String name;
	private String fullName;
	private boolean exists;
	private int attributes;
	private Date creationTime;
	private Date lastAccessTime;
	private Date lastWriteTime;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public boolean isExists() {
		return exists;
	}
	public void setExists(boolean exists) {
		this.exists = exists;
	}
	public int getAttributes() {
		return attributes;
	}
	public void setAttributes(int attributes) {
		this.attributes = attributes;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getLastAccessTime() {
		return lastAccessTime;
	}
	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	public Date getLastWriteTime() {
		return lastWriteTime;
	}
	public void setLastWriteTime(Date lastWriteTime) {
		this.lastWriteTime = lastWriteTime;
	}
	public long getFid() {
		return fid;
	}
	public void setFid(long fid) {
		this.fid = fid;
	}
}
