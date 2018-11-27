package br.com.projetoTcc.service;

import java.util.List;

import br.com.projetoTcc.model.Competence;
import br.com.projetoTcc.model.User;

public interface CompetenceService {

	Competence save(Competence competence);

	void delete(Competence competence);
	
    Competence update(Competence competence);

    Competence findById(int id);

    List<Competence> findAll();

    List<Competence> findByCategorie(String categorie);

    List<Competence> findByUser(User user);

}
