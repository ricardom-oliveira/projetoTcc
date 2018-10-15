package br.com.projetoTcc.utils;

public enum OccupationCategories {
	COSTUREIRA("COSTUREIRA"),
	MOTORISTA("MOTORISTA"),
    PROGRAMADOR_JAVA("PROGRAMADOR JAVA"),
    MARCENEIRO("MARCENEIRO"),
    COZINHEIRO("COZINHEIRO");
	private String value;

	OccupationCategories(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
