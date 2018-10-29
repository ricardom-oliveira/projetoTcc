package br.com.projetoTcc.utils;

public enum Categories {
	Roupas("Roupas"),
	Automóveis("Automóveis"),
	Computação("Computação"),
    Cozinha("Cozinha");
	private String value;

	Categories(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
