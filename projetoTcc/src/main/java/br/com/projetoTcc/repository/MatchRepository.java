package br.com.projetoTcc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.projetoTcc.model.Match;
import br.com.projetoTcc.model.User;

public interface MatchRepository extends CrudRepository<Match, Integer> {

	Match findById(int id);

	
	List<Match> findByUserReceiver(User userReceiver);
	
	List<Match> findByUserRequest(User userRequest);
	
	List<Match> findByMatchStatus(String status);

	List<Match> findByUserRequestAndMatchStatus(User userRequest, String status);
	
	List<Match> findByUserReceiverAndMatchStatus(User userReceiver, String status);

	//@Query("from Match m where m.id = id and m.user_receiver = userReceiver")
	//List<Match> findMatchsByUserReceiver(@Param("id") int id, @Param("userReceiver") User userReceiver);
}