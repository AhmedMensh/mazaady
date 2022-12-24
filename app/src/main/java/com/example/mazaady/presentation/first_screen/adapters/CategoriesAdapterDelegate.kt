package com.example.mazaady.presentation.first_screen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mazaady.core.adapter.AdapterDelegate
import com.example.mazaady.core.adapter.AdapterItem
import com.example.mazaady.core.adapter.BaseCommand
import com.example.mazaady.databinding.ListItemBottomSheetBinding
import com.example.mazaady.domain.models.CategoryModel

class CategoriesAdapterDelegate :
    AdapterDelegate<CategoryModel, CategoriesAdapterDelegate.ViewHolder>(
        CategoryModel::class.java
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
        model: CategoryModel,
        payload: List<AdapterItem.PayLoadable>
    ) {
        with(viewHolder.binding) {
            text.text = model.name
            root.setOnClickListener { handleCommand(CategoriesAdapterCommand.SelectedCategory(model)) }
        }
    }

    class ViewHolder(val binding: ListItemBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root)


    sealed class CategoriesAdapterCommand : BaseCommand {
        data class SelectedCategory(val category: CategoryModel) : CategoriesAdapterCommand()

    }
}