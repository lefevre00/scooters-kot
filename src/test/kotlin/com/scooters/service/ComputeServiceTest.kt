package com.scooters.service

import com.scooters.model.InputDTO
import org.junit.Assert
import org.junit.Test

class ComputeServiceTest {

    val service:ComputeService = ComputeService()

    @Test
    fun should_solve_example_1() {
        val input = InputDTO(listOf(15, 10), 12, 5)
        Assert.assertEquals(3, service.compute(input))
    }

    @Test
    fun should_solve_example_2() {
        val input = InputDTO(listOf(11, 15, 13), 9, 5)
        Assert.assertEquals(7, service.compute(input))
    }

}