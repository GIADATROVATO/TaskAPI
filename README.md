TASK API 
Api Rest per la gestione di task con autenticazione basata di JWT. il progetto gestisce utenti, ruoli e task, con controllo accessi. 

Funzionalità principali 
* Autenticazione e Registrazione
    - Login e registrazione utenti tramite JWT (AuthController)
    - Gestione ruoli(Role) e sicurezza(SecurityConfig, JwtService)
* Gestione Task
    - Creazione, aggiornamento, eliminazione e lettura dei task ( TaskController, TaskService)
    - DTO per trasferimento dati (TaskDto)
* Gestioone errori
    - Handler globale per eccezioni ( GlobalExceptionHandler)
    - ApiError e ApiError

Architettura 
│
├── config
│   └── SecurityConfig.java			
│
├── security
│   ├── JwtService.java			
│   ├── JwtAuthFilter.java			
│   └── CustomUserDetailsService.java	
│
├── controller
│   ├── AuthController.java			
│   └── TaskController.java			
│
├── dto
│   ├── AuthRequest.java				 
│   ├── AuthResponse.java				 
│   │      												 	 
│   ├── RegisterRequest.java			 
│   └── TaskDto.java			
│
├── entity
│   ├── User.java				
│   └── Task.java
│   └── Role.java				
│
├── repository
│   ├── UserRepository.java		
│   └── TaskRepository.java		
│
└── service
│   ├── AuthService.java			
│   ├── TaskService.java		
│   ├── TaskServiceImpl.java		
│
├── payload 
│      ├──ApiResponse.java
│      ├──ApiError.java 
│
├── exception 
│   ├── GlobalExceptionHandler.java			
│   ├── TaskNotFoundException.java		
│   ├── UserNotFoundNotFoundException.java
