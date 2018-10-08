package com.hwagain.documentcenter.service;

import com.hwagain.documentcenter.entity.App;
import com.hwagain.documentcenter.dto.AppDto;
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
public interface IAppService extends IService<App> {
	
	public List<AppDto> findAll() throws CustomException; 
	
	public AppDto findOne(String fdId) throws CustomException;
	
	public AppDto save(AppDto dto) throws CustomException;
	
	public AppDto update(AppDto dto) throws CustomException;
	
	public Boolean deleteByIds(String ids) throws CustomException;
	
	public PageDto<AppDto> findByPage(AppDto dto,PageVO pageVo) throws CustomException;
	
	public AppDto findByName(String appName) throws CustomException;
}
