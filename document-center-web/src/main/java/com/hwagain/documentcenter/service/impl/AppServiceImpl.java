package com.hwagain.documentcenter.service.impl;

import java.util.Arrays;
import java.util.List;

import com.hwagain.documentcenter.entity.App;
import com.hwagain.documentcenter.dto.AppDto;
import com.hwagain.documentcenter.mapper.AppMapper;
import com.hwagain.documentcenter.service.IAppService;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.plugins.Page;
import com.hwagain.framework.core.dto.PageDto;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.exception.CustomException;
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
@Service("appService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements IAppService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(App.class, AppDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(AppDto.class, App.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<AppDto> findAll() {
		Wrapper<App> wrapper = new CriterionWrapper<App>(App.class);
		List<App> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, AppDto.class);
	}

	@Override
	public AppDto findOne(String fdId) {
		return entityToDtoMapper.map(super.selectById(fdId), AppDto.class);
	}

	@Override
	public AppDto save(AppDto dto) {
		Assert.notBlank(dto.getFdId(), "appId 不能为空");
		Assert.notBlank(dto.getAppName(), "应用名称不能为空");
		Assert.notBlank(dto.getAppSecret(), "应用蜜钥不能为空");
		
		AppDto findOne = findByName(dto.getAppName());
		Assert.isTrue(findOne == null || findOne.getFdId().equals(dto.getFdId()), "应用名称已被占用，请另选一个");
		
		App entity = dtoToEntityMapper.map(dto, App.class);
		super.insert(entity);
		return dto;
	}

	@Override
	public AppDto update(AppDto dto) {
		Assert.notBlank(dto.getFdId(), "appId 不能为空");
		Assert.notBlank(dto.getAppName(), "应用名称不能为空");
		Assert.notBlank(dto.getAppSecret(), "应用蜜钥不能为空");
		
		AppDto findOne = findByName(dto.getAppName());
		Assert.isTrue(findOne == null || findOne.getFdId().equals(dto.getFdId()), "应用名称已被占用，请另选一个");
		
		App entity = dtoToEntityMapper.map(dto, App.class);
		super.updateById(entity);
		return dto;
	}

	@Override
	public Boolean deleteByIds(String ids) {
		String[] id = ids.split(";");
		return super.deleteBatchIds(Arrays.asList(id));
	}
	
	@Override
	public PageDto<AppDto> findByPage(AppDto dto,PageVO pageVo) {
		PageDto<AppDto> pageDto = new PageDto<AppDto>();
		pageDto.setPage(pageVo.getPage()+1);
		pageDto.setPageSize(pageVo.getPageSize());
		Wrapper< App> wrapper = new CriterionWrapper< App>( App.class);
		Page< App> page = super.selectPage(new Page< App>(pageDto.getPage(), pageDto.getPageSize()), wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), AppDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}

	@Override
	public AppDto findByName(String appName) throws CustomException {
		Wrapper< App> wrapper = new CriterionWrapper< App>( App.class);
		wrapper.eq("appName", appName);
		App app = super.selectOne(wrapper);
		return entityToDtoMapper.map(app, AppDto.class);
	}
}
