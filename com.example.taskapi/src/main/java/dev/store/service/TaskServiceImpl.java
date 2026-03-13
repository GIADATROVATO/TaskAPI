package dev.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.store.dto.TaskDto;
import dev.store.entity.Task;
import dev.store.entity.User;
import dev.store.repository.TaskRepository;
import dev.store.repository.UserRepository;
import exception.TaskNotFoundException;
import exception.UserNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class TaskServiceImpl implements TaskService{
	private final TaskRepository repository;
	private final UserRepository userRepository;
	
	public TaskServiceImpl(TaskRepository repository,UserRepository userRepository) {
	this.repository=repository;
	this.userRepository=userRepository;
	}
	//GET 
	@Override
	public Page<TaskDto> getUsernameTask(String username, org.springframework.data.domain.Pageable pageable) {
		return repository.findByUserUsername( username,pageable).map(this::mapToDto);
		
	}
	//CREATE
	@Override
	public TaskDto createTask(TaskDto dto, String username) {
		
		User u= userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("user not found"));
		
		Task task= new Task();
		mapToEntity(dto,task);
		
		//collego la task all'utente
		task.setUser(u);
		Task saved=repository.save(task);
		
		return mapToDto(saved);
	}	
	
	//READ BY ID
	@Override
	public TaskDto getTaskdById(Long id,String username) {	
		
		Task task= repository.findByIdAndUserUsername(id,username).orElseThrow(()-> new TaskNotFoundException(
				"Task with id "+ id +" not found"));
		return mapToDto(task);
	}

	//UPDATE
	@Override
	public TaskDto updateTask(TaskDto dto,Long id, String username) {
		
		Task task= repository.findByIdAndUserUsername(id, username).orElseThrow(()-> new TaskNotFoundException(
				"Task with id "+ id +" not found"));
		
		mapToEntity(dto, task);
		Task updated= repository.save(task);
		return mapToDto(updated);		
	}
	//DELETE
	@Override
	public void deleteTask(Long id, String username) {
		
		Task task= repository.findByIdAndUserUsername(id, username).orElseThrow(()-> new TaskNotFoundException(
				"Task with id "+ id +" not found"));
		repository.delete(task);
	
	}

//MAPPING
	public void mapToEntity(TaskDto dto,Task task ) {
		/*
		 * non metto setId, perche viene generato dal db e non deve essere modificato o copiato dal DTO
		 */
		task.setCompleted(dto.isCompleted());
		task.setDescription(dto.getDescription());
		task.setDueDate(dto.getDueDate());
		task.setTitle(dto.getTitle());			
	}
	public TaskDto mapToDto(Task task) {
		TaskDto dto= new TaskDto();
		if(task==null) return null;
		dto.setCompleted(task.isCompleted());
		dto.setDescription(task.getDescription());
		dto.setDueDate(task.getDueDate());
		dto.setId(task.getId());
		dto.setTitle(task.getTitle());			
		return dto;		
	}
	
	
}
