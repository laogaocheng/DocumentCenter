package com.hwagain.documentcenter.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.framework.validation.util.ValidationUtil;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.documentcenter.dto.AppDto;
import com.hwagain.documentcenter.service.IAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author laogaocheng
 * @since 2018-09-20
 */
@RestController
@RequestMapping(value="/doc/app",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="应用注册和管理",description="应用注册和管理")
public class AppController extends BaseController{
	
	@Autowired
	IAppService appService;
	
	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@ApiOperation(value = "查詢应用列表", notes = "查詢应用列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(appService.findAll(),null,this.getValidation(new AppDto()),null);
	}
	
	/**
	 * 获取当前资源下的用户权限
	 * 
	 * @param dto
	 * @return
	 */
	public Map<String, Boolean> getValidation(AppDto dto) {
		String contextPath = getRequest().getContextPath();
		Map<String, Boolean> validURIMaps = ValidationUtil.validModuleOperator(contextPath, "/documentcenter/app", dto);
		return validURIMaps;
	}
	
	/**
	 * 按ID查询数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/findOne",method={RequestMethod.GET})
	@ApiOperation(value = "按ID查询应用", notes = "按ID查询应用",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "数据ID", paramType = "query", required = false, dataType = "String")
	})
	public Response findOne(String fdId){
		return SuccessResponseData.newInstance(appService.findOne(fdId));
	}
	
	/**
	 * 新增数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/addNew",method={RequestMethod.POST})
	@ApiOperation(value = "新增应用", notes = "新增应用",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "appName", value = "应用名称", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "appDesc", value = "描述", paramType = "query", required = false, dataType = "String") })
	public Response addNew(String appName, String appDesc){
		AppDto dto = new AppDto();
		dto.setFdId(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
		dto.setAppSecret(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
		dto.setAppName(appName);
		dto.setAppDesc(appDesc);
		dto.setCreateTime(new Date(System.currentTimeMillis()));
		dto.setCreator(UserUtils.getUserInfo().getName());
		
		return SuccessResponseData.newInstance(appService.save(dto));
	}
	
	/**
	 * 修改数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ApiOperation(value = "修改应用资料", notes = "修改应用资料",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "appId", value = "应用 ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "appName", value = "应用名称", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "appDesc", value = "描述", paramType = "query", required = false, dataType = "String") })
	public Response update(String appId, String appName, String appDesc){
		AppDto dto = appService.findOne(appId);
		Assert.isTrue(dto != null, "指定的应用不存在");		
		
		if(appName != null && appName.length() > 0) dto.setAppName(appName);
		if(appDesc != null && appDesc.length() > 0) dto.setAppDesc(appDesc);
		
		return SuccessResponseData.newInstance(appService.update(dto));
	}
	
	@RequestMapping(value="/updateSecret",method={RequestMethod.POST})
	@ApiOperation(value = "修改访问密钥", notes = "修改访问密钥",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "appId", value = "应用 ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "secret", value = "访问密钥", paramType = "query", required = true, dataType = "String") })
	public Response updateSecret(String appId, String secret){
		AppDto dto = appService.findOne(appId);
		Assert.isTrue(dto != null, "指定的应用不存在");		
		dto.setAppSecret(secret);		
		
		return SuccessResponseData.newInstance(appService.update(dto));
	}
	
	/**
	 * 删除数据，可批量
	 * 
	 * @param ids  格式：1;2;3;4....
	 * @return
	 */
	@RequestMapping(value="/delete",method={RequestMethod.GET})
	@ApiOperation(value = "删除应用", notes = "删除应用",httpMethod="GET")
		@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "数据集,格式：1;2;3;4....", paramType = "query", required = false, dataType = "String")
	})
	public Response delete(String ids){
		Boolean isOk = appService.deleteByIds(ids);
		return SuccessResponse.newInstance(isOk?"删除成功！":"删除失败！");
	}
	
	/**
	 * 分页查询
	 * 
	 * @param pageDto
	 * @return
	 */
	@RequestMapping(value="/findByPage",method={RequestMethod.GET})
	@ApiOperation(value = "分页查询应用", notes = "分页查询应用",httpMethod="GET")
	public Response findByPage(AppDto dto,PageVO pageVo){
		return SuccessResponseData.newInstance(appService.findByPage(dto,pageVo));
	}
}
