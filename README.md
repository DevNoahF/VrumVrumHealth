# VrumVrumHealth
 
API para gestão de transporte em saúde (agendamento de viagens, pacientes, motoristas e veículos), construída em **Java 17** com **Spring Boot 3** e **MySQL**.
API for health transport managements (trip to Exams, doctors appointmens and treatments, patients, drivers and vehicles), build in **Java 17** with **Spring Boot 3** and **MySQL**<!--Precisa de uma análise maior para poder traduzir corretamente-->

## Aviso/Warning ##
Os textos mostrados abaixo estão traduzidos tanto para Inglês quanto para Português[Clique aqui para ser redirecionado para versão em Inglês](Levar para parte em inglês)
The texts shown below are both translated to English and Portuguese [Click Here to be redirect to the English version](Levar para parte em inglês) <!--Translation ou Version?-->



## Sumário
- [Sobre o projeto](#sobre-o-projeto)
- [Arquitetura](#arquitetura)
- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Configuração do ambiente](#configuração-do-ambiente)
- [Execução da aplicação](#execução-da-aplicação)
- [Banco de dados com Docker Compose](#banco-de-dados-com-docker-compose)
- [Documentação da API (Swagger)](#documentação-da-api-swagger)
- [Estrutura de pastas](#estrutura-de-pastas)
- [Testes](#testes)
- [Membros do Projeto](#membros-do-projeto)
- [Instruções de execução](#instruções-de-execução)

## Sobre o projeto
O **VrumVrumHealth** é um sistema voltado ao transporte de pacientes para unidades de saúde. Ele permite:
- Cadastro e autenticação de pacientes, motoristas e administradores.
- Agendamento de consultas e transporte.
- Acompanhamento do status de agendamentos.
- Gestão de veículos e diários de bordo.

A aplicação expõe uma **API REST** protegida com **Spring Security + JWT** e pode ser consumida tanto por um front-end dedicado quanto pelas páginas HTML/JS presentes na pasta `pages/` do próprio projeto.

## Arquitetura
- **Backend:** Java 17 + Spring Boot 3 (REST API).
- **Segurança:** Spring Security com autenticação baseada em JWT (bibliotecas `java-jwt` e `jjwt`).
- **Banco de dados:** MySQL (dockerizado via `docker-compose.yaml`).
- **Documentação da API:** Springdoc OpenAPI (Swagger UI).

## Tecnologias utilizadas

### Linguagem e build
- Java 17
- Maven (gestão de dependências e build)

### Dependências principais (conforme `pom.xml`)
- `spring-boot-starter-web` – criação da API REST.
- `spring-boot-starter-data-jpa` – persistência de dados com JPA/Hibernate.
- `spring-boot-starter-security` – autenticação e autorização.
- `spring-boot-starter-test` – suporte a testes automatizados.
- `spring-security-test` – utilitários de teste para Security.
- `spring-boot-devtools` – reload automático em desenvolvimento.
- `lombok` – geração de getters/setters, construtores, etc.
- `springdoc-openapi-starter-webmvc-ui` – geração da documentação OpenAPI/Swagger.
- `java-jwt` e `jjwt-*` – criação e validação de tokens JWT.
- `mysql-connector-j` – driver JDBC para MySQL.
- `spring-boot-docker-compose` – integração do Spring Boot com Docker Compose.

## Configuração do ambiente

### Pré-requisitos
- **JDK 17** instalado.
- **Maven** instalado ou uso do wrapper (`mvnw` / `mvnw.cmd`).
- **Docker** e **Docker Compose** instalados (para subir o banco MySQL).
- **Node.js** instalado
- **GoogleStorageAPI** devidamente configurado no site https://cloud.google.com/?hl=pt-BR (Para armazenar e receber os anexos de pedido médico)

### Configuração de propriedades
As configurações da aplicação ficam em `src/main/resources/application.properties`.

Você deve garantir que os dados de conexão com o MySQL estejam coerentes com o `docker-compose.yaml`, por exemplo:
- URL, usuário, senha do banco.
- Dialeto e propriedades JPA/Hibernate.

## Execução da aplicação


### 1. Configurar a GoogleCloudAPI ###
Certifique-se de ter configurado devidamente a API no site https://cloud.google.com/?hl=pt-BR

Na raiz do projeto (`VrumVrumHealth`) insira a chave da sua API GoogleCloud com o nome `cred.json`

Insira o ID da sua API no arquivo `server.js` presente na raiz do projeto, procure a variável com nome `projectID` e insira o ID do seu projeto

No arquivo `submit.js` presente no diretório `src/pages/js` insira o link do seu Bucket do seu projeto do Google Cloud e o link do Storage do seu projeto

### 2. Subir o banco de dados com Docker Compose
Na raiz do projeto (`VrumVrumHealth`), existe um arquivo `docker-compose.yaml` que sobe um container **MySQL**.

Defina as variáveis de ambiente usadas no arquivo (`container_name`, `db_name`, `db_user`, `db_password`) ou crie um arquivo `.env` na raiz com algo como:

```env
container_name=
db_name= seu banco
db_user= seu usuario
db_password=sua senha
```

Em seguida, suba o serviço:
- `docker compose up -d`

O MySQL ficará disponível na porta `3306` do host.

### 3. Rodar a aplicação Spring Boot
Ainda na raiz do projeto:
- Em Linux/MacOS: `./mvnw spring-boot:run`
- Em Windows: `mvnw.cmd spring-boot:run`

Por padrão, a aplicação sobe na porta **8080** (ajustável em `application.properties`).

## Banco de dados com Docker Compose
O arquivo `docker-compose.yaml` define o serviço:
- **Imagem:** `mysql:latest`
- **Variáveis de ambiente:** `MYSQL_DATABASE`, `MYSQL_USER`, `MYSQL_PASSWORD` (vindas do `.env`).
- **Volume nomeado:** `db_data` em `/var/lib/mysql` (persistência de dados).
- **Porta exposta:** `3306:3306`.
- **Healthcheck:** comando `mysqladmin ping` para verificar se o banco está pronto.

## Documentação da API (Swagger)
Com a aplicação em execução, acesse em um navegador:
- `http://localhost:8080/swagger-ui/index.html`

A interface Swagger UI é fornecida pelo **springdoc-openapi-starter-webmvc-ui** e lista:
- Endpoints disponíveis (controllers).
- Modelos de requisição e resposta.
- Possibilidade de testar as chamadas, incluindo endpoints protegidos por JWT (via botão **Authorize**).

## Estrutura de pastas
Principais diretórios do backend (conforme `src/main/java/com/devnoahf/vrumvrumhealth`):

- `config/` – configurações de segurança (SecurityConfig), CORS, Swagger/OpenAPI, password encoder etc.
- `controller/` – controllers REST (por exemplo: Auth, Paciente, Motorista, Agendamento, Adm...).
- `dto/` – Data Transfer Objects usados como payload de entrada/saída da API.
- `enums/` – enums de domínio (status, frequências, tipos de atendimento etc.).
- `exception/` – exceções personalizadas e handlers globais (caso existentes).
- `mapper/` – conversão entre entidades e DTOs.
- `model/` – entidades JPA (Paciente, Motorista, Agendamento, Veículo, etc.).
- `repository/` – interfaces Spring Data JPA para acesso ao banco.
- `security/` – classes JWT, filtros, implementação de `UserDetailsService`, etc.
- `service/` – regras de negócio e orquestração entre controller e repository.

Outros diretórios relevantes:
- `src/main/resources/application.properties` – configuração da aplicação.
- `pages/` – páginas HTML/CSS/JS que consomem a API (login, cadastro, painel admin etc.).
- `test/java/...` – testes automatizados.

## Testes
Para executar os testes automatizados:
- Em Linux/MacOS: `./mvnw test`
- Em Windows: `mvnw.cmd test`

## Membros do Projeto
- Leonardo Perin - https://www.linkedin.com/in/leonardo-perin
- - Desenvolvedor Frontend
- Noah Franco    - https://www.linkedin.com/in/noahmf
- - Desenvolvedor Backend
- Joaquim Pedro  - https://www.linkedin.com/in/joaquim-pedro-augusto-de-castro-leite-oliveira-5420022a0
- - Desenvolvedor Frontend
- Gustavo Rosa   - https://www.linkedin.com/in/gustavo-jesus-rosa
- - Desenvolvedor Backend

## Instruções de execução

Esta seção explica como executar tanto o backend (API Spring Boot) quanto o frontend (páginas estáticas na pasta `pages/`).

Pré-requisitos
- JDK 17
- Maven (ou use o wrapper incluído `mvnw` / `mvnw.cmd`)
- Docker & Docker Compose (para MySQL)
- Node.js com as seguintes bibliotecas:
  - Multer
  - Express
  - Nodemon
  - Google-Cloud/Storage

Backend (API)
1. Configurar ambiente
- Crie um arquivo `.env` na raiz do projeto (opcional) com os valores usados pelo `docker-compose.yaml`:
  MYSQL_DATABASE=vrumdb
  MYSQL_USER=vrumuser
  MYSQL_PASSWORD=vrumpassword
  MYSQL_ROOT_PASSWORD=rootpassword

- Verifique se `src/main/resources/application.properties` corresponde às configurações do banco de dados (URL, usuário, senha).

2. Iniciar MySQL com Docker Compose (a partir da raiz do projeto):
- docker compose up -d

3. Executar a aplicação Spring Boot
- Linux / macOS: ./mvnw spring-boot:run
- Windows (PowerShell / CMD): mvnw.cmd spring-boot:run

Alternativamente, construa e execute o jar:
- ./mvnw package
- java -jar target/*.jar

4. Documentação da API (Swagger)
- Após a inicialização, abra: http://localhost:8080/swagger-ui/index.html

Frontend (páginas estáticas)
Os arquivos do frontend estão na pasta `pages/`. Eles são HTML/CSS/JS puro e podem ser abertos diretamente ou servidos por um servidor estático.

Node.js:
- Na raiz do projeto, execute(com instalação da Biblioteca `nodemon`):
  npm run dev
- Em seguida, abra: http://localhost:8180/index.html

Configurações de CORS e URL da API
- O frontend espera que a API esteja acessível no host/porta do backend (padrão: http://localhost:8080).
- Se você servir o frontend em uma porta diferente (por exemplo, 5500), garanta que o backend permita CORS a partir desse origem. Verifique `src/main/java/.../config/CorsConfig` ou `application.properties` para origens permitidas e atualize para incluir http://localhost:5500, se necessário.

Solução de problemas comuns
- Banco de dados não pronto: verifique os logs do docker para o container MySQL e assegure-se de que as migrações de esquema em `src/main/resources/db/migrations` foram executadas.
- Conflitos de porta: altere a porta do backend em `application.properties` (server.port) ou a porta do servidor frontend.
- Dependências faltando: execute `./mvnw clean package` para forçar o download das dependências.
- Erro 401 ao fazer requisições: certifique-se que as páginas estão sendo executas pelo nodemon, pois, as mesmas, automaticamente geram o token de autenticação e certifique-se que os dados inseridos estão corretos(Exemplo:CPF deve ter 11 digitos, agendamento não pode ser no passado, número de telefone deve ter 9 dígitos, email e CPF não podem ser repetidos ao criar uma conta, etc.).







