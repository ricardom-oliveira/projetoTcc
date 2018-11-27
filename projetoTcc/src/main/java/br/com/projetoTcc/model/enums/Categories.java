package br.com.projetoTcc.model.enums;

public enum Categories {
	Roupas("Roupas"),
	Automóveis("Automóveis"),
	Informatica("Informática"),
    Gastronomia("Gastronomia"),
    Construção("Construção"),
    Marcenaria("Marcenaria"),
    Arquitetura("Arquitetura"),
    Aulas("Aulas"),
    Musica("Musica"),
    Estética("Estética"),
    Arte("Arte"),
    Consultoria("Consultoria"),
    Outros("Outros");
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
