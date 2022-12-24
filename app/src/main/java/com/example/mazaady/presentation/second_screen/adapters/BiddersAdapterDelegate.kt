package com.example.mazaady.presentation.second_screen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mazaady.core.adapter.AdapterDelegate
import com.example.mazaady.core.adapter.AdapterItem
import com.example.mazaady.core.adapter.BaseCommand
import com.example.mazaady.databinding.ListItemBidderBinding
import com.example.mazaady.databinding.ListItemBottomSheetBinding
import com.example.mazaady.domain.models.BidderModel
import com.example.mazaady.domain.models.CategoryModel
import com.example.mazaady.domain.models.SubCategoryModel

class BiddersAdapterDelegate :
    AdapterDelegate<BidderModel, BiddersAdapterDelegate.ViewHolder>(
        BidderModel::class.java
    ) {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(

        ListItemBidderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        model: BidderModel,
        payload: List<AdapterItem.PayLoadable>
    ) {}

    class ViewHolder(val binding: ListItemBidderBinding) :
        RecyclerView.ViewHolder(binding.root)

}