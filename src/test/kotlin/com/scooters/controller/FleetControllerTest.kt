package com.scooters.controller

import com.nhaarman.mockitokotlin2.anyOrNull
import com.scooters.model.InputDTO
import com.scooters.service.ComputeService
import com.scooters.validation.ValidationError
import com.scooters.validation.ValidationException
import com.scooters.validation.ValidationService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(SpringRunner::class)
@WebMvcTest(FleetController::class)
class FleetControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var validationService: ValidationService

    @MockBean
    lateinit var computeService: ComputeService

    @Test
    @Throws(Exception::class)
    fun given_bad_values_should_return_errors() {

        given(validationService.validate(anyOrNull())).willThrow(
                ValidationException(listOf(
                        ValidationError("Error 1"),
                        ValidationError("Error 2")
                ))
        )

        mvc.perform(post("/api/employees", InputDTO())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andDo {
                    JSONAssert.assertEquals(
                            """
                                {"message":"Error 1"},
                                {"message":"Error 2"}
                            """.trimIndent(),
                            it.response.errorMessage,
                            false)
                }
    }

    @Test
    fun given_good_values_should_return_fleet_definition() {

        given(computeService.compute(anyOrNull())).willReturn(5)

        mvc.perform(post("/api/employees", InputDTO())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andDo {
                    JSONAssert.assertEquals(
                            """{"fleet_engineers":5}""",
                            it.response.contentAsString,
                            true)
                }
    }
}