package com.scooters.service

import com.scooters.model.InputDTO
import com.scooters.model.OutputDTO
import org.springframework.stereotype.Service

@Service
class ComputeService {
    fun compute(inputDTO: InputDTO): Int {
        var fleet = 0
        inputDTO.scooters?.forEach {
            var sold = it!! - inputDTO.managerCapacity!!
            fleet++
            while (sold > 0) {
                sold -= inputDTO.engineerCapacity!!
                fleet++
            }
        }
        return fleet
    }
}