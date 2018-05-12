package dto;

public class Recete {

	private int id;

	private String ilac;
	
	private String kullanim_sekli;

	private long hasta_tc_kimlik;
	
	private int kutu_adet;
	
	private long recete_no;
	

	public long getRecete_no() {
		return recete_no;
	}

	public void setRecete_no(long recete_no) {
		this.recete_no = recete_no;
	}

	public int getKutu_adet() {
		return kutu_adet;
	}

	public void setKutu_adet(int kutu_adet) {
		this.kutu_adet = kutu_adet;
	}

	public String getKullanim_sekli() {
		return kullanim_sekli;
	}

	public void setKullanim_sekli(String kullanim_sekli) {
		this.kullanim_sekli = kullanim_sekli;
	}

	public long getHasta_tc_kimlik() {
		return hasta_tc_kimlik;
	}

	public void setHasta_tc_kimlik(long hasta_tc_kimlik) {
		this.hasta_tc_kimlik = hasta_tc_kimlik;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIlac() {
		return ilac;
	}

	public void setIlac(String ilac) {
		this.ilac = ilac;
	}


}
