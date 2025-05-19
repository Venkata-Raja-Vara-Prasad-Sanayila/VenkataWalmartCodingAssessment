package com.walmart.venkata.presentation.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.walmart.venkata.databinding.ViewHolderCountryItemBinding
import com.walmart.venkata.domain.model.Country
import com.walmart.venkata.presentation.view.viewholder.CountryViewHolder

/**
 * RecyclerView Adapter for displaying a list of countries.
 *
 * @property countries The list of [Country] objects to be displayed.
 */
class CountryAdapter(private var countries: List<Country>): Adapter<CountryViewHolder>() {

    /**
     * Creates a new [CountryViewHolder] by inflating the item layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderCountryItemBinding.inflate(inflater, parent, false)
        return CountryViewHolder(binding)
    }

    /**
     * Returns the total number of items in the list.
     */
    override fun getItemCount() = countries.size

    /**
     * Binds the country data to the view holder at the given position.
     */
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country)
    }

    /**
     * Updates the adapter’s data by assigning
     * @param newData and refreshes the list.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newData: List<Country>){
        countries = newData
        notifyDataSetChanged()
    }

}