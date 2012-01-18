package com.utils.poi;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExeclPicture {

	public ExeclPicture() {
	}

	public static void main(String[] args) throws Exception {
		// 创建一个工作薄
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建一个表格
		HSSFSheet sheet = wb.createSheet("sheet1");
		sheet.setColumnWidth(4, 4400);
		// 创建一个列
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short)1550);
		// 创建一个样式
		HSSFCellStyle style = wb.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 创建一个字体
		HSSFFont font = wb.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 16);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 填充单元格
		for (int i = 0; i < 5; i++) {
			// 创建一个单元格
			HSSFCell cell = row.createCell(i);
			switch (i) {
			case 0:
				// 设置普通文本
				cell.setCellValue(new HSSFRichTextString("普通文本"));
				break;
			case 1:
				// 设置为形状
				HSSFClientAnchor a1 = new HSSFClientAnchor(0, 0, 1023, 255,
						(short) 1, 0, (short) 1, 0);
				HSSFSimpleShape shape1 = patriarch.createSimpleShape(a1);
				// 这里可以设置形状的样式
				shape1.setShapeType(HSSFSimpleShape.OBJECT_TYPE_OVAL);

				break;
			case 2:
				// 设置为布尔量
				cell.setCellValue(true);
				break;
			case 3:
				// 设置为double值
				cell.setCellValue(12.5);
				break;
			case 4:
				// 设置为图片]
				File bufferImg = new File("c:/ok.jpg");
				insertImage(wb, patriarch,
						getImageData(ImageIO.read(bufferImg)), 0, 4, 0);
				break;

			}

			// 设置单元格的样式
			cell.setCellStyle(style);
		}
		FileOutputStream fout = new FileOutputStream("c:/EXCEL.xls");
		// 输出到文件
		wb.write(fout);
		fout.close();
	}

	// 自定义的方法,插入某个图片到指定索引的位置
	private static void insertImage(HSSFWorkbook wb, HSSFPatriarch pa,
			byte[] data, int row, int column, int index) {
		int x1 = index * 250;
		int y1 = 0;
		int x2 = x1 + 0;
		int y2 = 255;
		HSSFClientAnchor anchor = new HSSFClientAnchor(x1, y1, x2, y2,
				(short) column, row, (short) (column+1), row);
		anchor.setAnchorType(0);
		pa.createPicture(anchor, wb.addPicture(data,
				HSSFWorkbook.PICTURE_TYPE_JPEG));
	}

	// 从图片里面得到字节数组
	private static byte[] getImageData(BufferedImage bi) {
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ImageIO.write(bi, "JPG", bout);
			return bout.toByteArray();
		} catch (Exception exe) {
			exe.printStackTrace();
			return null;
		}
	}
}
