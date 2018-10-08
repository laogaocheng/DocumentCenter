package com.hwagain.documentcenter.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hwagain.documentcenter.EnumPermission;
import com.hwagain.documentcenter.TokenInfo;
import com.hwagain.documentcenter.dto.SearchFileDto;
import com.hwagain.documentcenter.dto.UpdateShareDto;
import com.hwagain.documentcenter.service.*;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/doc", method = { RequestMethod.GET, RequestMethod.POST })
@Api(value = "文档中心管理", description = "文档中心管理")
public class DocumentController extends BaseController {
	@Autowired
	IDocumentService documentService;
	@Autowired
	IAppService appService;

	@RequestMapping(value = "/getToken", method = { RequestMethod.GET })
	@ApiOperation(value = "获取 access token", notes = "获取授权访问凭证", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "appId", value = "App ID", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "secret", value = "访问密钥", paramType = "query", required = true, dataType = "String") })
	public Response getToken(String appId, String secret) {
		return SuccessResponseData.newInstance(documentService.getToken(appId, secret, getRequest().getRemoteAddr()));
	}

	@RequestMapping(value = "/directory/getDirectoryInfo", method = { RequestMethod.GET })
	@ApiOperation(value = "获取目录信息", notes = "获取目录信息", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "path", value = "目录路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response getDirectoryInfo(String token, String path, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.READ);
		// 返回目录信息
		return SuccessResponseData.newInstance(documentService.getDirectoryInfo(appId, path));
	}

	@RequestMapping(value = "/directory/create", method = { RequestMethod.GET })
	@ApiOperation(value = "创建目录", notes = "创建目录", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "path", value = "目录路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response createDirectory(String token, String path, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.WRITE);
		return SuccessResponseData.newInstance(documentService.createDirectory(appId, path));
	}

	@RequestMapping(value = "/directory/delete", method = { RequestMethod.GET })
	@ApiOperation(value = "删除目录", notes = "删除目录", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "path", value = "目录路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "recursive", value = "是否删除子目录和文件", paramType = "query", required = false, dataType = "boolean"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response deleteDirectory(String token, String path, boolean recursive, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.DELETE);

		documentService.deleteDirectory(appId, path, recursive);
		return SuccessResponseData.newInstance("删除成功");
	}

	@RequestMapping(value = "/directory/rename", method = { RequestMethod.GET })
	@ApiOperation(value = "目录重命名", notes = "目录重命名", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "srcPath", value = "要改名的目录的绝对路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "dstPath", value = "新路径（绝对路径或相对路径）", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response renameDirectory(String token, String srcPath, String dstPath, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.REVISE);
		return SuccessResponseData.newInstance(documentService.renameDirectory(appId, srcPath, dstPath));
	}

	@RequestMapping(value = "/directory/moveTo", method = { RequestMethod.GET })
	@ApiOperation(value = "移动目录", notes = "移动目录", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "srcPath", value = "要移动的目录的绝对路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "dstPath", value = "要将此目录移动到的目标位置的名称和路径。 目标不能是另一个具有相同名称的目录。 它可以是你要将此目录作为子目录添加的某个现有目录。", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "overWrite", value = "是否覆盖", paramType = "query", required = false, dataType = "boolean"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response moveToDirectory(String token, String srcPath, String dstPath, boolean overWrite, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.REVISE);
		return SuccessResponseData.newInstance(documentService.moveToDirectory(appId, srcPath, dstPath, overWrite));
	}

	@RequestMapping(value = "/directory/copyTo", method = { RequestMethod.GET })
	@ApiOperation(value = "复制到目录", notes = "复制到目录", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "srcPath", value = "要复制的目录的绝对路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "dstPath", value = "要将此目录复制到的目标位置的名称和路径。 目标不能是另一个具有相同名称的目录。 它可以是你要将此目录作为子目录添加的某个现有目录。", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "overWrite", value = "是否覆盖", paramType = "query", required = false, dataType = "boolean"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response copyToDirectory(String token, String srcPath, String dstPath, boolean overWrite, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.WRITE);
		if (overWrite) {
			tokenInfo.check(appId, EnumPermission.REVISE);
		}
		return SuccessResponseData.newInstance(documentService.copyToDirectory(appId, srcPath, dstPath, overWrite));
	}

	@RequestMapping(value = "/directory/getFiles", method = { RequestMethod.GET })
	@ApiOperation(value = "获取目录中的文件", notes = "获取目录中的文件", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "path", value = "目录的绝对路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "searchAllDirectories", value = "搜索当前目录和所有它的子目录", paramType = "query", required = false, dataType = "boolean"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response getFiles(String token, String path, boolean searchAllDirectories, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.LIST);

		if (searchAllDirectories)
			return SuccessResponseData.newInstance(documentService.getAllFiles(appId, path));
		else
			return SuccessResponseData.newInstance(documentService.getFiles(appId, path));
	}

	@RequestMapping(value = "/directory/getDirectories", method = { RequestMethod.GET })
	@ApiOperation(value = "获取当前目录中的子目录", notes = "获取指定目录中的子目录", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "path", value = "目录的绝对路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "searchAllDirectories", value = "搜索当前目录和所有它的子目录", paramType = "query", required = false, dataType = "boolean"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response getDirectories(String token, String path, boolean searchAllDirectories, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.LIST);

		if (searchAllDirectories)
			return SuccessResponseData.newInstance(documentService.getAllDirectories(appId, path));
		else
			return SuccessResponseData.newInstance(documentService.getDirectories(appId, path));
	}

	@RequestMapping(value = "/directory/createSubdirectory", method = { RequestMethod.GET })
	@ApiOperation(value = "创建子目录", notes = "创建子目录", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "parentPath", value = "父目录路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "path", value = "子目录路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response createSubdirectory(String token, String parentPath, String path, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.WRITE);

		return SuccessResponseData.newInstance(documentService.createSubdirectory(appId, parentPath, path));
	}

	@RequestMapping(value = "/directory/search", method = { RequestMethod.POST })
	@ApiOperation(value = "搜索文件", notes = "搜索文件", httpMethod = "POST")
	public Response search(@RequestBody SearchFileDto dto) {
		TokenInfo tokenInfo = documentService.getTokenInfo(dto.getToken());
		String appId = dto.getAppId();
		String path = dto.getPath();
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (dto.getAppId().equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.READ);

		return SuccessResponseData.newInstance(documentService.search(appId, path, dto));
	}

	// -----------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/file/getFileInfo", method = { RequestMethod.GET })
	@ApiOperation(value = "获取文件信息", notes = "获取文件信息", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "path", value = "文件路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response getFileInfo(String token, String path, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.READ);

		// 返回文件信息
		return SuccessResponseData.newInstance(documentService.getFileInfo(appId, path));
	}

	@RequestMapping(value = "/file/delete", method = { RequestMethod.GET })
	@ApiOperation(value = "删除文件", notes = "删除文件", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "path", value = "文件路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response deleteFile(String token, String path, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.DELETE);

		documentService.deleteFile(appId, path);
		return SuccessResponseData.newInstance("删除成功");
	}

	@RequestMapping(value = "/file/rename", method = { RequestMethod.GET })
	@ApiOperation(value = "文件重命名", notes = "文件重命名", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "srcPath", value = "文件路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "dstPath", value = "新文件名（可以加子目录路径）", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response renameFile(String token, String srcPath, String dstPath, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.REVISE);
		return SuccessResponseData.newInstance(documentService.renameFile(appId, srcPath, dstPath));
	}

	@RequestMapping(value = "/file/moveTo", method = { RequestMethod.GET })
	@ApiOperation(value = "文件移动", notes = "文件移动", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "srcPath", value = "文件路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "dstPath", value = "新文件名（可以加目录路径）", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "overWrite", value = "是否覆盖", paramType = "query", required = false, dataType = "boolean"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response moveToFile(String token, String srcPath, String dstPath, boolean overWrite, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.REVISE);
		return SuccessResponseData.newInstance(documentService.moveToFile(appId, srcPath, dstPath, overWrite));
	}

	@RequestMapping(value = "/file/copyTo", method = { RequestMethod.GET })
	@ApiOperation(value = "复制文件", notes = "复制文件", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "srcPath", value = "要复制的文件路径（包含文件名））", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "dstPath", value = "新路径（包含文件名）", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "overWrite", value = "是否覆盖", paramType = "query", required = false, dataType = "boolean"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response copyToFile(String token, String srcPath, String dstPath, boolean overWrite, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.WRITE);
		if (overWrite) {
			tokenInfo.check(appId, EnumPermission.REVISE);
		}
		return SuccessResponseData.newInstance(documentService.copyToFile(appId, srcPath, dstPath, overWrite));
	}

	@RequestMapping(value = "/file/getDownloadUrl", method = { RequestMethod.GET })
	@ApiOperation(value = "获取文件下载地址", notes = "获取文件下载地址", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "path", value = "文件路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "timeOutMinites", value = "下载链接多少分钟后过期，默认永不过期", paramType = "query", required = false, dataType = "String"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response getDownloadUrl(String token, String path, String timeOutMinites, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.READ);

		if (timeOutMinites == null || timeOutMinites.equals(""))
			return SuccessResponseData.newInstance(documentService.getDownloadUrl(appId, path));
		else
			return SuccessResponseData
					.newInstance(documentService.getDownloadUrl(appId, path, Integer.parseInt(timeOutMinites)));
	}

	@RequestMapping(value = "/file/upload", method = { RequestMethod.POST })
	@ApiOperation(value = "上传文件", notes = "上传文件", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "path", value = "目录路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "filename", value = "文件名（含扩展名）", paramType = "query", required = false, dataType = "String"),
			@ApiImplicitParam(name = "overWrite", value = "是否覆盖", paramType = "query", required = false, dataType = "boolean"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response uploadFile(String token, String path, String filename, HttpServletRequest request,
			MultipartFile multipartFile, boolean overWrite, String appId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = request.getRemoteAddr();
		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		Assert.isTrue(ServletFileUpload.isMultipartContent(request), "缺少文件内容！");
		tokenInfo.check(appId, EnumPermission.WRITE);
		if (overWrite) {
			tokenInfo.check(appId, EnumPermission.REVISE);
		}

		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		// 获取multiRequest 中所有的文件名
		Iterator<String> iter = multiRequest.getFileNames(); // 表单里可以提交了多个附件控件
		if (iter.hasNext()) {
			String formFileName = iter.next().toString();// 附件控件的名字
			List<MultipartFile> files = multiRequest.getFiles(formFileName);// 获取相应名字的所有附件
			if (files != null && files.size() > 0) {
				// 遍历附件并上传
				for (MultipartFile file : files) {
					System.err.println(file.getOriginalFilename() + "=====");

					if (filename == null || filename.equals(""))
						filename = file.getOriginalFilename();

					filename = filename.replaceAll("\\\\", "/");
					filename = StringUtils.strip(filename, "/\\");
					path = path.replaceAll("\\\\", "/");
					path = StringUtils.strip(path, "/\\");
					path = path + "/" + filename;

					try {
						return SuccessResponseData
								.newInstance(documentService.uploadFile(appId, path, file.getBytes(), overWrite));
					} catch (IOException e) {
						return SuccessResponseData.newInstance(e);
					}
				}
			}
		}
		return SuccessResponseData.newInstance("没有上传任何文件");
	}

	@RequestMapping(value = "/createShare", method = { RequestMethod.GET })
	@ApiOperation(value = "分享文件或目录", notes = "分享文件或目录", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "path", value = "目录或文件路径", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "expiredTime", value = "过期时间", paramType = "query", required = false, dataType = "Date"),
			@ApiImplicitParam(name = "appId", value = "appId。默认是获取 token 对应的 appId", paramType = "query", required = false, dataType = "String"), })
	public Response createShare(String token, String path, String expiredTime, String appId) throws ParseException {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();

		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		if (appId == null || appId.equals(""))
			appId = tokenInfo.getAppId();

		long expires = System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000;
		if (expiredTime != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = simpleDateFormat.parse(expiredTime);
			expires = date.getTime();
		}
		Assert.isTrue(appService.findOne(appId) != null, "指定的应用不存在！");
		tokenInfo.check(appId, EnumPermission.SHARE);

		return SuccessResponseData.newInstance(documentService.createShare(appId, path, 1, expires));
	}

	// -----------------------------------------------------------------------------------------

	@RequestMapping(value = "/updateShare", method = { RequestMethod.POST })
	@ApiOperation(value = "修改分享限制", notes = "修改分享限制", httpMethod = "POST")
	public Response updateShare(@RequestBody UpdateShareDto dto) {
		TokenInfo tokenInfo = documentService.getTokenInfo(dto.getToken());
		String requestIP = getRequest().getRemoteAddr();
		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		documentService.updateShare(dto);
		return SuccessResponseData.newInstance("修改分享限制成功");
	}

	@RequestMapping(value = "/deleteShare", method = { RequestMethod.GET })
	@ApiOperation(value = "删除分享", notes = "删除分享", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "授权凭证", paramType = "query", required = true, dataType = "String"),
			@ApiImplicitParam(name = "shareId", value = "分享 id", paramType = "query", required = true, dataType = "String") })
	public Response deleteShare(String token, String shareId) {
		TokenInfo tokenInfo = documentService.getTokenInfo(token);
		String requestIP = getRequest().getRemoteAddr();
		Assert.isTrue(tokenInfo.isValid(requestIP), "token 已失效，请重新获取");

		documentService.deleteShare(shareId);
		return SuccessResponseData.newInstance("删除分享成功");
	}
}
