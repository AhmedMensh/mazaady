package com.example.mazaady.core.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gswebtech.dealnotdeal.core.presentation.adapter.*

/**
 * @Author Abdullah Abo El~Makarem on 18/06/2022.
 */
class AdapterDelegateImpl(
    private val listener: CommandListener,
    delegatesList: List<AdapterDelegate<out AdapterItem, *>> = listOf(),
    loadMoreCallback: (() -> Unit)? = null
) : ListAdapter<AdapterItem, RecyclerView.ViewHolder>(AdapterItemDiffUtil()) {
    private val delegatesManager = AdapterDelegatesManager()
    private val paginationHelper = PaginationHelper(loadMoreCallback)

    init {
        delegatesList.filterIsInstance<AdapterDelegate<AdapterItem, RecyclerView.ViewHolder>>()
            .forEach { delegate ->
                delegate.attachCommandListener(listener)
                delegatesManager.addDelegate(delegate)
            }
    }

    fun submitList(adapterItems: List<AdapterItem>, hasNextPage: Boolean) {
        this.submitList(adapterItems)
        paginationHelper.updateLoadingStatus(hasNextPage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        delegatesManager.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        delegatesManager.onBindViewHolder(holder, getItem(position), listOf())

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        delegatesManager.onBindViewHolder(
            holder,
            getItem(position),
            payloads.map { it as AdapterItem.PayLoadable })
        paginationHelper.loadMore(position, currentList.size)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewDetachedFromWindow(holder)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewAttachedToWindow(holder)
    }

    override fun getItemCount() = currentList.size

    override fun getItemViewType(position: Int) = delegatesManager.getViewType(getItem(position))
}