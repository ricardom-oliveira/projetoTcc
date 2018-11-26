package br.com.projetoTcc.service;

import java.util.Collection;

import br.com.projetoTcc.model.Match;
import br.com.projetoTcc.model.User;
import br.com.projetoTcc.utils.CompetenceFilter;
import br.com.projetoTcc.utils.MatchFilter;


public interface UserService {

    User save(User user);

    Boolean delete(int id);

    User update(User user);

    User findById(int id);

    User findByUserName(String username);

    User findByEmail(String email);

    Collection<User> findAll();
    
    Collection<User> findAllByRole(int role);
    
    Collection<Match> filterMatchsRequests(MatchFilter matchFilter, User user);

    Collection<Match> filterMatchsReceivers(MatchFilter matchFilter, User user);

	int findNumberOfnewMatchs(User user);

	Collection<User> filterUsersByCompetence(CompetenceFilter competenceFilter, User userLogin);
    
}
