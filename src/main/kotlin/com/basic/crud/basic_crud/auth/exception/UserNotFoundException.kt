package com.basic.crud.basic_crud.auth.exception;

class UserNotFoundException(
    message: String
) : RuntimeException(message)

class EmailAlreadyExistsException(
    message: String
) : RuntimeException(message)

class EmailNotFoundException(
    message: String
) : RuntimeException(message)

class InvalidCredentialsException(
    message: String
) : RuntimeException(message)