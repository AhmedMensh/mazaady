package com.example.mazaady.presentation.first_screen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mazaady.core.adapter.AdapterDelegate
import com.example.mazaady.core.adapter.AdapterItem
import com.example.mazaady.core.adapter.BaseCommand
import com.example.mazaady.databinding.ListItemBottomSheetBinding
import com.example.mazaady.domain.models.CategoryModel
import com.example.mazaady.domain.models.SubCategoryModel

class SubCategoryAdapterDelegate :
    AdapterDelegate<SubCategoryModel, SubCategoryAdapterDelegate.ViewHolder>(
        SubCategoryModel::class.java
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
        model: SubCategoryModel,
        payload: List<AdapterItem.PayLoadable>
    ) {
        with(viewHolder.binding) {
            text.text = model.name
            root.setOnClickListener { handleCommand(SubCategoriesAdapterCommand.SelectedSubCategory(model)) }
        }
    }

    class ViewHolder(val binding: ListItemBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root)


    sealed class SubCategoriesAdapterCommand : BaseCommand {
        data class SelectedSubCategory(val category: SubCategoryModel) : SubCategoriesAdapterCommand()

    }
}