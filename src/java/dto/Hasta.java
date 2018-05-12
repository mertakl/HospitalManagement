package dto;

public class Hasta {
	
	private int id;
	
	private long tcKimlik;
	
	private String ulke;
	
	private String pas_no;
	
	private String isim;
	
	private String soyIsim;
	
	private String baba_adi;
	
	private String medeni_hal;
	
	private String dogumTarih;
	
	private String cinsiyet;
	
	private String telefon;
	
	private String email;

	
	
	public String getBaba_adi() {
		return baba_adi;
	}

	public void setBaba_adi(String baba_adi) {
		this.baba_adi = baba_adi;
	}

	public String getMedeni_hal() {
		return medeni_hal;
	}

	public void setMedeni_hal(String medeni_hal) {
		this.medeni_hal = medeni_hal;
	}

	public String getCinsiyet() {
		return cinsiyet;
	}

	public void setCinsiyet(String cinsiyet) {
		this.cinsiyet = cinsiyet;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String meslek;
	
	private boolean randevu;
	
	

	public String getUlke() {
		return ulke;
	}

	public void setUlke(String ulke) {
		this.ulke = ulke;
	}

	public String getPas_no() {
		return pas_no;
	}

	public void setPas_no(String pas_no) {
		this.pas_no = pas_no;
	}

	public boolean isRandevu() {
		return randevu;
	}

	public void setRandevu(boolean randevu) {
		this.randevu = randevu;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getTcKimlik() {
		return tcKimlik;
	}

	public void setTcKimlik(long tcKimlik) {
		this.tcKimlik = tcKimlik;
	}

	public String getIsim() {
		return isim;
	}

	public void setIsim(String isim) {
		this.isim = isim;
	}

	public String getSoyIsim() {
		return soyIsim;
	}

	public void setSoyIsim(String soyIsim) {
		this.soyIsim = soyIsim;
	}

	public String getDogumTarih() {
		return dogumTarih;
	}

	public void setDogumTarih(String dogumTarih) {
		this.dogumTarih = dogumTarih;
	}

	public String getMeslek() {
		return meslek;
	}

	public void setMeslek(String meslek) {
		this.meslek = meslek;
	}
	
	

}
