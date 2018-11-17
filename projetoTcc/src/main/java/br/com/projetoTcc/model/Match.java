package br.com.projetoTcc.model;

import java.util.Objects;

import javax.persistence.*;

import br.com.projetoTcc.model.enums.MatchStatus;

@Entity
@Table(name = "match", schema = "tccdb")
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "user_request", unique = true, length = 30)
	private User userRequest;

	@Column(name = "user_receiver")
	private User userReceiver;
	
	@Column(name = "match_status")
	private String matchStatus;
	

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
