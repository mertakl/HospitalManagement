package dto;

public class Login {
	
	private String kullanici;
	
	private String sifre;
	
	private boolean gecerli;
	

	public boolean isGecerli() {
		return gecerli;
	}

	public void setGecerli(boolean gecerli) {
		this.gecerli = gecerli;
	}

	public String getKullanici() {
		return kullanici;
	}

	public void setKullanici(String kullanici) {
		this.kullanici = kullanici;
	}

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}
	

}
