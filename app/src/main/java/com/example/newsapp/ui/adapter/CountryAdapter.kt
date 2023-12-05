import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.RecyclerCountryItemBinding
import com.example.newsapp.domain.model.enums.Countries
import com.example.newsapp.utils.SelectableData

class CountryAdapter(
    private val callback: (SelectableData<Countries>) -> Unit
) : ListAdapter<SelectableData<Countries>, CountryAdapter.VHMainList>(CountriesDiffCallback) {
    class VHMainList(val binding: RecyclerCountryItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHMainList {
        val binding = RecyclerCountryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VHMainList(binding)
    }

    override fun onBindViewHolder(holder: VHMainList, position: Int) {
        val currentSelectableCountry = getItem(position)

        val resources = holder.binding.btnCountry.resources
        val selectedDrawable = ResourcesCompat
            .getDrawable(resources, R.drawable.background_selected_button, null)
        val unselectedDrawable = ResourcesCompat
            .getDrawable(resources, R.drawable.background_unselected_button, null)

        holder.binding.apply {
            btnCountry.setImageResource(currentSelectableCountry.data.flagImageId)

            btnCountry.setOnClickListener {
                callback(currentSelectableCountry)
            }

            if (currentSelectableCountry.isSelected) {
                btnCountry.foreground = selectedDrawable
                tvSelected.visibility = View.VISIBLE
            } else {
                btnCountry.foreground = unselectedDrawable
                tvSelected.visibility = View.INVISIBLE
            }
        }
    }
}

object CountriesDiffCallback : DiffUtil.ItemCallback<SelectableData<Countries>>() {
    override fun areItemsTheSame(
        oldItem: SelectableData<Countries>, newItem: SelectableData<Countries>
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: SelectableData<Countries>, newItem: SelectableData<Countries>
    ): Boolean {
        return oldItem.isSelected == newItem.isSelected
    }
}