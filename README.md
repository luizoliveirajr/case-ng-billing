# 💼 Case NG Billing

Este é um teste técnico desenvolvido para a empresa **NG Billing**. O projeto consiste em uma API REST utilizando tecnologias modernas do ecossistema Java.

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Docker & Docker Compose** – Containerização da aplicação e dos serviços
- **JUnit** – Testes unitários
- **Swagger** – Documentação da API
- **MySQL** – Banco de dados
- **Flyway** – Controle de versões do banco de dados (migrações)

---

## ✅ Pré-requisitos

Antes de iniciar o projeto, verifique se você possui os seguintes itens instalados em sua máquina:

- [JDK 17](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Git](https://git-scm.com/)

---

## 📦 Como executar o projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/luizoliveirajr/case-ng-billing.git
```

### 2. Importar na sua IDE

Você pode importar o projeto como um projeto Maven em IDEs como **IntelliJ IDEA**, **Eclipse** ou similares.

---

### 🖥️ Execução Local (sem Docker)

1. Suba um banco MySQL localmente ou configure o `application.yml` para apontar para um banco já existente.
2. Execute o projeto com o comando:

```bash
mvn spring-boot:run
```

---

### 🐳 Execução com Docker Compose

1. Suba os containers (aplicação + MySQL):

```bash
docker-compose up --build
```

> Isso irá construir e iniciar os containers necessários para rodar a aplicação integrada ao banco de dados MySQL.

---

## 📚 Documentação da API

Após iniciar a aplicação, acesse a documentação Swagger em:

```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Rodando os Testes

Para executar os testes unitários da aplicação:

```bash
mvn test
```

---

## 👨‍💻 Autor

Desenvolvido por [Luiz Oliveira](https://github.com/luizoliveirajr) no contexto de um teste técnico para a empresa NG Billing.
