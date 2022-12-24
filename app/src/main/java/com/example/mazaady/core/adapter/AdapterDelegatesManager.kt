package com.example.mazaady.core.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * @Author Abdullah Abo El~Makarem on 18/06/2022.
 */
class AdapterDelegatesManager {
    private val listOfDelegates = ArrayList<AdapterDelegate<AdapterItem, ViewHolder>>()

    fun addDelegate(delegate: AdapterDelegate<AdapterItem, ViewHolder>) {
        listOfDelegates.add(delegate)
    }

    fun getViewType(adapterItem: AdapterItem): Int {
        listOfDelegates.forEach {
            if (it.modelClass == adapterItem.javaClass)
                return listOfDelegates.indexOf(it)
        }
        throw  NoSuchElementException("No Adapter Delegate was added that matches item ${adapterItem.javaClass}")
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        listOfDelegates.getOrNull(viewType)?.let {
            return it.onCreateViewHolder(parent)
        }
            ?: throw NoSuchElementException("No Adapter Delegate was added that matches type $viewType")
    }

    fun onBindViewHolder(
        viewHolder: ViewHolder,
        item: AdapterItem,
        payLoadable: List<AdapterItem.PayLoadable>
    ) {
        val delegate = listOfDelegates.getOrNull(viewHolder.itemViewType)
            ?: throw NoSuchElementException("No Adapter Delegate was added that matches type ${viewHolder.itemViewType}")
        delegate.onBindViewHolder(viewHolder = viewHolder, model = item, payload = payLoadable)
    }

    fun onViewDetachedFromWindow(holder: ViewHolder) {
        val delegate = listOfDelegates.getOrNull(holder.itemViewType) ?: return
        delegate.onViewDeAttachedToWindow(holder)
    }

    fun onViewAttachedToWindow(holder: ViewHolder) {
        val delegate = listOfDelegates.getOrNull(holder.itemViewType) ?: return
        delegate.onViewAttachedToWindow(holder)
    }
}