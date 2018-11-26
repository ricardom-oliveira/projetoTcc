package br.com.projetoTcc.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projetoTcc.controller.GlobalController;
import br.com.projetoTcc.model.Competence;
import br.com.projetoTcc.model.Match;
import br.com.projetoTcc.model.User;
import br.com.projetoTcc.model.enums.Categories;
import br.com.projetoTcc.model.enums.MatchStatus;
import br.com.projetoTcc.model.enums.Roles;
import br.com.projetoTcc.repository.MatchRepository;
import br.com.projetoTcc.service.CompetenceService;
import br.com.projetoTcc.service.MatchService;
import br.com.projetoTcc.service.UserService;
import br.com.projetoTcc.utils.MatchFilter;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CompetenceService competenceService;

	@Autowired
	private GlobalController globalController;
	
	@Override
	public Match findById(int id) {
		return matchRepository.findById(id);
	}

	@Override
	public List<Match> findByUserReceiver(User userReceiver) {
		return matchRepository.findByUserReceiver(userReceiver);
	}

	@Override
	public List<Match> findByUserRequest(User userRequest) {
		return matchRepository.findByUserRequest(userRequest);
	}

	@Override
	public List<Match> findByMatchStatus(String status) {
		return matchRepository.findByMatchStatus(status);
	}

	@Override
	public List<Match> findByUserRequestAndMatchStatus(User userRequest, String status) {
		return matchRepository.findByUserRequestAndMatchStatus(userRequest, status);

	}

	@Override
	public List<Match> findByUserReceiverAndMatchStatus(User userReceiver, String status) {
		return matchRepository.findByUserReceiverAndMatchStatus(userReceiver, status);
	}

	@Override
	public Collection<Match> findAll() {
		return findAll();
	}

	@Override
	public Match save(Match match) {
		return matchRepository.save(match);
	}

	@Override
	public void delete(Match match) {
		matchRepository.delete(match);		
	}

	@Override
	public Collection<User> findUsersOkToMatch(User userLogin) {

		Collection<User> allUsers = userService.findAllByRole(Roles.ROLE_USER.getValue());

		List<Match> matchsUserLogin = findByUserRequest(userLogin);

		Collection<User> userOkToMatch = new ArrayList<User>();
		for (User user : allUsers) {
			boolean hasMatch = false;
			if (!matchsUserLogin.isEmpty() || matchsUserLogin == null) {
				for (Match match : matchsUserLogin) {
					if ((user.getId() == match.getUserReceiver().getId()) || (userLogin.getId() == user.getId())) {
						if(match.getMatchStatus().equals(MatchStatus.WAITING.getValue()) || match.getMatchStatus().equals(MatchStatus.IGNORED.getValue())) {
							hasMatch = true;
						}	
					}
				}
				
				if (!hasMatch) {
					userOkToMatch.add(user);
				}else {
					if (!hasMatch && userLogin.getId() != user.getId())
						userOkToMatch.add(user);					
				}
				
			}else {
				if (userLogin.getId() != user.getId())
					userOkToMatch.add(user);
			}

		}
		return userOkToMatch;

	}

	@Override
	public Match checkIfMatchExists(User userMatch, User userLogin) {
		List<Match> matchsUserLogin = findByUserReceiver(userLogin);

		if (matchsUserLogin != null) {
			for (Match match : matchsUserLogin) {
				if (match.getUserRequest().getId() == userMatch.getId()) {
					return match;
				}
			}
		}
		return null;

	}

	@Override
	public Match setToAccepted(Match match) {
		match.setMatchStatus(MatchStatus.ACCEPTED.getValue());
		return save(match);
	}
	
	@Override
	public Match setToIgnored(Match match) {
		match.setMatchStatus(MatchStatus.IGNORED.getValue());
		return save(match);
	}
	
	@Override
	public Match setToWaiting(Match match) {
		match.setMatchStatus(MatchStatus.WAITING.getValue());
		return save(match);
	}

	

}
