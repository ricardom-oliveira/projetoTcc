package br.com.projetoTcc.service.impl;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.projetoTcc.model.Competence;
import br.com.projetoTcc.model.User;
import br.com.projetoTcc.model.enums.Categories;
import br.com.projetoTcc.repository.CompetenceRepository;
import br.com.projetoTcc.service.CompetenceService;

@Service
@Transactional
public class CompetenceServiceImpl implements CompetenceService {
	
	@Autowired
	CompetenceRepository competenceRepository;
	
	@Override
	public Competence save(Competence competence) {
		return competenceRepository.save(competence);
	}

	@Override
	public Competence update(Competence competence) {
		return null;
	}

	@Override
	public Competence findById(int id) {
		 return competenceRepository.findById(id).get();
	}

	@Override
	public List<Competence> findAll() {
		return (List<Competence>) competenceRepository.findAll();
	}

	@Override
	public List<Competence> findByCategorie(String categorie) {
		return competenceRepository.findByCategorie(categorie);
	}

	@Override
	public List<Competence> findByUser(User user) {
		return competenceRepository.findByUser(user);
	}

	@Override
	public void delete(Competence competence) {
		competenceRepository.delete(competence);
	}


	
}
