package dev.store.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.store.dto.TaskDto;
import dev.store.service.TaskServiceImpl;
import payload.ApiResponse;
@RestController
@RequestMapping("/tasks")
public class TaskController {
	private final TaskServiceImpl service;
	public TaskController(TaskServiceImpl i) {
		this.service=i;
	}
	@GetMapping 
	public ResponseEntity<ApiResponse<Page<TaskDto>>> getTasks(Pageable p, Authentication a){
		String username=a.getName();
		Page<TaskDto> tasks= service.getUsernameTask(username, p);
		return ResponseEntity.ok(ApiResponse.success(tasks, "Tasks retrieved successfully"));
	}
	//CREATE
	@PostMapping
	public ResponseEntity<ApiResponse<TaskDto>> create(@RequestBody TaskDto dto, Authentication authentication){
		String username=authentication.getName();
		TaskDto created= service.createTask(dto,username);						// restituisce 201 CREATED
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(created,"Task created successful"));
	}
	
	//READ BY ID
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<TaskDto>> findById(@PathVariable Long id, Authentication auth) {
		String username= auth.getName();
		TaskDto task=service.getTaskdById(id,username);
		return ResponseEntity.ok(ApiResponse.success(task,"task retrieved successfully"));
	}
	//UPDATE
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<TaskDto>>update(@PathVariable Long id, @RequestBody TaskDto d, Authentication auth){
		String username=auth.getName();
		TaskDto dto= service.updateTask(d, id,username);
		return ResponseEntity.ok(ApiResponse.success(dto,"Task retrieved successfully"));		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)								// se tutto va bene restituisci 204 
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id, Authentication auth){
		String username=auth.getName();
		service.deleteTask(id, username);
		return ResponseEntity.ok(ApiResponse.success(null, "task deleted successfully"));
	}
}
