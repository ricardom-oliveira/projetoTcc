package br.com.projetoTcc.service;

import java.util.List;

import br.com.projetoTcc.model.Competence;
import br.com.projetoTcc.model.User;
import br.com.projetoTcc.utils.Categories;

public interface CompetenceService {

	Competence save(Competence competence);

    Boolean delete(int id);

    Competence update(Competence competence);

    Competence findById(int id);

    List<Competence> findAll();


    List<Competence> findByCategorie(Categories categorie);

    List<Competence> findByUser(User user);

}
