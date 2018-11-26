package br.com.projetoTcc.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projetoTcc.model.Competence;
import br.com.projetoTcc.model.Match;
import br.com.projetoTcc.model.User;
import br.com.projetoTcc.model.enums.MatchStatus;
import br.com.projetoTcc.repository.UserRepository;
import br.com.projetoTcc.service.CompetenceService;
import br.com.projetoTcc.service.MatchService;
import br.com.projetoTcc.service.UserService;
import br.com.projetoTcc.utils.CompetenceFilter;
import br.com.projetoTcc.utils.MatchFilter;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CompetenceService competenceService;
	
	@Autowired
	private MatchService matchService;

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public Boolean delete(int id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public User update(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findById(int id) {
		return userRepository.findById(id).get();
	}

	@Override
	public User findByUserName(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Collection<User> findAll() {
		Iterable<User> itr = userRepository.findAll();
		return (Collection<User>) itr;
	}

	@Override
	public Collection<User> findAllByRole(int role) {
		Iterable<User> itr = userRepository.findAllByRole(role);
		return (Collection<User>) itr;
	}

	@Override
	public List<Match> filterMatchsReceivers(MatchFilter matchFilter, User user) {
		String matchStatus = matchFilter.getMatchStatus();

		List<Match> matchs = new ArrayList<Match>();

		List<Match> matchsReceivers = new ArrayList<Match>();
		if (matchStatus.equals("IGNORED")) {
			return matchsReceivers;
		}

		if (matchStatus.equals("WAITING")) {
			return matchsReceivers;
		}
		if (matchStatus.equals("NEWS")) {
			matchStatus = MatchStatus.WAITING.getValue();
		}

		matchsReceivers.addAll(user.getMatchsReceiver());

		if (matchStatus.equals("all")) {
			return matchsReceivers;
		}

		for (Match match : matchsReceivers) {
			if (match.getMatchStatus().equals(matchStatus)) {
				matchs.add(match);
			}
		}

		return matchs;
	}

	@Override
	public List<Match> filterMatchsRequests(MatchFilter matchFilter, User user) {
		String matchStatus = matchFilter.getMatchStatus();

		List<Match> matchs = new ArrayList<Match>();

		List<Match> matchsRequests = new ArrayList<Match>();

		if (matchStatus.equals("NEWS")) {
			return matchsRequests;
		}

		matchsRequests.addAll(user.getMatchsRequest());

		if (matchStatus.equals("all")) {
			return matchsRequests;
		}

		for (Match match : matchsRequests) {
			if (match.getMatchStatus().equals(matchStatus)) {
				matchs.add(match);
			}
		}

		return matchs;
	}

	@Override
	public int findNumberOfnewMatchs(User user) {
		String matchStatus = MatchStatus.WAITING.getValue();

		List<Match> matchs = new ArrayList<Match>();

		List<Match> matchsReceiver = new ArrayList<Match>();
		matchsReceiver.addAll(user.getMatchsReceiver());

		for (Match match : matchsReceiver) {
			if (match.getMatchStatus().equals(matchStatus)) {
				matchs.add(match);
			}
		}

		return matchs.size();
	}

	@Override
	public Collection<User> filterUsersByCompetence(CompetenceFilter competenceFilter, User userLogin) {
		String competenceCategorie = competenceFilter.getCompetenceCategorie();

		Collection<Competence> allCompetences = competenceService.findAll();
		
		Collection<Competence> competenceList = getCompetenceList(competenceCategorie, allCompetences);
		Collection<User> userList = getUserList(competenceList, userLogin);


		return userList;
	}

	private Collection<User> getUserList(Collection<Competence> competenceList, User userLogin) {
		Collection<User> usersOkToMatch = matchService.findUsersOkToMatch(userLogin);
		Collection<User> userList = new ArrayList<User>();
		
		for (User user : usersOkToMatch) {
			Collection<Competence> competencesUser =  user.getCompetences();
			
			if (!competencesUser.isEmpty()) {
				for (Competence competenceUser : competencesUser) {
					boolean hasCategorie = false;
					for (Competence competence : competenceList) {
						if (competenceUser.getCategorie().getValue().equals(competence.getCategorie().getValue()) && !hasCategorie){
							hasCategorie = true;
						}
					}
					if (hasCategorie) {
						if (!userList.contains(user))
							userList.add(user);
					}
				}
			}
		}
		
		
		return userList;
	
	}

	private Collection<Competence> getCompetenceList(String competenceCategorie, Collection<Competence> allCompetences) {
		Collection<Competence> competenceList = new ArrayList<Competence>();
		
		
		if (!competenceCategorie.equals("all")) {
			for (Competence competence : allCompetences) {
				if(competence.getCategorie().getValue().equals(competenceCategorie)) {
					competenceList.add(competence);
				}
			}
		}else {
			competenceList.addAll(allCompetences);
		}
		
		return competenceList;
	}

}
