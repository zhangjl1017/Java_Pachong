package com.main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class em {

    public static void main(String[] args) {
		FileReader reader;
		HttpGet httpGet = new HttpGet();
		try {
			reader = new FileReader("d://code.txt");
			BufferedReader br = new BufferedReader(reader);
			String str = null;
			while ((str = br.readLine()) != null) {
				String type = "1";
				String start = "2000-1-1";
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String end = formatter.format(new Date());
				String subtype = "0";
				String companycode = str;
				String key = "说明书";
				String a = "1";
				File file = new File("D:\\pdf\\" + companycode);
				String basePath = "D:\\pdf\\" + companycode;
				String url = "http://www.neeq.com.cn/controller/GetDisclosureannouncementPage";
				url += "?type=" + type + "&startDate=" + start + "&endDate="
						+ end + "&subType=" + subtype + "&company_cd="
						+ companycode + "&key=" + key + "&page=1";
				String jsonStr = getHtml(url);
			//	System.out.println(jsonStr);
				JSONObject jo = JSONObject.fromString(jsonStr);
				JSONArray ja = jo.getJSONArray("disclosureInfos");
				for (int i = 0; i < ja.length(); i++) {
					JSONObject jotemp = JSONObject.fromString(ja.get(i)
							.toString());
					String downloadurl = jotemp.get("filePath").toString();
					String fileName = jotemp.get("titleFull").toString().replace(":", "")
							+ ".pdf";
					downloadurl = "http://file.neeq.com.cn/upload"
							+ downloadurl;
					try {
						System.out.println(fileName);
						httpGet.addItem(downloadurl, fileName,basePath);
					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			System.out.println("success!");
			br.close();
			reader.close();
			httpGet.downLoadByList();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String getHtml(String urlString) {
		try {
			StringBuffer html = new StringBuffer();
			java.net.URL url = new java.net.URL(urlString);

			java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url
					.openConnection();

			java.io.InputStreamReader isr = new java.io.InputStreamReader(
					conn.getInputStream());
			java.io.BufferedReader br = new java.io.BufferedReader(isr);

			String temp;
			while ((temp = br.readLine()) != null) {
				if (!temp.trim().equals("")) {
					html.append(temp).append("\n");
				}
			}
			br.close();
			isr.close();
			return html.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	

}
