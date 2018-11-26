package br.com.projetoTcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.projetoTcc.model.Match;
import br.com.projetoTcc.model.User;

public interface MatchRepository extends CrudRepository<Match, Integer> {
		
	Match findById(int id);

	
	List<Match> findByUserRequest(User user);
	
	List<Match> findByUserReceiver(User user);
	
	List<Match> findByMatchStatus(String status);

	List<Match> findByUserRequestAndMatchStatus(User userRequest, String status);
	
	List<Match> findByUserReceiverAndMatchStatus(User userReceiver, String status);


	//
	//List<Match> findMatchsByUserReceiver(@Param("id") int id, @Param("userReceiver") User userReceiver);
}