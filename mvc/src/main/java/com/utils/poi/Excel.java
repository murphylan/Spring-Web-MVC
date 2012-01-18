package com.utils.poi;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class Excel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// String output =
		// ServletRequestUtils.getStringParameter(request, "output");

		List<Map<String, String>> body = (List<Map<String, String>>) model
				.get("dataDto");
		// create a wordsheet
		HSSFSheet sheet = workbook.createSheet("Demo");

		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("First");
		header.createCell(1).setCellValue("Second");

		int rowNum = 1;
		for (Map<String, String> o : body) {
			for (Map.Entry<String, String> entry : o.entrySet()) {
				// create the row data
				HSSFRow row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(entry.getKey());
				row.createCell(1).setCellValue(entry.getValue());
			}
		}

	}

}