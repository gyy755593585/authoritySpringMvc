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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;

/**
 * @author Yayun
 *
 */
public class ImportCSVUtil {

	public static List<List<Object>> importCSVUtil(InputStream in, String[] header) {
		List<List<Object>> list = null;
		Reader reader = null;
		CSVParser parser = null;
		try {
			reader = new InputStreamReader(new BOMInputStream(in), "UTF-8");
			parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
			list = new ArrayList<List<Object>>();
			// 遍历所有的列
			List<Object> li = null;
			for (CSVRecord csvRecord : parser) {
				li = new ArrayList<Object>();
				for (String key : header) {
					li.add(csvRecord.get(key));
				}
				list.add(li);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (parser != null) {
					parser.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public static void main(String[] args) {
		List<List<Object>> list = null;
		File file = new File("e:/temp/test.csv");
		URI url = file.toURI();
		System.out.println(url.toString());
		try {
			URL url2 = url.toURL();
			System.out.println(url2.toString());
			// url2.openStream();

			list = importCSVUtil(url2.openStream(), new String[] { "userName", "nickName", "password" });
			for (List<Object> objects : list) {
				System.out.println(objects.get(0));
				System.out.println(objects.get(1));
				System.out.println(objects.get(2));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
