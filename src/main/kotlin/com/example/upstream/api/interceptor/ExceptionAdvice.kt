package com.example.upstream.api.interceptor

import com.example.upstream.exception.EntityNotFoundException
import com.example.upstream.exception.ForbiddenException
import com.example.upstream.exception.InvalidInputException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.validation.FieldError
import java.util.Dictionary
import kotlin.Exception

@RestControllerAdvice
class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors = HashMap<String, String>()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage!!
        }
        return ResponseEntity.badRequest().body(errors)
    }

    @ExceptionHandler(InvalidInputException::class)
    fun handleInvalidInputException(ex: InvalidInputException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.err)
    }

    @ExceptionHandler(ForbiddenException::class)
    fun forbidden(ex: ForbiddenException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.message)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleInvalidInputException(ex: EntityNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.err)
    }

    @ExceptionHandler(Exception::class)
    fun handleValidationExceptions(ex: Exception): ResponseEntity<Map<String, String>> {
        val errors = HashMap<String, String>()
        errors["Error"] = ex.message.toString()
        return ResponseEntity.internalServerError().body(errors)
    }
}
