package com.example.mazaady.presentation.first_screen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mazaady.core.adapter.AdapterDelegate
import com.example.mazaady.core.adapter.AdapterItem
import com.example.mazaady.core.adapter.BaseCommand
import com.example.mazaady.databinding.ListItemBottomSheetBinding
import com.example.mazaady.domain.models.PropertyOptionModel

class PropertyOptionsAdapterDelegate :
    AdapterDelegate<PropertyOptionModel, PropertyOptionsAdapterDelegate.ViewHolder>(
        PropertyOptionModel::class.java
    ) {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(

        ListItemBottomSheetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        model: PropertyOptionModel,
        payload: List<AdapterItem.PayLoadable>
    ) {
        with(viewHolder.binding) {
            text.text = model.name
            ivCheck.isVisible = model.isSelected
            root.setOnClickListener {
                handleCommand(PropertyOptionsAdapterCommand.SelectedOption(model))
            }
        }
    }

    class ViewHolder(val binding: ListItemBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root)


    sealed interface PropertyOptionsAdapterCommand : BaseCommand {
        data class SelectedOption(val option: PropertyOptionModel) :
            PropertyOptionsAdapterCommand

    }
}