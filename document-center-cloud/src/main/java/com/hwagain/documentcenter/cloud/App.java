package com.hwagain.documentcenter.cloud;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Throwable {
		
		AppInfo user = new AppInfo("3624952399109");
//
//		CloudFile file = new CloudFile(user, "/ABC/test.txt");
//		//file.moveTo("/ABC/test.txt", true);
//		System.out.println("下载地址：" + file.getDownloadUrl(20));
		
//		String password = EncryptUtil.encodeBASE64(EncryptUtil.d3esEncode("劳高成"));
//		System.out.println(password);
//
//		password = EncryptUtil.d3esDecode(EncryptUtil.decodeBASE64(password));
//		System.out.println(password);

		
		CloudDirectory dir = new CloudDirectory(user, "/新建文件夹");
//		dir.copyTo("/移动1/新目录1/ccc/", true);
//		
		System.out.println("路径：" + dir.getFullName());
		
		
//		for(CloudDirectory subdir : dir.getAllDirectories())
//		{
//			//System.out.println("父目录：" + subdir.getParent().getFullName());
//			System.out.println("路径：" + subdir.getFullName());
//			//System.out.println("名称：" + subdir.getName());
//		}
		
//		for(CloudFile file : dir.getAllFiles())
//		{
//			System.out.println("目录：" + file.getDirectory().getFullName());
//			System.out.println("路径：" + file.getFullName());
//			System.out.println("扩展名：" + file.getExtension());
//			System.out.println("长度：" + file.getLength());
//		}
		
//		CloudFile file = new CloudFile(user, "/HTTP依赖jar包.zip");
//		System.out.println(file.getLastAccessTime());
//		System.out.println(file.getLastWriteTime());
//		System.out.println(file.getDirectory().getFullName());
//		System.out.println(file.getFullName());
//		System.out.println(file.getExtension());
//		System.out.println(file.getLength());
		
//		CloudDirectory dir = new CloudDirectory(user, "My Folder\\SSSS");
//		dir.delete();
//		dir = dir.createSubdirectory("子目录4");
//		System.out.println(dir.getLastAccessTime());
//		System.out.println(dir.getLastWriteTime());
//		System.out.println(dir.getParent().getFullName());
//		System.out.println(dir.getFullName());
		
		//InfoByFidResponse res = CloudDisk.infoByFid(3624952399109L);
		//System.out.println(res.getFileInfo());
				
//		SearchRequest req = new SearchRequest();
//		req.setKeyWord("Crack");
//		SearchResponse res = CloudDisk.search(req);
//		System.out.println(res.getTotal());
		
		//GetDownloadUrlResponse res = CloudDisk.getDownloadUrl("/test/予悦标志.ppt", 20);
		//System.out.println(res.getUrl());
		
		//File file = new File("D:\\Downloads\\TIM2.2.0.exe");
		
		//File file = new File("c:\\yk.log");
		//Boolean flag = CloudDisk.UploadFile("/test/test.txt", file);
		//System.out.println(flag);

		// RenameByPathResponse res =
		// CloudDisk.renameByPath("/其它/AllWebsites.dat", "/其它/AllWebsites.xml",
		// false);
		// System.out.println(res.getFileInfo().getCreator().toString());

		// FileInfoByPathResponse res =
		// CloudDisk.fileInfoByPath("/公文/基础开发平台介绍.pptx");
		// System.out.println(res.getFileInfo().getCreator().toString());

		// CopyByPathResponse res = CloudDisk.copyByPath("/其它/", "/other/",
		// true);
		// System.out.println(res.getFileInfo().getCreator().toString());

		 //MkdirByPathResponse res = CloudDisk.mkdirByPath("/劳高成1\\4");
		 //System.out.println(res.getStat());

		// RmByPathResponse res = CloudDisk.rmByPath("/other");
		// System.out.println(res.getStat());

		// RequestUploadByPathResponse
		// res=CloudDisk.requestUploadByPath("/test/test.txt", 1024, false,
		// null);
		// System.out.println(res.getStat());

		// File file = new File("D:\\Downloads\\TIM2.2.0.exe");
		// List<FilePart> parts = FilePart.getFilePartsInfo(file);
		// System.out.println(parts.get(0).getPartSha1());

		// byte[] buffer = "我是中国人".getBytes();
		// System.out.println(Base64Utils.encodeToString(buffer));
		/*
		 * MessageDigest digest; try { digest =
		 * MessageDigest.getInstance("SHA1"); byte[] sha1Bytes =
		 * digest.digest("我是中国人".getBytes());
		 * System.out.println(Base64Utils.encodeToString(sha1Bytes));
		 * 
		 * } catch (NoSuchAlgorithmException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */
	}
}
