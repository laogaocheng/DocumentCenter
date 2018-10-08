package com.hwagain.documentcenter.dto;

import java.io.Serializable;
import java.util.Date;

import com.hwagain.framework.core.dto.BaseDto;

public class CloudFileDto  extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;    
    
    private long fid;
    private String name;
	private String fullName;
	private boolean exists;
	private int attributes;
	private Date creationTime;
	private Date lastAccessTime;
	private Date lastWriteTime;
	private String extension;
	private String directoryName;
	private long length;
	
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
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getDirectoryName() {
		return directoryName;
	}
	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public long getFid() {
		return fid;
	}
	public void setFid(long fid) {
		this.fid = fid;
	}
	
	
}
