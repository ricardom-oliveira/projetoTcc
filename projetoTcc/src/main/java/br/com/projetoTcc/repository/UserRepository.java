package br.com.projetoTcc.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import br.com.projetoTcc.model.User;


public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);
    
    Collection<User> findAllByRole(int role);
}