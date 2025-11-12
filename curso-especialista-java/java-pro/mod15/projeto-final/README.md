# Memelandia - Plataforma de Gerenciamento de Memes

Memelandia é uma aplicação de microsserviços desenvolvida em Java/Spring Boot para gerenciamento de memes, usuários e categorias. O projeto implementa uma arquitetura distribuída com API Gateway, circuit breakers, e comunicação entre serviços via OpenFeign.

### Microsserviços

#### 1. API Gateway (Porta 9000)
- Ponto de entrada único para todos os serviços
- Roteamento de requisições para os microsserviços
- Circuit Breaker para resiliência
- Fallback handlers para degradação graceful

**Rotas:**
- `/api/usuarios/**` → Usuario Service
- `/api/categorias/**` → Categoria Service
- `/api/memes/**` → Memes Service

#### 2. Usuario Service (Porta 9001)
- Gerenciamento de usuários
- Banco de dados H2 em memória
- Endpoints de CRUD para usuários
- Actuator para health checks e métricas

#### 3. Categoria Service (Porta 9002)
- Gerenciamento de categorias de memes
- Integração com Usuario Service via OpenFeign
- Validação de categorias
- Banco de dados H2 em memória

#### 4. Memes Service (Porta 9003)
- Gerenciamento de memes
- Integração com Usuario Service e Categoria Service
- Validação de usuários e categorias antes de criar memes
- Banco de dados H2 em memória

## Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.7** - Framework principal
- **Spring Cloud 2025.0.0** - Gateway e OpenFeign
- **H2 Database** - Banco de dados em memória
- **Gradle** - Gerenciamento de dependências e build
- **Docker** - Containerização
- **Docker Compose** - Orquestração de containers

### Dependências Principais

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Actuator
- Spring Cloud Gateway (MVC)
- Spring Cloud OpenFeign
- Micrometer (métricas)
- H2 Database

## Requisitos

- **Java 21** ou superior
- **Docker** e **Docker Compose**
- **Gradle** (incluído via wrapper)

## Como Executar

### Opção 1: Com Docker Compose (Recomendado)

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
```

```bash
cd projeto-final
```

2. Execute com Docker Compose:
```bash
docker compose up --build
```

3. Aguarde todos os serviços iniciarem. Os healthchecks garantem a ordem correta de inicialização.

### Opção 2: Executar Individualmente

#### Pré-requisitos
Certifique-se de ter o Java 21 instalado:
```bash
java -version
```

#### 1. Usuario Service
```bash
cd usuario-service
```

```bash
./gradlew bootRun
```

#### 2. Categoria Service
```bash
cd categoria-service
```

```bash
./gradlew bootRun
```

#### 3. Memes Service
```bash
cd memes-service
```

```bash
./gradlew bootRun
```

#### 4. API Gateway
```bash
cd api-gateway
```

```bash
./gradlew bootRun
```

## Endpoints

### Através do API Gateway (Porta 9000)

#### Usuários
```
POST   /api/usuarios              - Cria novo usuário
GET    /api/usuarios/{id}         - Busca usuário por ID
GET    /api/usuarios/email/{email} - Busca usuário por email
```

**Exemplo de payload para criar usuário:**
```json
{
  "nome": "João Humberto",
  "email": "joao@example.com"
}
```

#### Categorias
```
POST   /api/categorias                    - Cria nova categoria
GET    /api/categorias                    - Lista todas as categorias
GET    /api/categorias/{id}               - Busca categoria por ID
GET    /api/categorias/usuario/{usuarioId} - Busca categorias de um usuário
```

**Exemplo de payload para criar categoria:**
```json
{
  "nome": "Memes Engraçados",
  "descricao": "Categoria de memes engraçados",
  "usuarioId": 1
}
```

#### Memes
```
POST   /api/memes                       - Cria novo meme
GET    /api/memes                       - Lista todos os memes
GET    /api/memes/{id}                  - Busca meme por ID
GET    /api/memes/usuario/{usuarioId}   - Busca memes de um usuário
GET    /api/memes/categoria/{categoriaId} - Busca memes de uma categoria
```

**Exemplo de payload para criar meme:**
```json
{
  "nome": "Meme Divertido",
  "descricao": "Um meme muito engraçado",
  "dataUrl": "https://example.com/meme.jpg",
  "usuarioId": 1,
  "categoriaId": 1
}
```

### Endpoints de Monitoramento

Cada serviço expõe endpoints do Actuator:

```
GET /actuator/health           - Status de saúde
GET /actuator/info             - Informações do serviço
GET /actuator/metrics          - Métricas da aplicação
```

API Gateway também expõe:
```
GET /actuator/gateway/routes   - Rotas configuradas
```

### Consoles H2

Cada serviço possui seu próprio console H2:

- Usuario Service: http://localhost:9001/h2-console
- Categoria Service: http://localhost:9002/h2-console
- Memes Service: http://localhost:9003/h2-console

**Credenciais:**
- JDBC URL: `jdbc:h2:mem:<service>db` (ex: `jdbc:h2:mem:usuariodb`)
- Username: `sa`
- Password: (vazio)

## Estrutura do Projeto

```
projeto-final/
├── api-gateway/              # API Gateway
│   ├── src/
│   ├── Dockerfile
│   └── build.gradle
├── usuario-service/          # Serviço de Usuários
│   ├── src/
│   ├── Dockerfile
│   └── build.gradle
├── categoria-service/        # Serviço de Categorias
│   ├── src/
│   ├── Dockerfile
│   └── build.gradle
├── memes-service/           # Serviço de Memes
│   ├── src/
│   ├── Dockerfile
│   └── build.gradle
├── docker-compose.yaml      # Orquestração Docker
└── README.md               # Este arquivo
```

## Arquitetura de Cada Serviço

Todos os serviços seguem a Clean Architecture com as seguintes camadas:

```
src/
├── domain/                  # Camada de domínio
│   ├── entity/             # Entidades de negócio
│   ├── repository/         # Interfaces de repositório
│   └── exception/          # Exceções de negócio
├── application/            # Camada de aplicação
│   ├── usecase/           # Casos de uso
│   └── dto/               # DTOs
└── infrastructure/         # Camada de infraestrutura
    ├── controller/        # Controllers REST
    ├── persistence/       # Implementação JPA
    ├── client/           # Clients Feign (quando aplicável)
    └── config/           # Configurações
```

## Resiliência e Monitoramento

### Circuit Breaker
O API Gateway implementa Circuit Breaker para cada rota:
- Fallback handlers para quando os serviços estão indisponíveis
- Degradação graceful da aplicação

### Health Checks
Todos os serviços possuem health checks configurados:
- Intervalo: 30s
- Timeout: 10s
- Retries: 5
- Start period: 40s

### Métricas
Micrometer está configurado em todos os serviços para coleta de métricas:
- Latência de requisições
- Taxa de erro
- Uso de recursos

## Build e Testes

### Build individual
```bash
cd <service-directory>
```

```bash
./gradlew build
```

### Executar testes
```bash
./gradlew test
```

### Gerar JAR
```bash
./gradlew bootJar
```

O JAR será gerado em `build/libs/`

## Troubleshooting

### Serviços não iniciam
1. Verifique se as portas 9000-9003 estão disponíveis
2. Verifique os logs com `docker-compose logs <service-name>`
3. Aguarde os health checks completarem

### Erro de comunicação entre serviços
1. Verifique se todos os serviços estão healthy: `docker-compose ps`
2. Verifique os logs do API Gateway
3. Confirme as URLs de integração nos arquivos `application.yaml`

### Banco de dados
Os bancos H2 são em memória e são perdidos ao reiniciar os serviços. Para dados persistentes, considere usar PostgreSQL ou MySQL.

## Autor

Desenvolvido como projeto final do módulo 15 - Curso Especialista Java EBAC por Marlinsk

## Licença

Este projeto é de uso educacional.
