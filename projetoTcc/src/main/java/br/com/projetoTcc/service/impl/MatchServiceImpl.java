package br.com.projetoTcc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projetoTcc.model.Match;
import br.com.projetoTcc.model.Task;
import br.com.projetoTcc.model.User;
import br.com.projetoTcc.repository.MatchRepository;
import br.com.projetoTcc.repository.TaskRepository;
import br.com.projetoTcc.repository.UserRepository;
import br.com.projetoTcc.service.MatchService;
import br.com.projetoTcc.service.TaskService;

import javax.transaction.Transactional;

import java.util.Collection;
import java.util.List;


@Service
@Transactional
public class MatchServiceImpl implements MatchService {
	
	@Autowired
	private MatchRepository matchRepository;
	
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
    public Boolean delete(int id) {
        if (matchRepository.existsById(id)) {
        	matchRepository.deleteById(id);
            return true;
        }
        return false;
    }

   
}
