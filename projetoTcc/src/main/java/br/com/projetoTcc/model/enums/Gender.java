package br.com.projetoTcc.model.enums;

public enum Gender {
	masculino("masculino"), 
	feminino("feminino"), 
	outro("outro");

	private String value;

	Gender(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
