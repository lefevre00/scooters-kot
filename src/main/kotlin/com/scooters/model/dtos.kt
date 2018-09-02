package com.scooters.model

import com.fasterxml.jackson.annotation.JsonProperty

data class InputDTO (
        var scooters : List<Int?>? = null,
        @JsonProperty("C")
        var managerCapacity: Int? = null,
        @JsonProperty("P")
        var engineerCapacity: Int? = null
)

data class OutputDTO (
        @JsonProperty("fleet_engineers")
        val fleet : Int? = null
)