Spring Boot Backend Learning Notes (Anuj)

Project Overview

Building a Spring Boot + PostgreSQL backend from scratch.

Current stack:

* Kotlin
* Spring Boot
* PostgreSQL
* Spring Data JPA
* Spring Security
* JWT Authentication
* BCrypt Password Hashing

⸻

Stage 1 - Basic CRUD

Architecture:

Controller
↓
Service
↓
Repository
↓
Database

Responsibilities:

Controller

Handles:

* Request Mapping
* Request Validation
* Response Mapping

Example:

@PostMapping("/register")
fun register(
@Valid
@RequestBody request: RegisterRequest
)

⸻

Service

Contains business logic.

Example:

if (userRepository.existsByEmail(request.email)) {
throw EmailAlreadyExistsException(...)
}

⸻

Repository

Responsible for database access.

Example:

interface UserRepository : JpaRepository<UserEntity, Long>

Custom methods:

findByEmail()
existsByEmail()

⸻

Entity

Represents database table.

@Entity
@Table(name = "users")
class UserEntity(...)

Entity != DTO

⸻

Stage 2 - Validation

Request DTO:

data class RegisterRequest(
@field:Email
@field:NotBlank
val email: String,
@field:NotBlank
val password: String
)

Controller:

@Valid
@RequestBody

Important:

Validation happens BEFORE controller execution.

Flow:

Request
↓
Validation
↓
Controller

Invalid request never reaches service.

⸻

Stage 3 - Global Exception Handling

Custom Exceptions:

UserNotFoundException
EmailAlreadyExistsException
EmailNotFoundException
InvalidCredentialsException

Global Handler:

@RestControllerAdvice
class GlobalExceptionHandler

Purpose:

Convert exceptions into API responses.

Example:

{
"message": "User not found"
}

instead of ugly stack traces.

⸻

Stage 4 - Response DTOs

Do NOT return Entity directly.

Good:

data class UserResponse(
val id: Long,
val email: String
)

Purpose:

Hide internal fields.

Never expose:

password
internal ids
sensitive data

⸻

Stage 5 - Password Hashing (BCrypt)

Never store:

abc123

Store:

$2a$10$....

Configuration:

@Bean
fun passwordEncoder(): PasswordEncoder {
return BCryptPasswordEncoder()
}

⸻

Hashing

Password:

abc123

↓

Hash:

$2a$10$....

One-way operation.

Cannot recover original password.

⸻

Salt

Random value added before hashing.

Purpose:

Same password produces different hashes.

Example:

User A:

abc123
↓
Hash A

User B:

abc123
↓
Hash B

Hashes are different.

⸻

Login

Never compare:

savedPassword == requestPassword

Use:

passwordEncoder.matches(
request.password,
savedUser.password
)

⸻

Stage 6 - JWT Fundamentals

Purpose:

Avoid sending email/password on every request.

Login:

Email + Password
↓
JWT
↓
Store Token

Future requests:

Authorization: Bearer <jwt>

⸻

JWT Structure

Header.Payload.Signature

Example:

{
"alg": "HS256"
}

Payload:

{
"sub":"20",
"role":"USER"
}

⸻

JWT Claims

sub

Who is the token about?

Usually:

User Id

role

Authorization information.

Example:

USER
ADMIN

exp

Expiration timestamp.

iat

Issued at timestamp.

⸻

JWT Is Not Encrypted

Anyone can read:

{
"sub":"20",
"role":"USER"
}

Security comes from Signature.

⸻

Stage 7 - JwtService

Responsibilities:

generateToken()
extractUserId()
isTokenValid()

⸻

generateToken()

Creates:

{
"sub":"20",
"role":"USER",
"iat":...,
"exp":...
}

Signs using secret key.

⸻

extractUserId()

Reads:

{
"sub":"20"
}

Returns:

20

⸻

isTokenValid()

Verifies:

* Signature
* Expiration
* Format

Returns:

true

or

false

⸻

Stage 8 - Role Modeling

Role is Enum.

enum class Role {
USER,
ADMIN
}

Entity:

@Enumerated(EnumType.STRING)
val role: Role

Database stores:

USER
ADMIN

not:

0
1

⸻

Stage 9 - Spring Security Concepts

Spring Security does NOT know UserEntity.

It understands:

UserDetails

⸻

Why UserDetails?

Spring asks:

What is:

* Username?
* Password?
* Authorities?

It needs a common contract.

⸻

CustomUserDetails

Adapter Pattern.

Converts:

UserEntity

↓

UserDetails

Mapping:

username → email

password → password

authority → ROLE_USER

⸻

CustomUserDetailsService

Spring asks:

How do I load a user?

You answer:

class CustomUserDetailsService

Flow:

email
↓
UserRepository
↓
UserEntity
↓
CustomUserDetails

⸻

Mental Model

Database Model:

UserEntity

Security Model:

CustomUserDetails

These are separate concerns.

⸻

Next Stage

JwtAuthenticationFilter

Responsibilities:

1. Read Authorization Header
2. Extract Bearer Token
3. Validate JWT
4. Extract User Id
5. Load User
6. Create Authentication
7. Store In SecurityContext

Flow:

Request
↓
JwtAuthenticationFilter
↓
Controller
↓
Service
↓
Repository

After this stage:

Protected APIs require JWT.

Missing JWT:

401 Unauthorized

⸻

Key Backend Principles Learned

1. Validation before Controller.
2. Exceptions handled globally.
3. Passwords are hashed, never encrypted.
4. JWT is signed, not encrypted.
5. DTOs protect internal models.
6. Services contain business logic.
7. Repositories access database.
8. Security models and database models are different.
9. UserDetails is Spring Security’s user abstraction.
10. JWT Authentication is stateless because server stores no session.

Current Progress:

CRUD ✅
Validation ✅
Exception Handling ✅
BCrypt ✅
Login ✅
JWT Generation ✅
JWT Validation ✅
Role Enum ✅
CustomUserDetails ✅
CustomUserDetailsService ✅

Next:

JwtAuthenticationFilter 🚀
SecurityContext 🚀
Protected APIs 🚀