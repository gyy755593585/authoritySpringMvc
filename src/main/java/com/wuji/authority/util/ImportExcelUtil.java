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

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.wuji.basic.model.SystemException;

/**
 * @author Yayun
 *
 */

public class ImportExcelUtil {

	/**
	 * 描述：获取IO流中的数据，组装成List<List<Object>>对象 titleLength: 如数据从第三开始 则titleLength为2
	 *
	 * @param in,fileName
	 * @return
	 * @throws IOException
	 */
	public static List<List<Object>> getBankListByExcel(InputStream in, int titleLength, String fileName) throws Exception {
		List<List<Object>> list = null;

		// 创建Excel工作薄
		Workbook work = getWorkbook(in, fileName);
		if (null == work) {
			throw new SystemException("创建Excel工作薄为空！");
		}
		if (!validateFileType(fileName)) {
			throw new SystemException("文件格式有误！");
		}
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;

		list = new ArrayList<List<Object>>();
		// 遍历Excel中所有的sheet
		for (int i = 0; i < work.getNumberOfSheets(); i++) {
			sheet = work.getSheetAt(i);
			if (sheet == null) {
				continue;
			}

			// 遍历当前sheet中的所有行
			for (int j = sheet.getFirstRowNum() + titleLength; j <= sheet.getLastRowNum(); j++) {
				row = sheet.getRow(j);
				if (row == null || row.getFirstCellNum() == j) {
					continue;
				}

				// 遍历所有的列
				List<Object> li = new ArrayList<Object>();
				for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
					cell = row.getCell(y);
					li.add(getCellValue(cell));
				}
				list.add(li);
			}
		}
		work.close();
		return list;
	}

	/**
	 * 描述：根据文件后缀，自适应上传文件的版本
	 *
	 * @param inStr,fileName
	 * @return
	 * @throws Exception
	 */
	public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
		Workbook wb = null;
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if (SystemConst.EXCEL_2003L.equals(fileType)) {
			wb = new HSSFWorkbook(inStr); // 2003-
		} else if (SystemConst.EXCEL_2007U.equals(fileType)) {
			wb = new XSSFWorkbook(inStr); // 2007+
		} else {
			throw new SystemException("解析的文件格式有误！");
		}
		return wb;
	}

	/**
	 * 描述：对表格中数值进行格式化
	 *
	 * @param cell
	 * @return
	 */
	public static Object getCellValue(Cell cell) {
		Object value = null;
		DecimalFormat df = new DecimalFormat("0"); // 格式化number String字符
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
		DecimalFormat df2 = new DecimalFormat("0.00"); // 格式化数字

		switch (cell.getCellTypeEnum()) {
			case STRING:
				value = cell.getRichStringCellValue().getString();
				break;
			case NUMERIC:
				if ("General".equals(cell.getCellStyle().getDataFormatString())) {
					value = df.format(cell.getNumericCellValue());
				} else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
					value = sdf.format(cell.getDateCellValue());
				} else {
					value = df2.format(cell.getNumericCellValue());
				}
				break;
			case BOOLEAN:
				value = cell.getBooleanCellValue();
				break;
			case BLANK:
				value = "";
				break;
			default:
				break;
		}
		return value;
	}

	private static boolean validateFileType(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return SystemConst.EXCEL_FILE_TYPE.contains(fileName.substring(pos + 1));
	}
}
