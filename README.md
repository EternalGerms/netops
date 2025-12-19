# NetOps

Sistema de gestão de ativos para ISP, focado em controle de estoque de roteadores/ONUs, histórico de chamados e prevenção de retirada de equipamentos em uso.

## Tecnologias

- **Java 21**
- **Spring Boot 3** (Web, Data JPA, Validation)
- **PostgreSQL** (Docker Container)
- **Lombok** & **Maven**

## Configuração do Ambiente

### 1. Subir o Banco de Dados (Docker)
Certifique-se de ter o Docker rodando e execute o comando abaixo para criar o container do PostgreSQL:

```bash
docker run --name netops-db \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin \
  -e POSTGRES_DB=netops_db \
  -p 5433:5433 \
  -d postgres:15-alpine
```

### 2. Rodar a Aplicação
Execute a classe principal NetopsApplication.java
```bash
./mvnw spring-boot:run
```
A aplicação subirá em: http://localhost:8081

## Testando a API (Exemplos)

Criar Novo Equipamento (POST)
```bash
curl -X POST http://localhost:8081/equipamentos \
-H "Content-Type: application/json" \
-d "{\"marca\": \"Cisco\", \"modelo\": \"Catalyst 2960\", \"macAddress\": \"AA:BB:CC:00:11:22\", \"status\": \"DISPONIVEL\", \"numeroSerie\": \"CS123\"}"
```

Listar Tudo (GET)
```bash
curl http://localhost:8081/equipamentos
```

Tentar Excluir Equipamento em Uso (DELETE)
Regra de Negócio: Deve retornar erro 409 (Conflict)
```bash
curl -X DELETE http://localhost:8081/equipamentos/2 -v
```

Excluir Equipamento Disponível (DELETE)
Deve retornar status 204 (No Content)
```bash
curl -X DELETE http://localhost:8081/equipamentos/1 -v
```

## Estrutura do Projeto
- model: Entidades JPA (Tabelas do banco)
- repository: Interfaces de acesso a dados
- service: Regras de negócio (ex: validação de exclusão)
- controller: Endpoints REST
