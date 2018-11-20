package br.com.projetoTcc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.projetoTcc.model.enums.MatchStatus;

@Entity
@Table(name = "matchs", schema = "tccdb")
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true)
	private int id;

	
	@ManyToOne
	@JoinColumn(name="id_user_request", nullable=false)
	private User userRequest;
	
	@ManyToOne
	@JoinColumn(name="id_user_receiver", nullable=false)
	private User userReceiver;
	
	@Column(name = "match_status")
	private String matchStatus;
	
	public Match() {
	}

	public Match (User userRequest, User userReceiver) {
		this.userRequest = userRequest;
		this.userReceiver = userReceiver;
		this.matchStatus = MatchStatus.WAITING.getValue();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public User getUserRequest() {
		return userRequest;
	}


	public void setUserRequest(User userRequest) {
		this.userRequest = userRequest;
	}


	public User getUserReceiver() {
		return userReceiver;
	}


	public void setUserReceiver(User userReceiver) {
		this.userReceiver = userReceiver;
	}


	public String getMatchStatus() {
		return matchStatus;
	}


	public void setMatchStatus(String matchStatus) {
		this.matchStatus = matchStatus;
	}



	


}
