package com.hwagain.documentcenter.service;

import java.util.List;

import com.hwagain.documentcenter.TokenInfo;
import com.hwagain.documentcenter.dto.*;
import com.hwagain.framework.core.exception.CustomException;

public interface IDocumentService {
	public String getToken(String appId, String secret, String ip)  throws CustomException; 
	public TokenInfo getTokenInfo(String token) throws CustomException;
	public CloudDirectoryDto createDirectory(String appId, String path)  throws CustomException; 
	public void deleteDirectory(String appId, String path, boolean recursive)  throws CustomException;
	public CloudDirectoryDto getDirectoryInfo(String appId, String path)  throws CustomException;
	public CloudDirectoryDto renameDirectory(String appId, String srcPath, String dstPath)  throws CustomException; 
	public CloudDirectoryDto moveToDirectory(String appId, String srcPath, String dstPath, boolean overWrite)  throws CustomException;
	public CloudDirectoryDto copyToDirectory(String appId, String srcPath, String dstPath, boolean overWrite) throws CustomException;
	public List<CloudFileDto> getFiles(String appId, String path)  throws CustomException;
	public List<CloudFileDto> getAllFiles(String appId, String path)  throws CustomException;	
	public List<CloudDirectoryDto> getDirectories(String appId, String path)  throws CustomException;
	public List<CloudDirectoryDto> getAllDirectories(String appId, String path)  throws CustomException;
	public CloudDirectoryDto createSubdirectory(String appId, String parentPath, String path)  throws CustomException;
	public List<CloudFileDto> search(String appId, String path, SearchFileDto searchFileDto)  throws CustomException;
	
	public CloudFileDto getFileInfo(String appId, String path)  throws CustomException;
	public void deleteFile(String appId, String path)  throws CustomException;
	public CloudFileDto renameFile(String appId, String path, String dstPath)  throws CustomException; 
	public CloudFileDto moveToFile(String appId, String srcPath, String dstPath, boolean overWrite)  throws CustomException;
	public CloudFileDto copyToFile(String appId, String srcPath, String dstPath, boolean overWrite) throws CustomException;
	public String getDownloadUrl(String appId, String path, int timeOutMinites)  throws CustomException;	
	public String getDownloadUrl(String appId, String path)  throws CustomException;
	
	public CloudFileDto uploadFile(String appId, String path, byte[] fileData, boolean overWrite)  throws CustomException;
	public ShareDto createShare(String appId, String path, int type, long expires)  throws CustomException;
	public void updateShare(UpdateShareDto dto)  throws CustomException;
	public void deleteShare(String shareId)  throws CustomException;
}
