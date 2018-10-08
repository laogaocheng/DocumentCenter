package com.hwagain.documentcenter.entity;

import com.hwagain.framework.sys.comons.model.BaseModel;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;

/**
 * <p>
 * 
 * </p>
 *
 * @author laogaocheng
 * @since 2018-09-20
 */
public class Permission extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
	@TableId("fd_id")
	private String fdId;
    /**
     * 应用ID
     */
	private String appId;
    /**
     * 要访问的应用ID
     */
	private String visitedAppId;
    /**
     * 上传
     */
	private Boolean write;
    /**
     * 下载
     */
	private Boolean read;
    /**
     * 列表
     */
	private Boolean list;
    /**
     * 删除
     */
	private Boolean delete;
	private Boolean revise;
	private Boolean share;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 创建人
     */
	private String creator;


	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getVisitedAppId() {
		return visitedAppId;
	}

	public void setVisitedAppId(String visitedAppId) {
		this.visitedAppId = visitedAppId;
	}

	public Boolean isWrite() {
		return write;
	}

	public void setWrite(Boolean write) {
		this.write = write;
	}

	public Boolean isRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Boolean isList() {
		return list;
	}

	public void setList(Boolean list) {
		this.list = list;
	}

	public Boolean isDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public Boolean isRevise() {
		return revise;
	}
	
	
	public void setRevise(Boolean revise) {
		this.revise = revise;
	}
	
	public Boolean isShare() {
		return share;
	}
	
	public void setShare(Boolean share) {
		this.share = share;
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
