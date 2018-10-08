package com.hwagain.documentcenter.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.hwagain.documentcenter.entity.Permission;
import com.hwagain.documentcenter.dto.PermissionDto;
import com.hwagain.documentcenter.mapper.PermissionMapper;
import com.hwagain.documentcenter.service.IPermissionService;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.security.common.util.UserUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.plugins.Page;
import com.hwagain.framework.core.dto.PageDto;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.util.ArraysUtil;
import com.hwagain.framework.core.util.Assert;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author laogaocheng
 * @since 2018-09-20
 */
@Service("permissionService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(Permission.class, PermissionDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(PermissionDto.class, Permission.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<PermissionDto> findAll() {
		Wrapper<Permission> wrapper = new CriterionWrapper<Permission>(Permission.class);
		List<Permission> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, PermissionDto.class);
	}

	@Override
	public PermissionDto findOne(String fdId) {
		return entityToDtoMapper.map(super.selectById(fdId), PermissionDto.class);
	}

	@Override
	public PermissionDto save(PermissionDto dto) {
		boolean isNew = dto.getFdId() == null || dto.getFdId().length() == 0;
		if(isNew){
			dto.setFdId(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
			dto.setCreateTime(new Date(System.currentTimeMillis()));
			dto.setCreator(UserUtils.getUserInfo().getName());			
		}
		
		Assert.notBlank(dto.getAppId(), "appId 不能为空");
		Assert.notBlank(dto.getVisitedAppId(), " visited AppId 不能为空");
		
		PermissionDto findOne = findOne(dto.getAppId(), dto.getVisitedAppId());
		Assert.isTrue(findOne == null || findOne.getFdId().equals(dto.getFdId()), "已经存在这个APP的对应权限记录，不能重复");
		
		Permission entity = dtoToEntityMapper.map(dto, Permission.class);
		super.insert(entity);
		return dto;
	}

	@Override
	public PermissionDto update(PermissionDto dto) {		
		PermissionDto findOne = findOne(dto.getAppId(), dto.getVisitedAppId());
		Assert.isTrue(findOne == null || findOne.getFdId().equals(dto.getFdId()), "已经存在这个APP的对应权限记录，不能重复");
		
		Permission entity = dtoToEntityMapper.map(dto, Permission.class);
		super.updateById(entity);
		return dto;
	}

	@Override
	public Boolean deleteByIds(String ids) {
		String[] id = ids.split(";");
		return super.deleteBatchIds(Arrays.asList(id));
	}
	
	@Override
	public PageDto<PermissionDto> findByPage(PermissionDto dto,PageVO pageVo) {
		PageDto<PermissionDto> pageDto = new PageDto<PermissionDto>();
		pageDto.setPage(pageVo.getPage()+1);
		pageDto.setPageSize(pageVo.getPageSize());
		Wrapper< Permission> wrapper = new CriterionWrapper< Permission>( Permission.class);
		Page< Permission> page = super.selectPage(new Page< Permission>(pageDto.getPage(), pageDto.getPageSize()), wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), PermissionDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}
	
	@Override
	public PermissionDto findOne(String appId, String visitedAppId) {
		Wrapper<Permission> wrapper = new CriterionWrapper<Permission>(Permission.class);
		wrapper.eq("appId", appId);
		wrapper.eq("visitedAppId", visitedAppId);
		Permission permission = super.selectOne(wrapper);
		return entityToDtoMapper.map(permission, PermissionDto.class);
	}

}
