package model;

public class Aeroport {
	private String codeAeroport; // code AITA
	private String ville;
	private String pays;
	
	public Aeroport(String codeAeroport, String ville, String pays) {
		this.codeAeroport = codeAeroport;
		this.ville = ville;
		this.pays = pays;
	}

	public String getCodeAeroport() {
		return codeAeroport;
	}

	public void setCodeAeroport(String codeAeroport) {
		this.codeAeroport = codeAeroport;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}
	
	// retourne un tableau de chaines de caractères avec le
	// code aéroport, la ville et le pays
	public String[] toArray(){
		return new String[]{codeAeroport,ville,pays};
	}
}
