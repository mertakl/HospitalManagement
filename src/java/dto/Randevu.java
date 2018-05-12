package dto;

public class Randevu {

	private int id;
	
	private String tarih;
	
	private String saat;
	
	private long tcKimlik;
	
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

	public long getTcKimlik() {
		return tcKimlik;
	}

	public void setTcKimlik(long tcKimlik) {
		this.tcKimlik = tcKimlik;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTarih() {
		return tarih;
	}

	public void setTarih(String tarih) {
		this.tarih = tarih;
	}

	public String getSaat() {
		return saat;
	}

	public void setSaat(String saat) {
		this.saat = saat;
	}

}
