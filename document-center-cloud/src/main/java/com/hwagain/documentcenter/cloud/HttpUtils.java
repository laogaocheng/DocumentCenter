package com.hwagain.documentcenter.cloud;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class HttpUtils {
	public static final String CHARSET = "UTF-8";
	private static RequestConfig requestConfig = RequestConfig.custom()  
				            .setSocketTimeout(15000)  
				            .setConnectTimeout(15000) 
				            .setConnectionRequestTimeout(15000) 
				            .build();  	

	// 发送get请求 url?a=x&b=xx形式
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlName = "";
			if (param.length() != 0) {
				urlName = url + "?" + param;
			} else
				urlName = url;
			URL resUrl = new URL(urlName);
			URLConnection urlConnec = resUrl.openConnection();
			urlConnec.setRequestProperty("accept", "*/*");
			urlConnec.setRequestProperty("connection", "Keep-Alive");
			urlConnec.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			urlConnec.connect();
			Map<String, List<String>> map = urlConnec.getHeaderFields();
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(urlConnec.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送get请求失败" + e);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 发送post请求
	public static String sendPost(String url, MultipartHttpServletRequest param) {
		String result = "";
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URL resUrl = new URL(url);
			URLConnection urlConnec = resUrl.openConnection();
			urlConnec.setRequestProperty("accept", "*/*");
			urlConnec.setRequestProperty("connection", "Keep-Alive");
			urlConnec.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			urlConnec.setDoInput(true);
			urlConnec.setDoOutput(true);

			out = new PrintWriter(urlConnec.getOutputStream());
			out.print(param);// 发送post参数
			out.flush();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(urlConnec.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("post请求发送失败" + e);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// post请求方法
	public static String sendPost(String url, Map<String, Object> params) {
		String response = null;
		System.out.println(url);
		System.out.println(params);
		try {
			List<NameValuePair> pairs = null;
			if (params != null && !params.isEmpty()) {
				pairs = new ArrayList<NameValuePair>(params.size());
				for (String key : params.keySet()) {
					pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
				}
			}
			CloseableHttpClient httpclient = null;
			CloseableHttpResponse httpresponse = null;
			try {
				httpclient = HttpClients.createDefault();
				HttpPost httppost = new HttpPost(url);
				// StringEntity stringentity = new StringEntity(data);
				if (pairs != null && pairs.size() > 0) {
					httppost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
				}
				httpresponse = httpclient.execute(httppost);
				response = EntityUtils.toString(httpresponse.getEntity());
				System.out.println(response);
			} finally {
				if (httpclient != null) {
					httpclient.close();
				}
				if (httpresponse != null) {
					httpresponse.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static String sendPost(String url, String jsonString) {
		String response = null;
		System.out.println(url);
		System.out.println(jsonString);
		try {
			CloseableHttpClient httpclient = null;
			CloseableHttpResponse httpresponse = null;
			try {
				httpclient = HttpClients.createDefault();
				HttpPost httppost = new HttpPost(url);
				StringEntity stringentity = new StringEntity(jsonString, CHARSET);
				httppost.setEntity(stringentity);
				httpresponse = httpclient.execute(httppost);
				response = EntityUtils.toString(httpresponse.getEntity());
				System.out.println(response);
			} finally {
				if (httpclient != null) {
					httpclient.close();
				}
				if (httpresponse != null) {
					httpresponse.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static String sendPost(String url, HttpEntity reqEntity) {
		String response = null;
		try {
			CloseableHttpClient httpclient = null;
			CloseableHttpResponse httpresponse = null;
			try {
				httpclient = HttpClients.createDefault();
				HttpPost httppost = new HttpPost(url);
				httppost.setEntity(reqEntity);
				httpresponse = httpclient.execute(httppost);
				response = EntityUtils.toString(httpresponse.getEntity());
				System.out.println(response);
			} finally {
				if (httpclient != null) {
					httpclient.close();
				}
				if (httpresponse != null) {
					httpresponse.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static String PostFile(String url, String path) {
		// 1. 创建上传需要的元素类型
		File file = new File(path);
		FileBody fileBody = new FileBody(file);

		// 2. 将所有需要上传元素打包成HttpEntity对象
		HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("FileData", fileBody).build();

		return sendPost(url, reqEntity);
	}

	public static String PostData(String url, byte[] data) {
		String response = null;
		try {
			CloseableHttpClient httpclient = null;
			CloseableHttpResponse httpresponse = null;
			try {
				ByteArrayEntity arrayEntity = new ByteArrayEntity(data);
				HttpPost httppost = new HttpPost(url);
				httppost.setEntity(arrayEntity);
				//创建默认的httpClient实例
				httpclient = HttpClients.createDefault();
				httppost.setConfig(requestConfig);
				//执行请求
				httpresponse = httpclient.execute(httppost);

				response = EntityUtils.toString(httpresponse.getEntity());
				//responseContent = EntityUtils.toByteArray(entity);
				System.out.println(response);
			} finally {
				if (httpclient != null) {
					httpclient.close();
				}
				if (httpresponse != null) {
					httpresponse.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}