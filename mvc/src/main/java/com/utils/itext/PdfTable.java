package com.utils.itext;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 一个非常简单的PdfPTable的例子.
 */
public class PdfTable {

	/**
	 * @author sanshang
	 */
	public static void main(String[] args) {

		System.out.println("My First PdfPTable");

		// 步骤 1: 创建一个document对象
		Document document = new Document();

		try {
			// 步骤 2:
			// 我们为document创建一个监听，并把PDF流写到文件中
			PdfWriter.getInstance(document, new FileOutputStream(
					"c:/PdfTable.pdf"));

			// 步骤 3:打开文档
			document.open();
			// 创建一个有3列的表格
			PdfPTable table = new PdfPTable(3);
			// 定义一个表格单元
			PdfPCell cell = new PdfPCell(new Paragraph("header with colspan 3"));
			// 定义一个表格单元的跨度
			cell.setColspan(3);
			// 把单元加到表格中
			table.addCell(cell);
			// 把下面这9项顺次的加入到表格中，当一行充满时候自动折行到下一行
			table.addCell("1.1");
			table.addCell("2.1");
			table.addCell("3.1");
			table.addCell("1.2");
			table.addCell("2.2");
			table.addCell("3.2");
			table.addCell("1.3");
			table.addCell("2.3");
			table.addCell("3.3");
			// 重新定义单元格
			cell = new PdfPCell(new Paragraph("cell test1"));
			// 定义单元格的框颜色
			cell.setBorderColor(new Color(255, 0, 0));
			// 把单元格加到表格上，默认为一个单元
			table.addCell(cell);
			// 重新定义单元格
			cell = new PdfPCell(new Paragraph("cell test2"));
			// 定义单元格的跨度
			cell.setColspan(2);
			// 定义单元格的背景颜色
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			// 增加到表格上
			table.addCell(cell);
			// 增加到文档中
			document.add(table);
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		// 步骤 5:关闭文档
		document.close();
	}
}