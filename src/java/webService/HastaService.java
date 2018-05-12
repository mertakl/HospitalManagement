package webService;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.itextpdf.text.DocumentException;

import dao.Access;
import dao.CsvGenerator;
import dao.PdfGenerator;
import dto.Hasta;
import dto.Istem;
import dto.Login;
import dto.Muayene;
import dto.MuayeneHasta;
import dto.Randevu;
import dto.Recete;
import dto.Saat;
import dto.Ulke;

@Path("/hastaService")
public class HastaService {

    public static long tcKimlik = 0;

    Access access = new Access();

    private JsonParser parser = new JsonParser();

    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response login(@FormParam("kullanici") String kullanici, @FormParam("sifre") String sifre,
            @Context HttpServletRequest request) throws URISyntaxException {

        Login login = new Login();

        login.setKullanici(kullanici);

        login.setSifre(sifre);

        login = Access.kullaniciOnayla(login);

        if (login.isGecerli() == true) {

            HttpSession session = request.getSession();

            session.setAttribute("name", login.getKullanici());

            URI location = new URI("/RestProject/index.html");

            return Response.temporaryRedirect(location).build();

        } else {

            return Response.status(Response.Status.NOT_FOUND).entity("Kullanici adi veya sifre hatali!").build();

        }

    }

    @POST
    @Path("/getKullanici")
    @Produces("application/json")
    public Response getKullanici(@Context HttpServletRequest request) {

        HttpSession session = request.getSession();

        String name = (String) session.getAttribute("name");

        return Response.ok(name).header("Access-Control-Allow-Origin", "*").build();

    }

    @POST
    @Path("/logout")
    @Consumes("application/x-www-form-urlencoded")
    public Response logout(@Context HttpServletRequest request) throws URISyntaxException {

        HttpSession session = request.getSession();

        session.invalidate();

        return Response.ok().header("Access-Control-Allow-Origin", "*").build();

    }
    
    @POST
    @Path("/kullanici")
    @Produces("application/json")
    public Response kullanicilar() {

        String kullanici = null;
        List<Login> kList = new ArrayList<Login>();
        try {
            kList = new Access().getKullanicilar();
            Gson gson = new Gson();
            kullanici = gson.toJson(kList);

        } catch (Exception e) {

            return Response.status(Status.BAD_REQUEST).entity("Bir sorun olustu!").build();

        }

        return Response.ok(kullanici).header("Access-Control-Allow-Origin", "*").build();
    }

    // Hasta Listesi Getir
    @POST
    @Path("/hastalar")
    @Produces("application/json")
    public Response hastalar() {

        String hastalar = null;
        List<Hasta> hastaList = new ArrayList<Hasta>();
        try {
            hastaList = new Access().getHastalar();
            Gson gson = new Gson();
            hastalar = gson.toJson(hastaList);

        } catch (Exception e) {

            return Response.status(Status.BAD_REQUEST).entity("Bir sorun olustu!").build();

        }

        return Response.ok(hastalar).header("Access-Control-Allow-Origin", "*").build();
    }
    
    public static String s;
    
    //Randevu Ara
    @GET
    @Path("/randevuAra/{date}")
    @Produces("application/json")
    public void randevuAra(@PathParam("date") String tarih) throws SQLException {
        s = access.randevuAra(tarih);
       
        System.out.println(s);

    }

    // Saat Listesi Getir
    @GET
    @Path("/saatler")
    @Produces("application/json")
    public Response saatler() {
        
        System.out.println(s);

        String saatler = null;
        List<Saat> saatList = new ArrayList<Saat>();
        try {
            saatList = new Access().getSaatler(s);
            Gson gson = new Gson();
            saatler = gson.toJson(saatList);

        } catch (Exception e) {

            return Response.status(Status.BAD_REQUEST).entity("Bir sorun olustu!").build();

        }

        return Response.ok(saatler).header("Access-Control-Allow-Origin", "*").build();

    }

    // Muayene listesi
    @POST
    @Path("/muayeneler")
    @Produces("application/json")
    public Response muayeneler() {
        String muayeneHasta = null;
        List<MuayeneHasta> muayeneHastaList = new ArrayList<MuayeneHasta>();
        try {
            muayeneHastaList = new Access().getMuayeneHasta();
            Gson gson = new Gson();
            muayeneHasta = gson.toJson(muayeneHastaList);

        } catch (Exception e) {

            return Response.status(Status.BAD_REQUEST).entity("Bir sorun olustu!").build();

        }

        return Response.ok(muayeneHasta).header("Access-Control-Allow-Origin", "*").build();
    }

    // Randevu Listesi
    @POST
    @Path("/randevular")
    @Produces("application/json")
    public Response randevular() {
        String randevular = null;
        List<Randevu> randevuList = new ArrayList<Randevu>();
        try {
            randevuList = new Access().getRandevular();
            Gson gson = new Gson();
            randevular = gson.toJson(randevuList);

        } catch (Exception e) {

            return Response.status(Status.BAD_REQUEST).entity("Bir sorun olustu!").build();

        }

        return Response.ok(randevular).header("Access-Control-Allow-Origin", "*").build();
    }

    // Randevu Listesi
    @POST
    @Path("/gunlukRandevu")
    @Produces("application/json")
    public Response gunlukRandevu() {
        String randevular = null;
        List<Randevu> randevuList = new ArrayList<Randevu>();
        try {
            randevuList = new Access().getGunlukRandevular();
            Gson gson = new Gson();
            randevular = gson.toJson(randevuList);

        } catch (Exception e) {

            return Response.status(Status.BAD_REQUEST).entity("Bir sorun olustu!").build();

        }

        return Response.ok(randevular).header("Access-Control-Allow-Origin", "*").build();
    }

    // Günlük Randevu Getir
    @GET
    @Path("/muayene")
    @Produces("application/json")
    public Response muayene() {
        String muayene = null;
        List<Muayene> muayeneList = new ArrayList<Muayene>();
        try {
            muayeneList = new Access().getMuayeneHane();
            Gson gson = new Gson();
            muayene = gson.toJson(muayeneList);

        } catch (Exception e) {

            return Response.status(Status.BAD_REQUEST).entity("Bir sorun olustu!").build();

        }

        return Response.ok(muayene).header("Access-Control-Allow-Origin", "*").build();
    }

    // Hasta ekleme
    @POST
    @Path("/insertHasta")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response createHastalar(@FormParam("tcKimlik") long tcKimlik, @FormParam("ulke") String ulke,
            @FormParam("pasaportNo") String pasaportNo, @FormParam("isim") String isim,
            @FormParam("soyisim") String soyIsim, @FormParam("dogum") String dogumTarih,
            @FormParam("cinsiyet") String cinsiyet, @FormParam("tel_no") String tel_no,
            @FormParam("email") String email, @FormParam("meslek") String meslek,
            @FormParam("yhasta") String checked,
            @FormParam("babaAdi") String baba_adi,
            @FormParam("medeniHal") String medeni_hal) throws SQLException {

        //Generate random tc number for foreigners
        Random r = new Random();

        if (checked != null && tcKimlik == 0) {
            long number = r.nextInt(1_000_000_000) + (r.nextInt(90) + 10) * 1_000_000_000L;

            tcKimlik = number;
        }

        Hasta hasta = new Hasta();

        List<Hasta> hastaList = new ArrayList<Hasta>();

        hasta.setTcKimlik(tcKimlik);
        hasta.setUlke(ulke);
        hasta.setPas_no(pasaportNo);
        hasta.setIsim(isim);
        hasta.setSoyIsim(soyIsim);
        hasta.setDogumTarih(dogumTarih);
        hasta.setCinsiyet(cinsiyet);
        hasta.setTelefon(tel_no);
        hasta.setEmail(email);
        hasta.setMeslek(meslek);
        hasta.setBaba_adi(baba_adi);
        hasta.setMedeni_hal(medeni_hal);

        if (access.hastaAra(tcKimlik) == false) {

            hastaList.add(hasta);

            if (access.saveHasta(hastaList) == true) {

                return Response.ok("Basariyla Kaydedildi!", MediaType.TEXT_PLAIN).build();

            } else {

                return Response.status(Status.BAD_REQUEST).entity("Hata!").build();

            }

        } else {

            return Response.status(Status.BAD_REQUEST).entity("Hasta kayd� zaten var!").build();

        }

    }

    @POST
    @Path("/insertRandevu")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response createRandevu(@FormParam("tcKimlik") long tcKimlik, @FormParam("tarih") String tarih,
            @FormParam("saat") String saat) {

        Randevu randevu = new Randevu();

        List<Randevu> randevuList = new ArrayList<Randevu>();

        randevu.setTcKimlik(tcKimlik);
        randevu.setTarih(tarih);
        randevu.setSaat(saat);

        randevuList.add(randevu);

        if (access.saveRandevu(randevuList) == true) {

            return Response.ok("Basariyla Kaydedildi!", MediaType.TEXT_PLAIN).build();

        } else {

            return Response.status(Status.BAD_REQUEST).entity("Randevu Kaydedilemedi!").build();
        }

    }

    @POST
    @Path("/insertRecete")
    @Consumes("application/json")
    public Response createRecete(String data) throws ParseException, SQLException, FileNotFoundException, DocumentException {

        JSONParser parser = new JSONParser();

        Object obj = parser.parse(data);

        JSONArray array = (JSONArray) obj;

        String ilac = null;

        long tcKimlik = 0;

        String kSekli = null;

        int adet = 0;

        //Generate 11 digit random number
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long recete_no = random.nextLong(10_000_000_000L, 100_000_000_000L);

        for (int i = 0; i < array.size(); i++) {

            JSONObject item = (JSONObject) array.get(i);
            ilac = (String) item.get("ilac");
            tcKimlik = Long.valueOf((String) item.get("tcKimlik"));
            kSekli = (String) item.get("kullanim");
            adet = Integer.valueOf((String) item.get("adet"));

            if (access.searchTcKimlik(tcKimlik) == true) { // Tc

                Recete rct = new Recete();

                List<Recete> receteList = new ArrayList<Recete>();

                rct.setIlac(ilac);

                rct.setKullanim_sekli(kSekli);

                rct.setKutu_adet(adet);

                rct.setHasta_tc_kimlik(tcKimlik);

                rct.setRecete_no(recete_no);

                receteList.add(rct);

                access.saveRecete(receteList);

            }

        }

        PdfGenerator.generateRecete(tcKimlik, recete_no);

        return Response.ok("Basariyla Kaydedildi!", MediaType.TEXT_PLAIN).build();

    }

    @GET
    @Path("/searchTcKimlik/{tcKimlik}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response searchTcKimlik(@PathParam("tcKimlik") long tcKimlik) throws SQLException {

        this.tcKimlik = tcKimlik;

        int count = 0;

        String nvi = null;

        while (tcKimlik != 0) {
            tcKimlik /= 10;
            ++count;
        }

        if (count == 11) {

            List<Hasta> nviList = new ArrayList<Hasta>();

            try {
                nviList = new Access().getUserData();
                Gson gson = new Gson();
                nvi = gson.toJson(nviList);

            } catch (Exception e) {

                return Response.status(Status.BAD_REQUEST).entity("Bir sorun olustu!").build();

            }

        }

        return Response.ok(nvi).header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("/insertMuayene")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response createMuayene(@FormParam("hastaTc") long tcKimlik, @FormParam("sikayet") String sikayet,
            @FormParam("hikaye") String hikaye, @FormParam("tani1") String tani1, @FormParam("tani2") String tani2,
            @FormParam("tetkik") String tetkik, @FormParam("istemId") int istem_id) throws SQLException {

        MuayeneHasta mHasta = new MuayeneHasta();

        List<MuayeneHasta> muayeneHastaList = new ArrayList<MuayeneHasta>();

        mHasta.setHasta_tc_kimlik(tcKimlik);

        mHasta.setSikayet(sikayet);

        mHasta.setHikaye(hikaye);

        mHasta.setTani1(tani1);

        mHasta.setTani2(tani2);

        mHasta.setTetkik(tetkik);

        mHasta.setIstem_id(istem_id);

        muayeneHastaList.add(mHasta);

        if (access.saveMuayeneHasta(muayeneHastaList) == true) {

            return Response.ok("Basariyla Kaydedildi!", MediaType.TEXT_PLAIN).build();

        } else {

            return Response.status(Status.BAD_REQUEST).entity("Muayene Hasta eklenirken bir hata meydana geldi!")
                    .build();

        }

    }

    @GET
    @Path("/istemler")
    @Produces("application/json")
    public Response istemler() {
        String istemler = null;
        List<Istem> istemList = new ArrayList<Istem>();
        try {
            istemList = new Access().getIstemler();
            Gson gson = new Gson();
            istemler = gson.toJson(istemList);

        } catch (Exception e) {

            return Response.status(Status.BAD_REQUEST).entity("Bir sorun olustu!").build();

        }

        return Response.ok(istemler).header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("/ulkeler")
    @Produces("application/json")
    public Response ulkeler() {
        String ulkeler = null;
        List<Ulke> ulkeList = new ArrayList<Ulke>();
        try {
            ulkeList = new Access().getUlkeler();
            Gson gson = new Gson();
            ulkeler = gson.toJson(ulkeList);

        } catch (Exception e) {

            return Response.status(Status.BAD_REQUEST).entity("Bir sorun olustu!").build();

        }

        return Response.ok(ulkeler).header("Access-Control-Allow-Origin", "*").build();
    }

    // Muayene bilgilerini g�ncelle
    @POST
    @Path("/muayeneGuncel")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response updateMuayene(@FormParam("mad") String m_ad, @FormParam("madres") String m_adres,
            @FormParam("memail") String m_email, @FormParam("mtel") String m_tel, @FormParam("mfax") String m_fax)
            throws SQLException {

        Muayene muayene = new Muayene();

        List<Muayene> mList = new ArrayList<Muayene>();

        muayene.setMuayene_ad(m_ad);

        muayene.setMuayene_adres(m_adres);

        muayene.setMuayene_email(m_email);

        muayene.setMuayene_tel(m_tel);

        muayene.setMuayene_fax(m_fax);

        mList.add(muayene);

        if (access.updateMuayeneHane(mList) == true) {

            return Response.ok("Ba�ar�yla G�ncellendi!", MediaType.TEXT_PLAIN).build();

        } else {

            return Response.status(Status.BAD_REQUEST).entity("Muayenehane g�ncellenirken bir hata meydana geldi!")
                    .build();

        }

    }
    
    
       // Kullanıcı bilgilerini güncelle
    @POST
    @Path("/kullaniciGuncel")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response updateKullanici(@FormParam("k_adi") String k_adi, @FormParam("k_sifre") String k_sifre)
            throws SQLException {

        Login l = new Login();

        List<Login> kList = new ArrayList<Login>();
        l.setKullanici(k_adi);
        l.setSifre(k_sifre);
        kList.add(l);

        if (access.updateKullanici(kList) == true) {

            return Response.ok("Başarıyla Güncellendi!", MediaType.TEXT_PLAIN).build();

        } else {

            return Response.status(Status.BAD_REQUEST).entity("Kullanıcı güncellenirken bir hata meydana geldi!")
                    .build();

        }

    }

    @GET
    @Path("/downloadRecete")
    @Produces("application/pdf")
    public Response downloadRecete() throws SQLException, FileNotFoundException {

        File file = new File("D:/temp/recete.pdf");

        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition",
                "attachment; filename=\"recete.pdf\"");

        return response.build();

    }

    @GET
    @Path("/downloadHListe")
    @Produces("application/pdf")
    public Response downloadHListe() throws SQLException, FileNotFoundException, DocumentException {

        PdfGenerator.generateHastaList();

        File file = new File("D:/temp/hastalistesi.pdf");

        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition",
                "attachment; filename=\"hastalistesi.pdf\"");

        return response.build();

    }

    @GET
    @Path("/downloadRListe")
    @Produces("application/pdf")
    public Response downloadRListe() throws SQLException, FileNotFoundException, DocumentException {

        PdfGenerator.generateRandevuList();

        File file = new File("D:/temp/randevulistesi.pdf");

        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition",
                "attachment; filename=\"randevulistesi.pdf\"");

        return response.build();

    }

    @GET
    @Path("/downloadMListe")
    @Produces("application/pdf")
    public Response downloadMListe() throws SQLException, FileNotFoundException, DocumentException {

        PdfGenerator.generateMuayeneList();

        File file = new File("D:/temp/muayenelistesi.pdf");

        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition",
                "attachment; filename=\"muayenelistesi.pdf\"");

        return response.build();

    }

    @GET
    @Path("/hastaListeCsv")
    @Produces("application/csv")
    public Response hastaListeCsv() throws SQLException, FileNotFoundException, DocumentException {

        CsvGenerator.generateHastaListCsv();

        File file = new File("D:/temp/hastaList.csv");

        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition",
                "attachment; filename=\"hastaList.csv\"");

        return response.build();

    }

    @GET
    @Path("/randevuListeCsv")
    @Produces("application/csv")
    public Response randevuListeCsv() throws SQLException, FileNotFoundException, DocumentException {

        CsvGenerator.generateRandevuListCsv();

        File file = new File("D:/temp/randevuList.csv");

        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition",
                "attachment; filename=\"randevuList.csv\"");

        return response.build();

    }

    @GET
    @Path("/muayeneListeCsv")
    @Produces("application/csv")
    public Response muayeneListeCsv() throws SQLException, FileNotFoundException, DocumentException {

        CsvGenerator.generateMuayeneListCsv();

        File file = new File("D:/temp/muayeneList.csv");

        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition",
                "attachment; filename=\"muayeneList.csv\"");

        return response.build();

    }

}
