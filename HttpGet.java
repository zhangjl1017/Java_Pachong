package com.main;

import java.io.*;
import java.net.*;
import java.util.*;

public class HttpGet {

    public final static boolean DEBUG = true;// 调试用
	private static int BUFFER_SIZE = 8096;// 缓冲区大小
	private Vector<String> vDownLoad = new Vector<String>();// URL列表
	private Vector<String> vFileList = new Vector<String>();// 下载后的保
	private Vector<String> vPath = new Vector<String>();// 下载后的保存路径

	public HttpGet() {
	}

	public void resetList() {
		vDownLoad.clear();
		vFileList.clear();
	}

	public void addItem(String url, String filename,String basePath) {
		vDownLoad.add(url);
		vFileList.add(filename);
		vPath.add(basePath);
	}

	public void downLoadByList() {
		String url = null;
		String filename = null;
		String path = null;
		// 按列表顺序保存资源
		for (int i = 0; i < vDownLoad.size(); i++) {
			url = (String) vDownLoad.get(i);
			filename = (String) vFileList.get(i);
			path = (String)vPath.get(i);
			try {
				saveToFile(url, filename,path);
			} catch (IOException err) {
				if (DEBUG) {
					System.out.println("资源[" + url + "]下载失败!!!");
				}
			}
		}

		if (DEBUG) {
			System.out.println("下载完成!!!");
		}
	}

	public void saveToFile(String destUrl, String fileName,String path)

	throws IOException {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		} else {
		}
		// 建立链接
		url = new URL(destUrl);
		httpUrl = (HttpURLConnection) url.openConnection();
		// 连接指定的资源
		httpUrl.connect();
		// 获取网络输入流
		bis = new BufferedInputStream(httpUrl.getInputStream());
		// 建立文件
		fos = new FileOutputStream(path+"\\"+ fileName);

		if (this.DEBUG)
			System.out.println("正在获取链接[" + destUrl + "]的内容...\n将其保存为文件["
					+ fileName + "]");

		// 保存文件
		while ((size = bis.read(buf)) != -1)
			fos.write(buf, 0, size);
		fos.flush();
		fos.close();
		bis.close();
		httpUrl.disconnect();
	}

	public void setProxyServer(String proxy, String

	proxyPort) {
		// 设置代理服务器
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", proxy);
		System.getProperties().put("proxyPort", proxyPort);
	}

	/*
	 * public void setAuthenticator(String uid, String pwd) {
	 * Authenticator.setDefault(new MyAuthenticator(uid, pwd)); }
	 */

	/*public static void main(String argv[]) {
		HttpGet oInstance = new HttpGet();
		try {
			// 增加下载列表（此处用户可以写入自己代码来增加下载列

			oInstance.addItem("http://img3.cache.netease.com/cnews/2015/9/4/2015090409201428099.jpg","[临时报告]欣威视通:公开转让说明书（更正公告）.jpg");
			// 开始下载
			oInstance.downLoadByList();
		} catch (Exception err) {
			System.out.println(err.getMessage());
		}
	}*/
}