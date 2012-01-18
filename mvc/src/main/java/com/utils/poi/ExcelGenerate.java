package com.utils.poi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelGenerate {
	private static Map<String, CellStyle> styles;
	private static int rowIndex = 0;
	public static Integer STRING = 1;
	public static Integer NUMBER = 2;
	public static Integer DATE = 3;

	public static Workbook excelWrite(Excel excel) {
		// 创建Workbook
		Workbook wb = new HSSFWorkbook();
		// 创建所有Cell Style
		createStyles(wb);
		// 创建工作表.
		Sheet s = wb.createSheet(excel.getTitle());
		// 设定所有Column宽度自动配合内容宽度
		int clos = excel.getExcelRows().get(0).getExcelCells().size();
		
		for (int i = 0; i < clos; i++) {
			s.autoSizeColumn(i);
		}

		// 产生标题
		generateTitle(s, clos - 1, excel.getTitle());
		// 产生内容
		generateContent(excel.getExcelRows(), s);
		return wb;
	}

	/**
	 * 生成title
	 * 
	 * @param s
	 * @param enCol
	 * @param title
	 */
	private static void generateTitle(Sheet s, int enCol, String title) {
		Row r = s.createRow(rowIndex++);
		Cell c1 = r.createCell(0);
		c1.setCellValue(title);
		c1.setCellStyle(styles.get("header"));
		// 合并单元格
		s.addMergedRegion(new CellRangeAddress(0, 0, 0, enCol));
	}

	/**
	 * 生成表格
	 * 
	 * @param excelRows
	 * @param s
	 */
	private static void generateContent(List<ExcelRow> excelRows, Sheet s) {
		CellStyle dateCellStyle = styles.get("dateCell");
		CellStyle numberCellStyle = styles.get("numberCell");
		CellStyle stringCellStyle = styles.get("stringCell");
		for (ExcelRow row : excelRows) {
			Row r = s.createRow(rowIndex++);
			int index = 0;
			Cell ce = null;
			for (ExcelCell cell : row.getExcelCells()) {
				ce = r.createCell(index);
				ce
						.setCellValue(cell.getValue() == null ? "-" : cell
								.getValue());
				s.autoSizeColumn(index++, true);
				if (cell.getType() == DATE) {
					ce.setCellStyle(dateCellStyle);
				} else if (cell.getType() == NUMBER) {
					ce.setCellStyle(numberCellStyle);
				} else if (cell.getType() == STRING) {
					ce.setCellStyle(stringCellStyle);
				}
			}
		}
		rowIndex = 0;
	}

	private static Map<String, CellStyle> createStyles(Workbook wb) {
		styles = new HashMap<String, CellStyle>();
		DataFormat df = wb.createDataFormat();

		// --字体设定 --//

		// 普通字体
		Font normalFont = wb.createFont();
		normalFont.setFontHeightInPoints((short) 10);

		// 加粗字体
		Font boldFont = wb.createFont();
		boldFont.setFontHeightInPoints((short) 10);
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// 蓝色加粗字体
		Font blueBoldFont = wb.createFont();
		blueBoldFont.setFontHeightInPoints((short) 10);
		blueBoldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		blueBoldFont.setColor(IndexedColors.BLUE.getIndex());

		// --Cell Style设定-- //

		// 标题格式
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setFont(boldFont);
		setBorder(headerStyle);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styles.put("header", headerStyle);

		// 日期格式
		CellStyle dateCellStyle = wb.createCellStyle();
		dateCellStyle.setFont(normalFont);
		dateCellStyle.setDataFormat(df.getFormat("yyyy-MM-dd"));
		dateCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		setBorder(dateCellStyle);
		styles.put("dateCell", dateCellStyle);

		// 数字格式
		CellStyle numberCellStyle = wb.createCellStyle();
		numberCellStyle.setFont(normalFont);
		numberCellStyle.setDataFormat(df.getFormat("#,##0.00"));
		numberCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		setBorder(numberCellStyle);
		styles.put("numberCell", numberCellStyle);

		// 字符格式
		CellStyle stringCellStyle = wb.createCellStyle();
		stringCellStyle.setFont(normalFont);
		stringCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		setBorder(stringCellStyle);
		styles.put("stringCell", stringCellStyle);

		// 合计列格式
		CellStyle totalStyle = wb.createCellStyle();
		totalStyle.setFont(blueBoldFont);
		totalStyle.setWrapText(true);
		totalStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		setBorder(totalStyle);
		styles.put("total", totalStyle);
		return styles;
	}

	private static void setBorder(CellStyle style) {
		// 设置边框
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	}
	
	/**
	 * Excel单元格
	 * 
	 * @author Administrator
	 * 
	 */
	public class ExcelCell {
		private Integer type = 0;
		private String value = "";

		public ExcelCell(String value) {
			this.value = value;
			this.type = 1;
		}

		public ExcelCell(String value, Integer type) {
			this.value = value;
			this.type = type;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}
	}

	/**
	 * Excel行
	 * 
	 * @author Administrator
	 * 
	 */
	public class ExcelRow {
		private List<ExcelCell> excelCells;
		
		public ExcelRow(){}

		/*
		 * 构造函数
		 */
		public ExcelRow(String[] title) {
			List<ExcelCell> list=new ArrayList<ExcelCell>();
			for (String o : title) {
				list.add(new ExcelCell(o));
			}
			setExcelCells(list);
		}
		
		public List<ExcelCell> getExcelCells() {
			return excelCells;
		}

		public void setExcelCells(List<ExcelCell> excelCells) {
			this.excelCells = excelCells;
		}
	}

	/**
	 * Excel
	 * 
	 * @author Administrator
	 * 
	 */
	public class Excel {
		private String title = "";
		private String other = "";
		private List<ExcelRow> excelRows;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getOther() {
			return other;
		}

		public void setOther(String other) {
			this.other = other;
		}

		public List<ExcelRow> getExcelRows() {
			return excelRows;
		}

		public void setExcelRows(List<ExcelRow> excelRows) {
			this.excelRows = excelRows;
		}
	}
}
