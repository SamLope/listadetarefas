# Sistema de Gerenciamento de Tarefas

##  Descrição
Sistema desenvolvido em Spring Boot para gerenciamento de tarefas do dia a dia, como trabalho acadêmico.

##  Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Lombok**


##  Funcionalidades
- Criar tarefas
- Listar todas as tarefas
- Buscar tarefa por ID
- Alterar tarefas
- Deletar tarefas

##  Estrutura do Projeto

listadetarefas/

├── entity/Tarefa.java

├── repository/TarefaRepository.java

├── service/TarefaService.java

├── controller/TarefaController.java

├── service/TarefaServiceTest.java

└── controller/TarefaControllerTest.java



##  Configuração e Execução

### Pré-requisitos
- Java 17
- Maven
- PostgreSQL

### 1. Configurar Banco de Dados

CREATE DATABASE lista_tarefas;


### 2. Executar Aplicação

mvn spring-boot:run

## Endpoints da API

Método	URL	Descrição

GET	/api/tarefas	Listar todas as tarefas

GET	/api/tarefas/{id}	Buscar tarefa por ID

POST	/api/tarefas	Criar nova tarefa

PUT	/api/tarefas/{id}	Alterar tarefa existente

DELETE	/api/tarefas/{id}	Deletar tarefa

## Testes

# Executar testes
mvn test

## Autor

Samir Lopes Rosa - Trabalho acadêmico


