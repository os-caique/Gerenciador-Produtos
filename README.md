dot# 📦 Gerenciador de Produtos - API REST

API backend para gerenciamento de produtos com **Spring Boot, MySQL, JWT e Docker**.

## 🚀 Tecnologias
- Java 17
- Spring Boot 3
- MySQL 8
- JWT (Autenticação)
- Mockito (Testes)
- Docker
- OpenAPI (Swagger)

---

## 🔧 Pré-requisitos
Você precisa ter instalado:
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/install/)

---

## 📥 Como Executar o Projeto

 1. Clone o repositório
```bash
git clone https://github.com/Oliveira-Caique/GerenciadorDeProdutos.git
cd GerenciadorDeProdutos
```
 2. Inicie os containers (API + MySQL)
```bash
docker-compose up --build
```
 3. Acesse o Swagger (Documentação)
```bash
http://localhost:8080/swagger-ui.html
```
## 🔒 Autenticação

Para usar os endpoints protegidos:

 1. Faça login em POST /login com:
```bash
{
  "email": "admin@example.com",
  "password": "senha123"
}
```
 2. Copie o token JWT retornado

 3. No Swagger, clique em "Authorize" e cole o token






