/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.wuji.authority.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Yayun
 *
 */
public class ExportCSVUtil {

	public static void downloadCSVFile(String fileName, Map<String, String> headMap, JSONArray jsonArray,
			HttpServletResponse response) {
		PrintWriter out = null;
		// 这是写入CSV的代码
		try {
			out = response.getWriter();
			// 设置response参数，可以打开下载页面
			response.reset();

			response.setContentType("application/csv;charset=UTF-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((fileName + ".csv").getBytes(), "iso-8859-1"));
			// response.setContentLength(content.length);
			exportCSV(headMap, jsonArray, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	public static void exportCSV(Map<String, String> headMap, JSONArray jsonArray, PrintWriter out) {
		CSVPrinter printer = null;
		try {
			Set<Entry<String, String>> entrySet = headMap.entrySet();
			String[] file_header = new String[headMap.size()];
			String[] field = new String[headMap.size()];
			int index = 0;
			for (Entry<String, String> entry : entrySet) {
				field[index] = entry.getKey();
				file_header[index] = entry.getValue();
				index++;
			}
			// Writer out = new FileWriter(title);
			// 这里显式地配置一下CSV文件的Header，然后设置跳过Header（要不然读的时候会把头也当成一条记录）
			CSVFormat format = CSVFormat.EXCEL.withHeader(file_header);
			printer = new CSVPrinter(out, format);
			for (Object obj : jsonArray) {
				JSONObject json = (JSONObject) JSONObject.toJSON(obj);
				List<String> records = new ArrayList<>();
				for (int i = 0; i < field.length; i++) {
					records.add(json.get(field[i]).toString());
				}
				printer.printRecord(records);
			}
			// 这是从上面写入的文件中读出数据的代码
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (printer != null) {
				try {
					printer.flush();
					printer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		new ExportCSVUtil().run();
	}

	public void run() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileOutputStream("e:/temp/test.csv"));
			List users = new ArrayList<UserVo>();
			for (int i = 0; i < 5; i++) {
				UserVo user = new UserVo();
				user.setUserName("test" + i);
				user.setNickName("张三" + i);
				user.setPassword("test");
				users.add(user);
			}
			JSONArray jsonArray = new JSONArray(users);
			System.out.println(jsonArray);
			Map<String, String> headMap = new LinkedHashMap<String, String>();
			headMap.put("userName", "userName");
			headMap.put("nickName", "nickName");
			headMap.put("password", "password");
			exportCSV(headMap, jsonArray, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	class UserVo implements Serializable {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		private String userName;

		private String nickName;

		private String password;

		public UserVo() {
		}

		public String getUserName() {
			return this.userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getNickName() {
			return this.nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		public String getPassword() {
			return this.password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}
}
