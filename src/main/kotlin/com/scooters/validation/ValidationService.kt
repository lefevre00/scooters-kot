package com.scooters.validation

import com.scooters.model.InputDTO
import org.springframework.stereotype.Service

@Service
class ValidationService {

    @Throws(ValidationException::class)
    fun validate(inputDTO: InputDTO): List<ValidationError> {
        val errors = mutableListOf<ValidationError>()

        errors.addAll(checkDistrict(inputDTO))
        errors.addAll(checkManager(inputDTO))
        errors.addAll(checkEngineer(inputDTO))

        return errors
    }

    private fun checkCapacity(prefix: String, value: Int?, min: Int, max: Int): Collection<ValidationError> {
        return mutableListOf<ValidationError>().apply {
            if (value == null) {
                add(ValidationError("$prefix capacity undefined"))
            } else {
                inBounds(value, min, max, "$prefix capacity")?.let {
                    error -> add(error)
                }
            }
        }
    }

    private fun checkEngineer(inputDTO: InputDTO): Collection<ValidationError> = checkCapacity("Engineer", inputDTO.engineerCapacity, 1, 1000)

    private fun checkManager(inputDTO: InputDTO): Collection<ValidationError> = checkCapacity("Manager", inputDTO.managerCapacity, 1, 999)

    private fun checkDistrict(inputDTO: InputDTO): Collection<ValidationError> =
            mutableListOf<ValidationError>().apply {
                if (inputDTO.scooters == null) {
                    add(ValidationError("The scooters per district is missing"))
                } else {
                    inBounds(inputDTO.scooters!!.size, 1, 100, "District size")?.let { error ->
                        add(error)
                    }
                    inputDTO.scooters!!.forEachIndexed { index, value ->
                        if (value == null) {
                            add(ValidationError("District $index has no scooters quantity defined"))
                        } else {
                            inBounds(value, 0, 1000, "Quantity of scooter for district $index")?.let { error ->
                                add(error)
                            }
                        }
                    }
                }
            }

    private fun inBounds(value: Int, min: Int, max: Int, prefix: String): ValidationError? =
            if (value !in min..max) {
                ValidationError("$prefix out of bounds [$min, $max] : $value")
            } else {
                null
            }

}
