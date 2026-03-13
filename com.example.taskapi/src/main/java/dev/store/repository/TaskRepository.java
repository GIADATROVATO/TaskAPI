package dev.store.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import dev.store.entity.Task;

public interface TaskRepository extends JpaRepository<Task,Long>{
	Optional<Task> findAllbyUsername(String username);
	void deleteByTitle(String title);
	boolean existsByTitle(String title);
	
	Optional<Task> findByIdAndUserUsername(Long id, String username);
	Page<Task> findByUserUsername(String username, Pageable pageable);
	
}
