Task API – REST API con Autenticazione JWT Completa

Questo progetto consiste in una REST API per la gestione di Task, sviluppata con architettura a livelli e sistema di autenticazione basato su JWT.
Rappresenta l’evoluzione di un primo tentativo di integrazione della sicurezza, con un’implementazione completa del flusso di autenticazione:

* Registrazione utente
* Login con generazione token
* Validazione token tramite filtro JWT
* Gestione ruoli

--> Architettura del progetto

Controller
- AuthController                gestione login e registrazione
- TaskController                gestione CRUD delle Task
                                
DTO
- AuthRequest                   credenziali inviate dal client (username, password)
- AuthResponse                  risposta del server con JWT, username, ruolo
- RegisterRequest               dati per la registrazione
- TaskDto                       trasferimento dati delle Task
                                
Security                      
- SecurityConfig                configurazione Spring Security
- JwtService                    generazione e validazione del token
- JwtAuthFilter                 intercettazione richieste e verifica JWT
- CustomUserDetailsService      integrazione con sistema utenti
  
Service Layer
- AuthService                   logica di autenticazione
- TaskService / TaskServiceImpl logica applicativa Task
  
Repository
- UserRepository
- TaskRepository
  
Gestione Errori
- GlobalExceptionHandler
- Eccezioni personalizzate (TaskNotFoundException, UserNotFoundException)

--> Flusso di Autenticazione 

1. Il client invia credenziali tramite AuthRequest
2. Il server valida l’utente
3. Viene generato un JWT tramite JwtService
4. Il token viene restituito tramite AuthResponse
5. Le richieste successive includono il token nell’header Authorization
6. JwtAuthFilter valida il token prima di accedere ai controller protetti

--> Obiettivi 

* Comprendere il flusso completo di autenticazione JWT
* Integrare Spring Security in un’API REST
* Separare dominio, DTO e logica applicativa
* Gestire ruoli e autorizzazioni
* Implementare gestione centralizzata delle eccezioni

--> Tecnologie Utilizzate
* Java
* Spring Boot
* Spring Security
* JWT
* JPA / Repository pattern
* Architettura a livelli (Controller – Service – Repository)

Progetto sviluppato con finalità formative per consolidare competenze backend avanzate, con particolare attenzione alla sicurezza applicativa e all’architettura REST.
