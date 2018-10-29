package br.com.projetoTcc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.projetoTcc.model.Competence;
import br.com.projetoTcc.model.User;
import br.com.projetoTcc.utils.Categories;

public interface CompetenceRepository extends CrudRepository<Competence, Integer> {

    
    List<Competence> findByCategorie(Categories categorie);
    
   
    List<Competence> findByUser(User userId);




}