package com.scooters.validation

import com.scooters.model.InputDTO
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ValidationServiceTest {

    val service:ValidationService = ValidationService()

    private lateinit var input: InputDTO

    @Before
    fun doBefore() {
        input = InputDTO(listOf(1, 2, 3), 5, 6)
    }

    @Test
    fun should_succeed_with_default_values() {
        val errors = service.validate(input)
        Assert.assertThat(errors, `is`(not(nullValue())))
        Assert.assertThat(errors, hasSize(0))
    }

    @Test
    fun should_fail_if_no_districts() {
        input.scooters = null
        assertMessage("The scooters per district is missing")
    }

    private fun assertMessage(msg: String) {
        val errors = service.validate(input)
        Assert.assertThat(errors, `is`(not(nullValue())))
        Assert.assertThat(errors, hasSize(1))
        Assert.assertThat(errors, contains(ValidationError(msg)))
    }

    @Test
    fun should_fail_with_empty_districts() {
        input.scooters = listOf()
        assertMessage("District size out of bounds [1, 100] : 0")
    }

    @Test
    fun should_fail_if_a_district_has_null_scooters() {
        input.scooters = listOf(null, 1)
        assertMessage("District 0 has no scooters quantity defined")
    }

    @Test
    fun should_fail_if_a_district_has_too_many_scooters() {
        input.scooters = listOf(1001)
        assertMessage("Quantity of scooter for district 0 out of bounds [0, 1000] : 1001")
    }

    @Test
    fun should_fail_if_no_manager_capacity_defined() {
        input.managerCapacity = null
        assertMessage("Manager capacity undefined")
    }

    @Test
    fun should_fail_if_manager_capacity_too_small() {
        input.managerCapacity = 0
        assertMessage("Manager capacity out of bounds [1, 999] : 0")
    }

    @Test
    fun should_fail_if_manager_capacity_too_big() {
        input.managerCapacity = 1000
        assertMessage("Manager capacity out of bounds [1, 999] : 1000")
    }

    @Test
    fun should_fail_if_no_engineer_capacity_defined() {
        input.engineerCapacity = null
        assertMessage("Engineer capacity undefined")
    }

    @Test
    fun should_fail_if_engineer_capacity_too_small() {
        input.engineerCapacity = 0
        assertMessage("Engineer capacity out of bounds [1, 1000] : 0")
    }

    @Test
    fun should_fail_if_engineer_capacity_too_big() {
        input.engineerCapacity = 1001
        assertMessage("Engineer capacity out of bounds [1, 1000] : 1001")
    }
}