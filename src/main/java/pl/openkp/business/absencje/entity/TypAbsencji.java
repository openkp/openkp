package pl.openkp.business.absencje.entity;

import javax.json.JsonValue;

public enum TypAbsencji {
	BEZPLATNA("bezpłatna"), URLOP("urlop"), CHOROBA("choroba"), SZPITAL("szpital");
	
	private String opis;
	
	private TypAbsencji(String opis) {
		this.opis = opis;
	}
	
	public String getOpis() {
		return opis;
	}
	
	@Override
	public String toString() {
		return opis;
	}

	public static TypAbsencji fromOpis(String opis) {
		for (TypAbsencji typ : TypAbsencji.values()) {
			if (typ.getOpis().equals(opis)) {
				return typ;
			}
		}
		return null;
	}
}
