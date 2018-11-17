package br.com.projetoTcc.model.enums;

public enum MatchStatus {
	WAITING("WAITING"), ACCEPTED("ACCEPTED"), REJECTED("REJECTED");
    
	private String value;

	MatchStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
