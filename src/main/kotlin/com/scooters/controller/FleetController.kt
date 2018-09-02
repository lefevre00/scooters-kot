package com.scooters.controller

import com.scooters.model.InputDTO
import com.scooters.model.OutputDTO
import com.scooters.service.ComputeService
import com.scooters.validation.ValidationException
import com.scooters.validation.ValidationService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@RestController("compute")
class FleetController(
        val validationService: ValidationService,
        val computeService: ComputeService
) {

    @PostMapping
    fun post(@RequestBody input:InputDTO) : ResponseEntity<Any> {
        val errors = validationService.validate(input)
        if (errors.isNotEmpty()) {
            throw ValidationException(errors)
        }

        return ResponseEntity.ok(OutputDTO(computeService.compute(input)))
    }

}

@ControllerAdvice
@RestController
class ValidationExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [(ValidationException::class)])
    fun handleValidationException(exception: ValidationException, request: ServletWebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(exception, exception.toDto(), HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

}