package br.com.projetoTcc.utils;

public enum OccupationCategories {
	costureiro("costureiro"),
	motorista("motorista"),
    programador("programador"),
    marceneiro("marceneiro"),
    cozinheiro("cozinheiro");
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
