package br.com.projetoTcc.service;

import java.util.Collection;

import br.com.projetoTcc.model.Match;
import br.com.projetoTcc.model.User;


public interface MatchService {
	Match save (Match match);
	
	Match findById(int id);
	
	Collection<Match> findByUserReceiver(User userReceiver);
	
	Collection<Match> findByUserRequest(User userRequest);
	
	Collection<Match> findByMatchStatus(String status);

	Collection<Match> findByUserRequestAndMatchStatus(User userRequest, String status);
	
	Collection<Match> findByUserReceiverAndMatchStatus(User userReceiver, String status);
	
	Collection<Match> findAll();
	
	void delete(Match match);

	Collection<User> findUsersOkToMatch(User userLogin);
	
	Match checkIfMatchExists(User userMatch, User userLogin);

	Match setToAccepted(Match match);
	
	Match setToIgnored(Match match);
	
	Match setToWaiting(Match match);

}
