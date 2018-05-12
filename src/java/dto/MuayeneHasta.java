package dto;

public class MuayeneHasta {

	private int hasta_muayene_id;

	private long hasta_tc_kimlik;

	private String sikayet;
	
	private String hikaye;

	private String tani1;
	
	private String tani2;
	
	private String tetkik;
	
	private int istem_id;
	
	private String saat;
	
	private String tarih;
	
	private String hasta_isim;
	
	private String hasta_soyisim;

	

	public String getHasta_isim() {
		return hasta_isim;
	}

	public void setHasta_isim(String hasta_isim) {
		this.hasta_isim = hasta_isim;
	}

	public String getHasta_soyisim() {
		return hasta_soyisim;
	}

	public void setHasta_soyisim(String hasta_soyisim) {
		this.hasta_soyisim = hasta_soyisim;
	}

	public String getSaat() {
		return saat;
	}

	public String getTarih() {
		return tarih;
	}

	public void setSaat(String saat) {
		this.saat = saat;
	}

	public void setTarih(String tarih) {
		this.tarih = tarih;
	}

	public int getIstem_id() {
		return istem_id;
	}

	public void setIstem_id(int istem_id) {
		this.istem_id = istem_id;
	}

	public int getHasta_muayene_id() {
		return hasta_muayene_id;
	}

	public void setHasta_muayene_id(int hasta_muayene_id) {
		this.hasta_muayene_id = hasta_muayene_id;
	}

	public long getHasta_tc_kimlik() {
		return hasta_tc_kimlik;
	}

	public void setHasta_tc_kimlik(long hasta_tc_kimlik) {
		this.hasta_tc_kimlik = hasta_tc_kimlik;
	}

	public String getSikayet() {
		return sikayet;
	}

	public void setSikayet(String sikayet) {
		this.sikayet = sikayet;
	}

	public String getHikaye() {
		return hikaye;
	}

	public void setHikaye(String hikaye) {
		this.hikaye = hikaye;
	}

	public String getTani1() {
		return tani1;
	}

	public void setTani1(String tani1) {
		this.tani1 = tani1;
	}

	public String getTani2() {
		return tani2;
	}

	public void setTani2(String tani2) {
		this.tani2 = tani2;
	}

	public String getTetkik() {
		return tetkik;
	}

	public void setTetkik(String tetkik) {
		this.tetkik = tetkik;
	}

}
