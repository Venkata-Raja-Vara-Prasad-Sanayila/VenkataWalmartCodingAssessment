package com.walmart.venkata.data.dto

import com.google.gson.annotations.SerializedName


/**
 * Data Transfer Object (DTO) representing a country as received from the API.
 */
data class CountryDto(

    @SerializedName("capital")
    val capital: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("currency")
    val currency: Currency,

    @SerializedName("flag")
    val flagUrl: String,

    @SerializedName("language")
    val language: Language,

    @SerializedName("name")
    val name: String,

    @SerializedName("region")
    val region: String

)
