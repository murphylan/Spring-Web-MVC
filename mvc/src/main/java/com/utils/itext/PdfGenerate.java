package com.utils.itext;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PdfGenerate {
	public static BaseFont bf;

	public PdfGenerate() {
		// 设置字体环境
		try {
			bf = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);//设置中文字体
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HttpServletResponse pdfWrite(PdfRow pdfRow, float[] widths,
			HttpServletResponse response) throws Exception {
		// 实例化文档对象
		Document document = new Document(PageSize.A4);
		try {
			// 创建pdfWriter实例
			PdfWriter.getInstance(document, response.getOutputStream());
			// 标题 document打开之前调用
			document.addTitle(pdfRow.getPdfTitle());
			// 打开document
			document.open();
			Font f12 = new Font(bf, 12, Font.BOLD);
			Paragraph p = new Paragraph(pdfRow.getPdfTitle(), f12);
			p.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(p);
			document.add(new Paragraph(Chunk.NEWLINE));
			/** 添加空行* */
			int cols = pdfRow.getContent().get(0).length;
			PdfPTable table = new PdfPTable(cols);
			// 表格样式
			table.setWidthPercentage(100);
			// 定义列宽
			if (widths.length == cols) {
				table.setWidths(widths);
			}
			// 为表格添加内容
			for (Object[] str : pdfRow.getContent()) {
				for (Object o : str) {
					if (null != o) {
						PdfPCell cell = setCell(StringUtils.trim(String
								.valueOf(o)));
						table.addCell(cell);
					} else {
						table.addCell("");
					}
				}
			}
			document.add(table);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 第五步关闭document
		document.close();
		return response;
	}

	public static PdfPCell setCell(String text) {
		Font FontChinese = new Font(bf, 10);
		PdfPCell cell = new PdfPCell(new Paragraph(text, FontChinese));
		boolean isNunicodeDigits = StringUtils.isNumeric(text);
		if (isNunicodeDigits) {
			cell.setNoWrap(true);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		} else {
			cell.setNoWrap(false);
		}
		return cell;
	}

	public class PdfRow {
		private String pdfTitle;
		private List<Object[]> content;
		private String PdfOther;

		public String getPdfTitle() {
			return pdfTitle;
		}

		public void setPdfTitle(String pdfTitle) {
			this.pdfTitle = pdfTitle;
		}

		public String getPdfOther() {
			return PdfOther;
		}

		public void setPdfOther(String pdfOther) {
			PdfOther = pdfOther;
		}

		public List<Object[]> getContent() {
			return content;
		}

		public void setContent(List<Object[]> content) {
			this.content = content;
		}

	}
}
