package com.hwagain.documentcenter.web;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.validation.util.ValidationUtil;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.documentcenter.dto.PermissionDto;
import com.hwagain.documentcenter.service.IPermissionService;
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
@RequestMapping(value="/doc/permission",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="文档中心权限管理",description="文档中心权限管理")
public class PermissionController extends BaseController{
	
	@Autowired
	IPermissionService permissionService;
	
	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@ApiOperation(value = "查詢权限列表", notes = "查詢权限列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(permissionService.findAll(),null,this.getValidation(new PermissionDto()),null);
	}
	
	/**
	 * 获取当前资源下的用户权限
	 * 
	 * @param dto
	 * @return
	 */
	public Map<String, Boolean> getValidation(PermissionDto dto) {
		String contextPath = getRequest().getContextPath();
		Map<String, Boolean> validURIMaps = ValidationUtil.validModuleOperator(contextPath, "/documentcenter/permission", dto);
		return validURIMaps;
	}
	
	/**
	 * 按ID查询数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/findOne",method={RequestMethod.GET})
	@ApiOperation(value = "按ID查询权限数据", notes = "按ID查询权限数据",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "数据ID", paramType = "query", required = false, dataType = "String")
	})
	public Response findOne(String fdId){
		return SuccessResponseData.newInstance(permissionService.findOne(fdId));
	}
	
	/**
	 * 新增数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/save",method={RequestMethod.POST})
	@ApiOperation(value = "新增权限数据", notes = "新增权限数据",httpMethod="POST")
	public Response save(@RequestBody PermissionDto dto){
		return SuccessResponseData.newInstance(permissionService.save(dto));
	}
	
	/**
	 * 修改数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ApiOperation(value = "修改权限数据", notes = "修改权限数据",httpMethod="POST")
	public Response update(@RequestBody PermissionDto dto){
		PermissionDto dto4fdid = permissionService.findOne(dto.getFdId());
		Assert.isTrue(dto4fdid != null, "权限记录不存在");		
		return SuccessResponseData.newInstance(permissionService.update(dto));
	}
	
	/**
	 * 删除数据，可批量
	 * 
	 * @param ids  格式：1;2;3;4....
	 * @return
	 */
	@RequestMapping(value="/delete",method={RequestMethod.GET})
	@ApiOperation(value = "删除权限数据", notes = "删除权限数据",httpMethod="GET")
		@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "数据集,格式：1;2;3;4....", paramType = "query", required = false, dataType = "String")
	})
	public Response delete(String ids){
		Boolean isOk = permissionService.deleteByIds(ids);
		return SuccessResponse.newInstance(isOk?"删除成功！":"删除失败！");
	}
	
	/**
	 * 分页查询
	 * 
	 * @param pageDto
	 * @return
	 */
	@RequestMapping(value="/findByPage",method={RequestMethod.GET})
	@ApiOperation(value = "分页查询权限数据", notes = "分页查询权限数据",httpMethod="GET")
	public Response findByPage(PermissionDto dto,PageVO pageVo){
		return SuccessResponseData.newInstance(permissionService.findByPage(dto,pageVo));
	}
}
