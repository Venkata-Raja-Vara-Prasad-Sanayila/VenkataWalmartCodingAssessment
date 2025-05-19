package com.walmart.venkata.presentation.view.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.walmart.venkata.R
import com.walmart.venkata.databinding.ViewHolderCountryItemBinding
import com.walmart.venkata.domain.model.Country


/**
 * ViewHolder that binds [Country] data to the view holder country item layout.
 */
class CountryViewHolder(private val binding: ViewHolderCountryItemBinding): ViewHolder(binding.root) {

    fun bind(country: Country) {
        with(binding){
            txtCountryNameRegion.text =
                itemView.context.getString(R.string.country_name_region, country.name, country.region)
            txtCapital.text = country.capital
            txtCode.text = country.code
        }
    }

}