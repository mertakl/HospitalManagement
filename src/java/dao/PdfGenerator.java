package dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dto.Recete;


public class PdfGenerator {

	private static String FILE = "D:/temp/recete.pdf";

	private static String FILE2 = "D:/temp/hastalistesi.pdf";

	private static String FILE3 = "D:/temp/randevulistesi.pdf";

	private static String FILE4 = "D:/temp/muayenelistesi.pdf";

	static Connection conn = Database.getConnection();

	public static void generateRecete(long tcKimlik, long receteNo) throws SQLException, FileNotFoundException, DocumentException {
		
		PreparedStatement stmt = conn.prepareStatement("SELECT tblhasta.hasta_isim,tblhasta.hasta_soyisim, tblhasta.hasta_tc_kimlik, tblrecete.ilac, tblrecete.kullanim_sekli, tblrecete.kutu_adet, tblrecete.recete_no, tblrecete.tarih FROM tblhasta RIGHT JOIN tblrecete ON tblhasta.hasta_tc_kimlik = tblrecete.hasta_tc_kimlik where tblrecete.hasta_tc_kimlik= ? AND tblrecete.recete_no = ?");
		stmt.setLong(1, tcKimlik);
		stmt.setLong(2, receteNo);
		

		ResultSet query_set = stmt.executeQuery();

		Document document = new Document();

		document.addTitle("Recete");

		PdfWriter.getInstance(document, new FileOutputStream(FILE));
		document.open();
		
		Paragraph preface = new Paragraph();
                addEmptyLine(preface, 1);

		PdfPTable my_report_table = new PdfPTable(3);

		PdfPCell c1 = new PdfPCell(new Phrase("Ilac"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Kullanim Sekli"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Kutu Adet"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);
		my_report_table.setHeaderRows(1);

		PdfPCell table_cell;
		String ad = null;
		String soyad = null;
		long recete_no = 0;
                String tarih_recete = null;

		while (query_set.next()) {
			ad = query_set.getString("hasta_isim");
			soyad = query_set.getString("hasta_soyisim");
			recete_no = query_set.getLong("recete_no");
                        tarih_recete = query_set.getString("tarih");

			String ilac = query_set.getString("ilac");
			table_cell = new PdfPCell(new Phrase(ilac));
			my_report_table.addCell(table_cell);
			String kullanim_sekli = query_set.getString("kullanim_sekli");
			table_cell = new PdfPCell(new Phrase(kullanim_sekli));
			my_report_table.addCell(table_cell);
			String kutu_adet = query_set.getString("kutu_adet");
			table_cell = new PdfPCell(new Phrase(kutu_adet));
			my_report_table.addCell(table_cell);
		}

		Paragraph isim = new Paragraph("Hasta Adi : " + ad);
		isim.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(isim, 1);
		Paragraph soyisim = new Paragraph("Hasta Soyadi : " + soyad);
		soyisim.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(soyisim, 1);
		Paragraph tc = new Paragraph("Hasta Tc : " + tcKimlik);
		tc.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(tc, 1);
		Paragraph no = new Paragraph("Recete No : " + recete_no);
		no.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(no, 1);
                Paragraph tarih = new Paragraph("Tarih : " + tarih_recete);
		tarih.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(tarih, 1);

		document.add(isim);
		document.add(soyisim);
		document.add(tc);
		document.add(no);
                document.add(tarih);
		document.add(preface);
		document.add(my_report_table);
		document.close();

		query_set.close();
		stmt.close();

	}

	public static void generateHastaList() throws SQLException, FileNotFoundException, DocumentException {

		Statement stmt = conn.createStatement();

		ResultSet query_set = stmt.executeQuery("SELECT * FROM tblHasta");

		Document document = new Document(PageSize.A4, 10, 10, 10, 10);

		document.addTitle("Hasta Listesi");

		PdfWriter.getInstance(document, new FileOutputStream(FILE2));
		document.open();

		PdfPTable my_report_table = new PdfPTable(7);

		my_report_table.setWidthPercentage(100);
		my_report_table.setSpacingBefore(0f);
		my_report_table.setSpacingAfter(0f);

		PdfPCell c1 = new PdfPCell(new Phrase("Tc Kimlik"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Yabanci Hasta Ulke"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Pasaport No"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Hasta Adi"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Hasta Soyadi"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Dogum Tarihi"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Hasta Telefon"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		my_report_table.setHeaderRows(1);

		PdfPCell table_cell;

		while (query_set.next()) {

			String tcNo = query_set.getString("hasta_tc_kimlik");
			table_cell = new PdfPCell(new Phrase(tcNo));
			my_report_table.addCell(table_cell);

			String ulke = query_set.getString("yabanci_hasta_ulke");
			table_cell = new PdfPCell(new Phrase(ulke));
			my_report_table.addCell(table_cell);

			String pasNo = query_set.getString("yabanci_hasta_pas_no");
			table_cell = new PdfPCell(new Phrase(pasNo));
			my_report_table.addCell(table_cell);

			String ad = query_set.getString("hasta_isim");
			table_cell = new PdfPCell(new Phrase(ad));
			my_report_table.addCell(table_cell);

			String soyad = query_set.getString("hasta_soyisim");
			table_cell = new PdfPCell(new Phrase(soyad));
			my_report_table.addCell(table_cell);

			String dt = query_set.getString("hasta_dogum_tarih");
			table_cell = new PdfPCell(new Phrase(dt));
			my_report_table.addCell(table_cell);

			String telefon = query_set.getString("hasta_telefon");
			table_cell = new PdfPCell(new Phrase(telefon));
			my_report_table.addCell(table_cell);
		}

		document.add(my_report_table);
		document.close();

	}

	public static void generateRandevuList() throws SQLException, FileNotFoundException, DocumentException {

		Statement stmt = conn.createStatement();

		ResultSet query_set = stmt.executeQuery(
				"SELECT tblhasta.hasta_isim,tblhasta.hasta_soyisim, tblrandevu.randevu_ID, tblrandevu.randevu_tarih, tblrandevu.randevu_saat, tblrandevu.tc_kimlik FROM tblhasta RIGHT JOIN tblrandevu ON tblhasta.hasta_tc_kimlik = tblrandevu.tc_kimlik ORDER BY randevu_ID");

		Document document = new Document();

		document.addTitle("Randevu Listesi");

		PdfWriter.getInstance(document, new FileOutputStream(FILE3));
		document.open();

		PdfPTable my_report_table = new PdfPTable(5);

		PdfPCell c1 = new PdfPCell(new Phrase("Tc Kimlik"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Hasta Adi"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Hasta Soyadi"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Randevu Tarihi"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Randevu Saat"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		my_report_table.setHeaderRows(1);

		PdfPCell table_cell;

		while (query_set.next()) {

			String tcNo = query_set.getString("tc_kimlik");
			table_cell = new PdfPCell(new Phrase(tcNo));
			my_report_table.addCell(table_cell);

			String ad = query_set.getString("hasta_isim");
			table_cell = new PdfPCell(new Phrase(ad));
			my_report_table.addCell(table_cell);

			String soyad = query_set.getString("hasta_soyisim");
			table_cell = new PdfPCell(new Phrase(soyad));
			my_report_table.addCell(table_cell);

			String rt = query_set.getString("randevu_tarih");
			table_cell = new PdfPCell(new Phrase(rt));
			my_report_table.addCell(table_cell);

			String rs = query_set.getString("randevu_saat");
			table_cell = new PdfPCell(new Phrase(rs));
			my_report_table.addCell(table_cell);
		}

		document.add(my_report_table);
		document.close();

	}

	public static void generateMuayeneList() throws SQLException, FileNotFoundException, DocumentException {

		Statement stmt = conn.createStatement();

		ResultSet query_set = stmt.executeQuery(
				"SELECT tblhasta.hasta_isim,tblhasta.hasta_soyisim, tblhastamuayene.hasta_tc_kimlik, tblhastamuayene.hasta_muayene_ID, tblhastamuayene.sikayet, tblhastamuayene.hikaye, tblhastamuayene.tani1, tblhastamuayene.tani2, tblhastamuayene.tetkik, tblhastamuayene.tarih, tblhastamuayene.saat FROM tblhasta RIGHT JOIN tblhastamuayene ON tblhasta.hasta_tc_kimlik = tblhastamuayene.hasta_tc_kimlik");

		Document document = new Document(PageSize.A4, 10, 10, 10, 10);

		document.addTitle("Muayene Hasta Listesi");

		PdfWriter.getInstance(document, new FileOutputStream(FILE4));
		document.open();

		PdfPTable my_report_table = new PdfPTable(8);

		my_report_table.setWidthPercentage(100);
		my_report_table.setSpacingBefore(0f);
		my_report_table.setSpacingAfter(0f);

		PdfPCell c1 = new PdfPCell(new Phrase("Tc Kimlik"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Hasta Adi"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Hasta Soyadi"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Sikayet"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Hikaye"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Tani1"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Tani2"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Tetkik"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		my_report_table.addCell(c1);

		my_report_table.setHeaderRows(1);

		PdfPCell table_cell;

		while (query_set.next()) {

			String tcNo = query_set.getString("hasta_tc_kimlik");
			table_cell = new PdfPCell(new Phrase(tcNo));
			my_report_table.addCell(table_cell);

			String ad = query_set.getString("hasta_isim");
			table_cell = new PdfPCell(new Phrase(ad));
			my_report_table.addCell(table_cell);

			String soyad = query_set.getString("hasta_soyisim");
			table_cell = new PdfPCell(new Phrase(soyad));
			my_report_table.addCell(table_cell);

			String sikayet = query_set.getString("sikayet");
			table_cell = new PdfPCell(new Phrase(sikayet));
			my_report_table.addCell(table_cell);

			String hikaye = query_set.getString("hikaye");
			table_cell = new PdfPCell(new Phrase(hikaye));
			my_report_table.addCell(table_cell);

			String tani1 = query_set.getString("tani1");
			table_cell = new PdfPCell(new Phrase(tani1));
			my_report_table.addCell(table_cell);

			String tani2 = query_set.getString("tani2");
			table_cell = new PdfPCell(new Phrase(tani2));
			my_report_table.addCell(table_cell);

			String tetkik = query_set.getString("tetkik");
			table_cell = new PdfPCell(new Phrase(tetkik));
			my_report_table.addCell(table_cell);
		}

		document.add(my_report_table);
		document.close();

	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

}
