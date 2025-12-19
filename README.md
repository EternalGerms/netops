# NetOps

> **Sistema de Gestão de Ativos para Provedores de Internet (ISP)**

API RESTful desenvolvida para controlar o ciclo de vida de equipamentos de rede (Roteadores, ONUs, Switchs), gerenciar vínculos com técnicos e garantir a integridade do estoque.

---

## Tecnologias & Arquitetura

* **Linguagem:** Java 21 (LTS)
* **Framework:** Spring Boot 3
* **Segurança:** Spring Security 6 + JWT (Stateless) + BCrypt
* **Banco de Dados:** PostgreSQL 15 (via Docker)
* **Testes:** JUnit 5 + Mockito
  
* **Arquitetura:**
* PADRÃO **DTO** (Data Transfer Object) com Java Records.
* Mappers para conversão de entidades.
* **Global Exception Handler** (`@RestControllerAdvice`).
* Validação de Dados (`Bean Validation`).


---

## Configuração do Ambiente

### 1. Pré-requisitos

* Java 21.
* Docker.
* Maven.

### 2. Subir o Banco de Dados

O sistema utiliza PostgreSQL em container.

```bash
docker run --name netops-db \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin \
  -e POSTGRES_DB=netops_db \
  -p 5433:5432 \
  -d postgres:15-alpine

```

*(Nota: O banco estará acessível na porta 5433 do host para evitar conflitos)*

### 3. Rodar a Aplicação

```bash
./mvnw spring-boot:run

```

A API estará disponível em: `http://localhost:8081`

---

## Autenticação & Segurança (JWT)

### 1. Registrar Usuário (Criar Conta)

```bash
curl -X POST http://localhost:8081/auth/register \
-H "Content-Type: application/json" \
-d "{\"login\": \"admin\", \"senha\": \"123456\", \"role\": \"ADMIN\"}" -v

```

### 2. Login (Gerar Token)

```bash
curl -X POST http://localhost:8081/auth/login \
-H "Content-Type: application/json" \
-d "{\"login\": \"admin\", \"senha\": \"123456\"}" -v

```

**Resposta (Exemplo):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}

```

---

## Endpoints Principais

### Equipamentos

**Listar Equipamentos (Protegido)**

```bash
curl http://localhost:8081/equipamentos \
-H "Authorization: Bearer SEU_TOKEN_AQUI"

```

**Criar Novo Equipamento (Protegido)**
Regra: O sistema valida campos obrigatórios via DTO.

```bash
curl -X POST http://localhost:8081/equipamentos \
-H "Content-Type: application/json" \
-H "Authorization: Bearer SEU_TOKEN_AQUI" \
-d "{\"marca\": \"MikroTik\", \"modelo\": \"CCR1009\", \"macAddress\": \"AA:BB:CC:DD:EE:FF\", \"status\": \"DISPONIVEL\", \"numeroSerie\": \"SN999\"}" -v

```

**Devolução de Equipamento**
Regra de Negócio: Só é possível devolver equipamentos que constam como `EM_USO`.

```bash
curl -X PUT "http://localhost:8081/equipamentos/1/devolucao?status=DISPONIVEL" \
-H "Authorization: Bearer SEU_TOKEN_AQUI" -v

```

---

## Testes Automatizados

O projeto conta com cobertura de testes unitários na camada de serviço, isolando o banco de dados com **Mocks**.

Para rodar os testes:

```bash
./mvnw test

```

**Cenários Cobertos:**

* Sucesso ao vincular técnico.
* Erro ao tentar vincular equipamento já em uso.
* Validação de status de devolução.

---

## Estrutura de Pastas

```
com.netops.api
├── controller       # Camada de entrada (Endpoints REST)
├── service          # Regras de Negócio
├── repository       # Acesso a Dados (JPA)
├── model            # Entidades (Banco de Dados)
├── dto              # Objetos de Transferência (Records) & Mappers
├── security         # Configuração JWT, SecurityFilterChain e Filtros
└── exception        # Tratamento Global de Erros (ControllerAdvice)

```

---
