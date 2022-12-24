package com.example.mazaady.core.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mazaady.core.adapter.AdapterItem

/**
 * @Author Abdullah Abo El~Makarem on 09/06/2022.
 */
class AdapterItemDiffUtil : DiffUtil.ItemCallback<AdapterItem>() {
    override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem) =
        oldItem::class == newItem::class && oldItem.id() == newItem.id()


    override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem) =
        oldItem.equals(newItem)

    override fun getChangePayload(oldItem: AdapterItem, newItem: AdapterItem) =
        oldItem.payLoad(newItem)
}