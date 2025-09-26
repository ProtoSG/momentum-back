# 🚀 Momentum - Backend API

**Momentum Backend** es la API REST que potencia la aplicación gamificada de productividad Momentum. Construida con Spring Boot, proporciona autenticación JWT, gestión de tareas, sistema de mascotas virtuales y gamificación completa.

## ✨ Características Principales

### 🔐 Autenticación y Seguridad
- **JWT Authentication** con access y refresh tokens
- **Spring Security** con configuración robusta
- **Cookies HTTP-Only** para refresh tokens seguros
- **CORS** configurado para frontend
- **Validación** de entrada con Bean Validation

### 📋 Sistema de Tareas
- **CRUD completo** de tareas por usuario
- **Prioridades** configurable (LOW, MEDIUM, HIGH)
- **Estados** de tarea (TODO, DONE, ARCHIVED)
- **Sistema de puntos** automático según prioridad
- **Timestamps** automáticos de creación y actualización

### 🐉 Sistema de Mascotas Virtuales
- **Mascota única** por usuario (dragón virtual)
- **Estadísticas**: Salud, Energía, Hambre
- **Sistema de niveles** y experiencia
- **Acciones de cuidado**: Alimentar, Curar, Energizar
- **Costo en puntos** para cada acción

### 🎮 Gamificación
- **Sistema de puntos** gastables para cuidar mascota
- **Experiencia permanente** para subir niveles
- **Niveles de mascota** con nombres personalizados
- **Ledger de puntos** para tracking de transacciones

## 🛠️ Tecnologías Utilizadas

### Framework y Core
- **Spring Boot 3.5.5** - Framework principal
- **Java 21** - Lenguaje de programación
- **Maven** - Gestión de dependencias y build

### Base de Datos
- **Spring Data JPA** - ORM y repositorios
- **Hibernate** - Implementación JPA
- **PostgreSQL** - Base de datos relacional
- **Flyway** - Migraciones de BD (implícito con JPA)

### Seguridad
- **Spring Security** - Framework de seguridad
- **JWT (jjwt 0.12.6)** - JSON Web Tokens
- **BCrypt** - Hashing de contraseñas

### Utilidades
- **Lombok** - Reducción de boilerplate
- **Bean Validation** - Validación de datos
- **Jackson** - Serialización JSON

## 🏗️ Arquitectura del Proyecto

```
src/main/java/com/momentum/momentum_back/
├── controller/           # Controladores REST
│   ├── AuthController.java       # Autenticación y registro
│   ├── TaskController.java       # Gestión de tareas
│   ├── PetController.java        # Gestión de mascotas
│   ├── PetLevelController.java   # Niveles de mascota
│   └── HealthController.java     # Health check
├── dto/                  # Data Transfer Objects
│   ├── AuthRequestDTO.java       # Login request
│   ├── AuthResponseDTO.java      # Login response
│   ├── UserCreateDTO.java        # Registro usuario
│   ├── TaskDTO.java              # Transferencia tarea
│   ├── PetDTO.java               # Transferencia mascota
│   └── PointsLedgerDTO.java      # Transferencia puntos
├── entity/               # Entidades JPA
│   ├── User.java                 # Usuario del sistema
│   ├── Task.java                 # Tarea/misión
│   ├── Pet.java                  # Mascota virtual
│   ├── PetLevel.java             # Nivel de mascota
│   ├── PointsLedger.java         # Registro de puntos
│   ├── TaskCompletion.java       # Completado de tarea
│   ├── TaskStatus.java           # Enum estados tarea
│   └── TaskPriority.java         # Enum prioridades
├── repository/           # Repositorios JPA
│   ├── UserRepository.java
│   ├── TaskRepository.java
│   ├── PetRepository.java
│   ├── PetLevelRepository.java
│   └── PointsLedgerRepository.java
├── service/              # Lógica de negocio
│   ├── impl/                     # Implementaciones
│   │   ├── AuthServiceImpl.java
│   │   ├── JwtServiceImpl.java
│   │   ├── TaskServiceImpl.java
│   │   ├── PetServiceImpl.java
│   │   └── UserServiceImpl.java
│   ├── AuthService.java          # Interface auth
│   ├── JwtService.java           # Interface JWT
│   ├── TaskService.java          # Interface tareas
│   ├── PetService.java           # Interface mascotas
│   ├── UserService.java          # Interface usuarios
│   └── PetStatsScheduler.java    # Scheduler stats
├── mapper/               # Mapeo Entity ↔ DTO
│   ├── UserMapper.java
│   ├── TaskMapper.java
│   ├── PetMapper.java
│   └── PointsLedgerMapper.java
└── config/               # Configuraciones
    ├── SecurityConfig.java       # Configuración seguridad
    ├── JwtAuthFilter.java        # Filtro JWT
    └── CorsConfig.java           # Configuración CORS
```

## 📊 Modelo de Datos

### Entidades Principales

#### User
```java
- userId (PK)
- name
- email (unique)
- password (hashed)
- createdAt, updatedAt
```

#### Task  
```java
- taskId (PK)
- userId (FK)
- description
- status (TODO/DONE/ARCHIVED)
- priority (LOW/MEDIUM/HIGH)
- pointsValue (auto-calculated)
- dueDate (optional)
- createdAt, updatedAt
```

#### Pet
```java
- petId (PK)
- userId (FK)
- name
- level (FK to PetLevel)
- pointsTotal
- experience
- health (0-100)
- energy (0-100)
- hunger (0-100)
- url (sprite image)
- createdAt, updatedAt
```

#### PetLevel
```java
- level (PK)
- name (ej: "Hatchling", "Young Dragon")
- experienceRequired
- description
```

#### PointsLedger
```java
- id (PK)
- userId (FK)
- amount (+/-)
- reason
- timestamp
```

### Relaciones
- **User 1:N Task** - Un usuario tiene muchas tareas
- **User 1:1 Pet** - Un usuario tiene una mascota
- **Pet N:1 PetLevel** - Muchas mascotas pueden tener el mismo nivel
- **User 1:N PointsLedger** - Un usuario tiene muchos registros de puntos

## 🔌 API Endpoints

### Autenticación (`/api/auth`)

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| `POST` | `/login` | Iniciar sesión | `{email, password}` |
| `POST` | `/register` | Registrar usuario | `{name, email, password}` |
| `POST` | `/refresh` | Renovar access token | Cookie: refresh_token |

**Respuesta de Auth:**
```json
{
  "accessToken": "eyJ...",
  "refreshToken": "eyJ...",
  "userEmail": "user@example.com",
  "userName": "John Doe"
}
```

### Tareas (`/api/task`)

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| `GET` | `/` | Obtener tareas del usuario | - |
| `POST` | `/` | Crear nueva tarea | `{description, priority, dueDate?}` |
| `PUT` | `/{id}` | Actualizar tarea completa | `{description, priority, dueDate?}` |
| `PUT` | `/state/{id}` | Cambiar estado de tarea | `{status}` |

**Ejemplo Task Response:**
```json
{
  "taskId": 1,
  "description": "Completar proyecto",
  "status": "TODO",
  "priority": "HIGH",
  "pointsValue": 20,
  "dueDate": "2024-12-31",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### Mascotas (`/api/pet`)

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| `GET` | `/` | Obtener mascota del usuario | - |
| `POST` | `/` | Crear mascota inicial | `{name}` |
| `POST` | `/points` | Añadir puntos a mascota | `{amount, reason}` |
| `POST` | `/experience` | Añadir experiencia | `15` (Integer) |
| `POST` | `/feed` | Alimentar mascota (-10 pts) | - |
| `POST` | `/heal` | Curar mascota (-20 pts) | - |
| `POST` | `/boost-energy` | Energizar mascota (-15 pts) | - |

**Ejemplo Pet Response:**
```json
{
  "petId": 1,
  "name": "Dragonite",
  "level": 3,
  "pointsTotal": 150,
  "experience": 45,
  "health": 85,
  "energy": 70,
  "hunger": 30,
  "url": "https://ibb.co/60tcMVfs"
}
```

### Niveles de Mascota (`/api/pet-levels`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/` | Obtener todos los niveles |

### Health Check (`/api/health`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/` | Verificar estado del servidor |

## 🚀 Instalación y Configuración

### Prerrequisitos
- **Java 21** o superior
- **Maven 3.8+**
- **PostgreSQL 13+**
- IDE compatible (IntelliJ IDEA, Eclipse, VS Code)

### Variables de Entorno Requeridas

```bash
# Base de datos
DATABASE_URL=jdbc:postgresql://localhost:5432/momentum_db

# JWT Security
JWT_SECRET_KEY=your-super-secret-jwt-key-here-at-least-256-bits

# Frontend URL para CORS
FRONTEND_URL=http://localhost:5173
```

### Configuración de Base de Datos

1. **Crear base de datos:**
   ```sql
   CREATE DATABASE momentum_db;
   CREATE USER momentum_user WITH PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE momentum_db TO momentum_user;
   ```

2. **Variables de entorno DATABASE_URL:**
   ```bash
   DATABASE_URL=jdbc:postgresql://localhost:5432/momentum_db?user=momentum_user&password=your_password
   ```

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/ProtoSG/momentum-back.git
   cd momentum-back
   ```

2. **Configurar variables de entorno**
   ```bash
   # Crear archivo .env o configurar en tu sistema
   export DATABASE_URL="jdbc:postgresql://localhost:5432/momentum_db?user=momentum_user&password=your_password"
   export JWT_SECRET_KEY="your-super-secret-jwt-key-minimum-256-bits-long"
   export FRONTEND_URL="http://localhost:5173"
   ```

3. **Instalar dependencias y compilar**
   ```bash
   ./mvnw clean install
   ```

4. **Ejecutar aplicación**
   ```bash
   # Modo desarrollo
   ./mvnw spring-boot:run
   
   # O ejecutar JAR
   java -jar target/momentum-back-0.0.1-SNAPSHOT.jar
   ```

5. **Verificar funcionamiento**
   ```bash
   curl http://localhost:8080/api/health
   # Respuesta esperada: {"status": "UP"}
   ```

### Configuración Docker (Opcional)

```dockerfile
FROM openjdk:21-jdk-slim

WORKDIR /app
COPY target/momentum-back-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
```

**Docker Compose:**
```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: momentum_db
      POSTGRES_USER: momentum_user
      POSTGRES_PASSWORD: your_password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  momentum-back:
    build: .
    ports:
      - "8080:8080"
    environment:
      DATABASE_URL: jdbc:postgresql://postgres:5432/momentum_db?user=momentum_user&password=your_password
      JWT_SECRET_KEY: your-super-secret-jwt-key-minimum-256-bits-long
      FRONTEND_URL: http://localhost:5173
    depends_on:
      - postgres

volumes:
  postgres_data:
```

## 🔧 Configuración Avanzada

### JWT Configuration
```properties
# application.properties
application.security.jwt.secret-key=${JWT_SECRET_KEY}
application.security.jwt.expiration=86400000          # 24 horas
application.security.jwt.refresh-token.expiration=604800000  # 7 días
```

### CORS Configuration
```java
@CrossOrigin(origins = "${frontend.url}")
// O configuración global en CorsConfig.java
```

### JPA/Hibernate
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
```

## 🧪 Testing

### Ejecutar Tests
```bash
# Todos los tests
./mvnw test

# Tests específicos
./mvnw test -Dtest=TaskServiceTest

# Tests de integración
./mvnw test -Dtest=TaskControllerIntegrationTest
```

### Test de API con cURL

**Registro:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@example.com","password":"password123"}'
```

**Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'
```

**Crear Tarea:**
```bash
curl -X POST http://localhost:8080/api/task \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{"description":"Test task","priority":"HIGH"}'
```

## 📊 Monitoreo y Logs

### Health Check
- **Endpoint**: `GET /api/health`
- **Uso**: Verificar estado de la aplicación

### Logs de Aplicación
```bash
# Ver logs en tiempo real
tail -f logs/momentum-back.log

# Filtrar logs por nivel
grep "ERROR" logs/momentum-back.log
```

### Métricas (Configurar Actuator)
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

## 🚀 Deployment

### Build para Producción
```bash
./mvnw clean package -DskipTests
```

### Variables de Entorno Producción
```bash
export DATABASE_URL="jdbc:postgresql://prod-db-host:5432/momentum_prod"
export JWT_SECRET_KEY="production-secret-key-very-secure"
export FRONTEND_URL="https://momentum-app.vercel.app"
```

### Deployment en Cloud

#### Heroku
```bash
# Configurar Heroku
heroku create momentum-back-api
heroku addons:create heroku-postgresql:hobby-dev
heroku config:set JWT_SECRET_KEY=your-production-secret
heroku config:set FRONTEND_URL=https://your-frontend-domain.com

# Deploy
git push heroku main
```

#### Railway/Render
1. Conectar repositorio GitHub
2. Configurar variables de entorno
3. Configurar PostgreSQL addon
4. Deploy automático

## 🔒 Seguridad

### Mejores Prácticas Implementadas
- **JWT con expiración** corta para access tokens
- **Refresh tokens** en HTTP-Only cookies
- **Contraseñas hasheadas** con BCrypt
- **Validación de entrada** en todos los endpoints
- **CORS** configurado específicamente
- **SQL Injection** prevenido con JPA/JPQL

### Consideraciones de Seguridad
- Usar HTTPS en producción
- Rotar JWT_SECRET_KEY regularmente
- Configurar rate limiting
- Implementar logging de seguridad
- Actualizar dependencias regularmente

## 🤝 Contribución

1. Fork el proyecto
2. Crear feature branch (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -m 'Añadir nueva funcionalidad'`)
4. Push branch (`git push origin feature/nueva-funcionalidad`)
5. Abrir Pull Request

### Estándares de Código
- Usar **Java Conventions**
- **Lombok** para reducir boilerplate
- **JavaDoc** para métodos públicos
- **Tests unitarios** para nueva funcionalidad
- **Validación** en DTOs con Bean Validation

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver `LICENSE` para más detalles.

## 🔗 Enlaces Relacionados

- **Frontend Repository**: [momentum-front](https://github.com/ProtoSG/momentum-front)
- **API Documentation**: _En desarrollo_
- **Live Demo**: _Próximamente_

---

**Desarrollado con ❤️ usando Spring Boot**
