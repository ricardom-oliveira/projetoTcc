package br.com.projetoTcc.service;

import java.util.Collection;
import java.util.List;

import br.com.projetoTcc.model.Match;
import br.com.projetoTcc.model.Task;
import br.com.projetoTcc.model.User;


public interface MatchService {
	Match findById(int id);
	
	List<Match> findByUserReceiver(User userReceiver);
	
	List<Match> findByUserRequest(User userRequest);
	
	List<Match> findByMatchStatus(String status);

	List<Match> findByUserRequestAndMatchStatus(User userRequest, String status);
	
	List<Match> findByUserReceiverAndMatchStatus(User userReceiver, String status);
	
	Collection<Match> findAll();


}