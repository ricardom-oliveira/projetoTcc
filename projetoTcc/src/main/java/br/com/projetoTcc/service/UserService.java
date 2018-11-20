package br.com.projetoTcc.service;

import java.util.Collection;

import br.com.projetoTcc.model.User;


public interface UserService {

    User save(User user);

    Boolean delete(int id);

    User update(User user);

    User findById(int id);

    User findByUserName(String username);

    User findByEmail(String email);

    Collection<User> findAll();
    
    Collection<User> findAllByRole(int role);
}
