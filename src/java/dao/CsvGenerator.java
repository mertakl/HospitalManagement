package dao;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CsvGenerator {
	
	static Connection connection = Database.getConnection();
	
	public static void generateHastaListCsv(){
		
		String filename ="D:/temp/hastaList.csv";
		
        try {
            FileWriter fw = new FileWriter(filename);
            String query = "SELECT * FROM tblHasta";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fw.append(rs.getString("hasta_tc_kimlik"));
                fw.append(',');
                fw.append(rs.getString("yabanci_hasta_ulke"));
                fw.append(',');
                fw.append(rs.getString("yabanci_hasta_pas_no"));
                fw.append(',');
                fw.append(rs.getString("hasta_isim"));
                fw.append(',');
                fw.append(rs.getString("hasta_soyisim"));
                fw.append(',');
                fw.append(rs.getString("hasta_dogum_tarih"));
                fw.append(',');
                fw.append(rs.getString("hasta_telefon"));
                fw.append('\n');
               }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	public static void generateRandevuListCsv(){
		
		String filename ="D:/temp/randevuList.csv";
		
        try {
            FileWriter fw = new FileWriter(filename);
            String query = "SELECT tblhasta.hasta_isim,tblhasta.hasta_soyisim, tblrandevu.randevu_ID, tblrandevu.randevu_tarih, tblrandevu.randevu_saat, tblrandevu.tc_kimlik FROM tblhasta RIGHT JOIN tblrandevu ON tblhasta.hasta_tc_kimlik = tblrandevu.tc_kimlik ORDER BY randevu_ID";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fw.append(rs.getString("tc_kimlik"));
                fw.append(',');
                fw.append(rs.getString("hasta_isim"));
                fw.append(',');
                fw.append(rs.getString("hasta_soyisim"));
                fw.append(',');
                fw.append(rs.getString("randevu_tarih"));
                fw.append(',');
                fw.append(rs.getString("randevu_saat"));
                fw.append('\n');
               }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
	}
	
public static void generateMuayeneListCsv(){
		
		String filename ="D:/temp/muayeneList.csv";
		
        try {
            FileWriter fw = new FileWriter(filename);
            String query = "SELECT tblhasta.hasta_isim,tblhasta.hasta_soyisim, tblhastamuayene.hasta_tc_kimlik, tblhastamuayene.hasta_muayene_ID, tblhastamuayene.sikayet, tblhastamuayene.hikaye, tblhastamuayene.tani1, tblhastamuayene.tani2, tblhastamuayene.tetkik, tblhastamuayene.tarih, tblhastamuayene.saat FROM tblhasta RIGHT JOIN tblhastamuayene ON tblhasta.hasta_tc_kimlik = tblhastamuayene.hasta_tc_kimlik";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fw.append(rs.getString("hasta_tc_kimlik"));
                fw.append(',');
                fw.append(rs.getString("hasta_isim"));
                fw.append(',');
                fw.append(rs.getString("hasta_soyisim"));
                fw.append(',');
                fw.append(rs.getString("sikayet"));
                fw.append(',');
                fw.append(rs.getString("hikaye"));
                fw.append(',');
                fw.append(rs.getString("tani1"));
                fw.append(',');
                fw.append(rs.getString("tani2"));
                fw.append(',');
                fw.append(rs.getString("tetkik"));
                fw.append('\n');
               }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
	}


}
