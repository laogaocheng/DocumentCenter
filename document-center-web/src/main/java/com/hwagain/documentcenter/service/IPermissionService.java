package com.hwagain.documentcenter.service;

import com.hwagain.documentcenter.entity.Permission;
import com.hwagain.documentcenter.dto.PermissionDto;
import com.hwagain.framework.mybatisplus.service.IService;
import java.util.List;
import com.hwagain.framework.core.dto.PageDto;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.exception.CustomException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author laogaocheng
 * @since 2018-09-20
 */
public interface IPermissionService extends IService<Permission> {
	
	public List<PermissionDto> findAll() throws CustomException; 
	
	public PermissionDto findOne(String fdId) throws CustomException;
	
	public PermissionDto save(PermissionDto dto) throws CustomException;
	
	public PermissionDto update(PermissionDto dto) throws CustomException;
	
	public Boolean deleteByIds(String ids) throws CustomException;
	
	public PageDto<PermissionDto> findByPage(PermissionDto dto,PageVO pageVo) throws CustomException;
	
	public PermissionDto findOne(String appId, String visitedAppId) throws CustomException;
}
