package com.scooters.validation

class ValidationException(private var errors: List<ValidationError> = listOf()) : RuntimeException() {
    fun toDto(): List<ValidationError> = errors
}