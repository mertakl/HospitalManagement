package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import dto.Hasta;
import dto.Istem;
import dto.Login;
import dto.Muayene;
import dto.MuayeneHasta;
import dto.Randevu;
import dto.Recete;
import dto.Saat;
import dto.Ulke;
import java.text.DateFormat;
import webService.HastaService;

public class Access {

    static Connection connection = Database.getConnection();

    // Hastalar� Getir
    public List<Hasta> getHastalar() throws SQLException {

        List<Hasta> hastaList = new ArrayList<Hasta>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM tblHasta");
        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next()) {
                Hasta hastaObj = new Hasta();
                hastaObj.setId(rs.getInt("hasta_ID"));
                hastaObj.setTcKimlik(rs.getLong("hasta_tc_kimlik"));
                hastaObj.setUlke(rs.getString("yabanci_hasta_ulke"));
                hastaObj.setPas_no(rs.getString("yabanci_hasta_pas_no"));
                hastaObj.setIsim(rs.getString("hasta_isim"));
                hastaObj.setSoyIsim(rs.getString("hasta_soyisim"));
                hastaObj.setDogumTarih(rs.getString("hasta_dogum_tarih"));
                hastaObj.setMeslek(rs.getString("hasta_meslek"));
                hastaList.add(hastaObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hastaList;

    }

    // Hasta Kay�t
    public boolean saveHasta(List<Hasta> hastaList) {

        int randevu_id = 0;

        try {

            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO tblHasta (hasta_tc_kimlik, yabanci_hasta_ulke, yabanci_hasta_pas_no, hasta_isim, hasta_soyisim,hasta_dogum_tarih,hasta_cinsiyet,hasta_telefon,hasta_email,hasta_meslek,baba_adi,medeni_hal,randevu_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");

            Iterator<Hasta> it = hastaList.iterator();

            while (it.hasNext()) {

                Hasta h = it.next();
                stmt.setLong(1, h.getTcKimlik());
                stmt.setString(2, h.getUlke());
                stmt.setString(3, h.getPas_no());
                stmt.setString(4, h.getIsim());
                stmt.setString(5, h.getSoyIsim());
                stmt.setString(6, h.getDogumTarih());
                stmt.setString(7, h.getCinsiyet());
                stmt.setString(8, h.getTelefon());
                stmt.setString(9, h.getEmail());
                stmt.setString(10, h.getMeslek());
                stmt.setString(11, h.getBaba_adi());
                stmt.setString(12, h.getMedeni_hal());
                
                int tc = (int) h.getTcKimlik();

                // if (h.isRandevu() == true) {
                PreparedStatement pst = connection.prepareStatement(
                        "SELECT randevu_ID FROM tblRandevu where tc_kimlik = '" + tc + "'");
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    randevu_id = rs.getInt("randevu_ID");
                }
                // }

                stmt.setInt(13, randevu_id);

                stmt.addBatch();

            }

            stmt.executeBatch();

            return true;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return false;
        }

    }

    /*
	// Hasta Kayd� i�in �ncelikle randevu al�nmas� gerekir
	public boolean randevuAra(long tcKimlik) throws SQLException {

		try {
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM tblRandevu where tc_kimlik = '" + tcKimlik + "'");
			ResultSet rs = stmt.executeQuery();

			if (!rs.isBeforeFirst()) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}
     */
    // Randevu Kaydet
    public boolean saveRandevu(List<Randevu> randevuList) {

        try {

            PreparedStatement stmt = connection
                    .prepareStatement("INSERT INTO tblRandevu (randevu_tarih,randevu_saat,tc_kimlik) VALUES (?,?,?)");
            Iterator<Randevu> it = randevuList.iterator();

            while (it.hasNext()) {

                Randevu r = it.next();
                stmt.setString(1, r.getTarih());
                stmt.setString(2, r.getSaat());
                stmt.setLong(3, r.getTcKimlik());

                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return false;

        }

        return true;

    }

    // Recete eklerken tc kimlik search ediyor
    public boolean searchTcKimlik(long tcKimlik) {

        try {
            PreparedStatement stmt = connection
                    .prepareStatement("SELECT * FROM tblHasta where hasta_tc_kimlik = '" + tcKimlik + "'");
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    // Recete Kaydet
    public boolean saveRecete(List<Recete> receteList) {

        Date dNow = new Date();
        SimpleDateFormat ft
                = new SimpleDateFormat("yyyy.MM.dd");

        try {

            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO tblRecete (ilac,hasta_tc_kimlik,kullanim_sekli,kutu_adet,recete_no,tarih) VALUES (?,?,?,?,?,?)");
            Iterator<Recete> it = receteList.iterator();

            while (it.hasNext()) {

                Recete r = it.next();

                stmt.setString(1, r.getIlac());
                stmt.setLong(2, r.getHasta_tc_kimlik());
                stmt.setString(3, r.getKullanim_sekli());
                stmt.setInt(4, r.getKutu_adet());
                stmt.setLong(5, r.getRecete_no());
                stmt.setString(6, ft.format(dNow));
                stmt.addBatch();
            }

            stmt.executeBatch();

            return true;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return false;
        }

    }

    public List<Hasta> getUserData() throws SQLException {

        long tc = HastaService.tcKimlik;

        Hasta hastaObj = null;

        List<Hasta> nviList = new ArrayList<Hasta>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM nvi where vatandas_tc_kimlik = ?");
        stmt.setLong(1, tc);

        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next()) {

                hastaObj = new Hasta();
                hastaObj.setId(rs.getInt("vatandas_ID"));
                hastaObj.setTcKimlik(rs.getLong("vatandas_tc_kimlik"));
                hastaObj.setIsim(rs.getString("vatandas_isim"));
                hastaObj.setSoyIsim(rs.getString("vatandas_soyisim"));
                hastaObj.setDogumTarih(rs.getString("vatandas_dogum_tarih"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        nviList.add(hastaObj);

        return nviList;

    }

    // Kullanici giri�
    public static Login kullaniciOnayla(Login login) {

        try {

            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM tblKullanicilar where kullanici_adi = ? AND " + "kullanici_sifre = ?");

            stmt.setString(1, login.getKullanici());
            stmt.setString(2, login.getSifre());

            ResultSet rs = stmt.executeQuery();

            boolean exist = rs.next();

            if (!exist) {

                System.out.println("Kay�tl� kullan�c� de�il!");
                login.setGecerli(false);

            } else {

                System.out.println("Ok!");
                login.setGecerli(true);

            }

        } catch (Exception e) {

            System.out.println(e);

        }

        return login;

    }

    public boolean hastaAra(long tcKimlik) {

        try {
            PreparedStatement stmt = connection
                    .prepareStatement("SELECT * FROM tblHasta where hasta_tc_kimlik = '" + tcKimlik + "'");
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;

    }

    // Muayenehaneyi getir
    public List<Muayene> getMuayeneHane() throws SQLException {

        List<Muayene> muayeneList = new ArrayList<Muayene>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM tblMuayene");
        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next()) {
                Muayene muayeneObj = new Muayene();
                muayeneObj.setMuayene_ad(rs.getString("muayene_ad"));
                muayeneObj.setMuayene_adres(rs.getString("muayene_adres"));
                muayeneObj.setMuayene_email(rs.getString("muayene_email"));
                muayeneObj.setMuayene_tel(rs.getString("muayene_tel"));
                muayeneObj.setMuayene_fax(rs.getString("muayene_fax"));
                muayeneList.add(muayeneObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return muayeneList;

    }

    /* D�zeltilecek*/
    public boolean saveMuayeneHasta(List<MuayeneHasta> muayeneHastaList) {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

        String tarih = dateFormatter.format(new Date());
        String saat = timeFormatter.format(new Date());

        try {

            PreparedStatement stmt = connection
                    .prepareStatement("INSERT INTO tblHastaMuayene (hasta_tc_kimlik ,sikayet, hikaye, tani1, tani2, tetkik, tarih, saat, istem_id) VALUES (?,?,?,?,?,?,?,?,?)");
            Iterator<MuayeneHasta> it = muayeneHastaList.iterator();

            while (it.hasNext()) {

                MuayeneHasta r = it.next();
                stmt.setLong(1, r.getHasta_tc_kimlik());
                stmt.setString(2, r.getSikayet());
                stmt.setString(3, r.getHikaye());
                stmt.setString(4, r.getTani1());
                stmt.setString(5, r.getTani2());
                stmt.setString(6, r.getTetkik());
                stmt.setString(7, tarih);
                stmt.setString(8, saat);
                stmt.setInt(9, r.getIstem_id());

                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return false;

        }

        return true;

    }

    public List<Istem> getIstemler() throws SQLException {
        // TODO Auto-generated method stub
        List<Istem> istemList = new ArrayList<Istem>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM tblistem");
        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next()) {
                Istem istemObj = new Istem();
                istemObj.setIstem_id(rs.getInt("istem_ID"));
                istemObj.setIstem_adi(rs.getString("istem_adi"));
                istemList.add(istemObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return istemList;
    }

    // Muayenehaneyi g�ncelleme
    public boolean updateMuayeneHane(List<Muayene> mList) {
        try {

            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE tblMuayene SET muayene_ad = ?, muayene_adres = ?, muayene_email = ?, muayene_tel = ?, muayene_fax = ? WHERE muayene_ID = 1");
            Iterator<Muayene> it = mList.iterator();

            while (it.hasNext()) {

                Muayene m = it.next();
                stmt.setString(1, m.getMuayene_ad());
                stmt.setString(2, m.getMuayene_adres());
                stmt.setString(3, m.getMuayene_email());
                stmt.setString(4, m.getMuayene_tel());
                stmt.setString(5, m.getMuayene_fax());

                stmt.addBatch();
            }

            stmt.executeBatch();

            stmt.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return false;

        }

        return true;

    }

    // Randevular� getir
    public List<Randevu> getRandevular() throws SQLException {

        List<Randevu> randevuList = new ArrayList<Randevu>();
        PreparedStatement stmt = connection.prepareStatement("SELECT tblhasta.hasta_isim,tblhasta.hasta_soyisim, tblrandevu.randevu_ID, tblrandevu.randevu_tarih, tblrandevu.randevu_saat, tblrandevu.tc_kimlik FROM tblhasta RIGHT JOIN tblrandevu ON tblhasta.hasta_tc_kimlik = tblrandevu.tc_kimlik ORDER BY randevu_ID");
        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next()) {
                Randevu randevuObj = new Randevu();
                randevuObj.setId(rs.getInt("randevu_ID"));
                randevuObj.setHasta_isim(rs.getString("hasta_isim"));
                randevuObj.setHasta_soyisim(rs.getString("hasta_soyisim"));
                randevuObj.setTarih(rs.getString("randevu_tarih"));
                randevuObj.setSaat(rs.getString("randevu_saat"));
                randevuObj.setTcKimlik(rs.getLong("tc_kimlik"));
                randevuList.add(randevuObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return randevuList;

    }

    // Muayene Hastalar� Getir  //D�zeltilecek
    public List<MuayeneHasta> getMuayeneHasta() throws SQLException {
        List<MuayeneHasta> hastaList = new ArrayList<MuayeneHasta>();
        PreparedStatement stmt = connection.prepareStatement("SELECT tblhasta.hasta_isim,tblhasta.hasta_soyisim, tblhastamuayene.hasta_tc_kimlik, tblhastamuayene.hasta_muayene_ID, tblhastamuayene.sikayet, tblhastamuayene.hikaye, tblhastamuayene.tani1, tblhastamuayene.tani2, tblhastamuayene.tetkik, tblhastamuayene.tarih, tblhastamuayene.saat FROM tblhasta RIGHT JOIN tblhastamuayene ON tblhasta.hasta_tc_kimlik = tblhastamuayene.hasta_tc_kimlik");
        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next()) {
                MuayeneHasta hastaObj = new MuayeneHasta();
                hastaObj.setHasta_muayene_id(rs.getInt("hasta_muayene_ID"));
                hastaObj.setHasta_tc_kimlik(rs.getLong("hasta_tc_kimlik"));
                hastaObj.setHasta_isim(rs.getString("hasta_isim"));
                hastaObj.setHasta_soyisim(rs.getString("hasta_soyisim"));
                hastaObj.setSikayet(rs.getString("sikayet"));
                hastaObj.setHikaye(rs.getString("hikaye"));
                hastaObj.setTani1(rs.getString("tani1"));
                hastaObj.setTani2(rs.getString("tani2"));
                hastaObj.setTetkik(rs.getString("tetkik"));
                hastaObj.setTarih(rs.getString("tarih"));
                hastaObj.setSaat(rs.getString("saat"));
                hastaList.add(hastaObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hastaList;

    }

    public List<Ulke> getUlkeler() throws SQLException {

        List<Ulke> ulkeList = new ArrayList<Ulke>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM tblUlke");
        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next()) {
                Ulke ulkeObj = new Ulke();
                ulkeObj.setUlke_id(rs.getInt("ulke_id"));
                ulkeObj.setUlke(rs.getString("ulke"));
                ulkeList.add(ulkeObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ulkeList;

    }

    public List<Randevu> getGunlukRandevular() throws SQLException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        String tarih = dateFormat.format(date);

        List<Randevu> randevuList = new ArrayList<Randevu>();
        PreparedStatement stmt = connection.prepareStatement("SELECT tblhasta.hasta_isim,tblhasta.hasta_soyisim, tblrandevu.randevu_ID, tblrandevu.randevu_tarih, tblrandevu.randevu_saat, tblrandevu.tc_kimlik FROM tblhasta JOIN tblrandevu ON tblhasta.hasta_tc_kimlik = tblrandevu.tc_kimlik AND tblrandevu.randevu_tarih = '" + tarih + "' ORDER BY randevu_ID ");
        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next()) {
                Randevu randevuObj = new Randevu();
                randevuObj.setId(rs.getInt("randevu_ID"));
                randevuObj.setHasta_isim(rs.getString("hasta_isim"));
                randevuObj.setHasta_soyisim(rs.getString("hasta_soyisim"));
                randevuObj.setTarih(rs.getString("randevu_tarih"));
                randevuObj.setSaat(rs.getString("randevu_saat"));
                randevuObj.setTcKimlik(rs.getLong("tc_kimlik"));
                randevuList.add(randevuObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return randevuList;

    }

    //Randevu Saatlerini Getir
    public List<Saat> getSaatler(String saat) throws SQLException {

        List<Saat> saatList = new ArrayList<Saat>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM randevu_saat where saat NOT IN '" + saat + "'");

        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next()) {
                Saat saatObj = new Saat();
                saatObj.setSaat_id(rs.getInt("id"));
                saatObj.setSaat(rs.getString("saat"));
                saatList.add(saatObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saatList;
    }

    public String randevuAra(String tarih) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement("select randevu_saat from tblrandevu where randevu_tarih = " + tarih + "");

        ResultSet rs = stmt.executeQuery();

        String s = null;

        try {
            while (rs.next()) {

                s = rs.getString("randevu_saat");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;

    }

    public boolean updateKullanici(List<Login> kList) {

        try {

            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE tblkullanicilar SET kullanici_adi = ?, kullanici_sifre = ? WHERE kullanici_ID = 1");
            Iterator<Login> it = kList.iterator();

            while (it.hasNext()) {

                Login l = it.next();
                stmt.setString(1, l.getKullanici());
                stmt.setString(2, l.getSifre());
                stmt.addBatch();
            }

            stmt.executeBatch();

            stmt.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return false;

        }

        return true;

    }

    public List<Login> getKullanicilar() throws SQLException {

        List<Login> kList = new ArrayList<Login>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM tblkullanicilar");
        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next()) {
                Login lObj = new Login();
                lObj.setKullanici(rs.getString("kullanici_adi"));
                lObj.setSifre(rs.getString("kullanici_sifre"));
                kList.add(lObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kList;

    }

}
