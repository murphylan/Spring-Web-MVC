package com.utils.itext;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class PdfExportUtil {

	/**
	 * Generates a PDF file with the text 'Hello World'
	 * 
	 * @param args
	 *            no arguments needed here
	 */
	public static void main(String[] args) {

		System.out.println("Hello World");

		// step 1: creation of a document-object
		Document document = new Document();
		try {
			// step 2:
			// we create a writer that listens to the document
			// and directs a PDF-stream to a file
			PdfWriter.getInstance(document, new FileOutputStream(
					"c:/HelloWorld.pdf"));
			// BaseFont bf = BaseFont.createFont("STSong-Light,Bold",
			// "UniGB-UCS2-H",
			// BaseFont.NOT_EMBEDDED);
			BaseFont bf = BaseFont.createFont(
					"c:\\windows\\fonts\\simsun.ttc,1", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			// step 3: we open the document
			document.open();
			// step 4: we add a paragraph to the document
			document.add(new Paragraph("Hello World"));
			document.add(new Paragraph("学习PDF", new Font(bf)));
			document.add(new Paragraph("中国人", new Font(bf)));
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		// step 5: we close the document
		document.close();

	}
}