package br.com.projetoTcc.service;

import org.springframework.data.jpa.repository.Query;

import br.com.projetoTcc.model.Task;

import java.util.List;


public interface TaskService {

    Task save(Task task);

    Boolean delete(int id);

    Task update(Task task);

    Task findById(int id);

    List<Task> findAll();

    List<Task> findByStatus(String status);

    List<Task> findByUserIdStatus(int userId, String status);

    List<Task> findBetween(int start, int end);

}
