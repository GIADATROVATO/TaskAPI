package dev.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.store.dto.TaskDto;
import dev.store.entity.Task;

public interface TaskService {
	
	TaskDto createTask(TaskDto dto,String username);
	Page<TaskDto>getUsernameTask( String username,Pageable pageable);
	TaskDto getTaskdById(Long id,String username);
	TaskDto updateTask(TaskDto dto,Long id, String username);
	void deleteTask(Long id, String username);
	
//tolgo Optional perchè sarà il service a decidere se ci sarà qualcosa da restituire o lancerà lui l'eccezione	
}
