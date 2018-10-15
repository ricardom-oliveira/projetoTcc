package br.com.projetoTcc.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.projetoTcc.model.User;


public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);
}