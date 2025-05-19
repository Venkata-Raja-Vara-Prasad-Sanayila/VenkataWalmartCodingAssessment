package com.walmart.venkata.data.mapper

import com.walmart.venkata.data.dto.CountryDto
import com.walmart.venkata.domain.model.Country

/**
 * Converts [CountryDto] (data layer) to [Country] (domain layer)
 */
fun CountryDto.toDomainModel(): Country{

    return Country(
        name = this.name,
        capital = this.capital,
        code = this.code,
        region = this.region
    )

}
