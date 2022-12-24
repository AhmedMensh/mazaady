package com.gswebtech.dealnotdeal.core.presentation.adapter

/**
 * @Author Abdullah Abo El~Makarem on 09/06/2022.
 */
class PaginationHelper(private val loadMoreCallback: (() -> Unit)? = null) {
    private val PAGINATION_LOAD_FACOR = 10
    private var isLoading = false
    private var hasNext = false

    fun updateLoadingStatus(hasNextPage: Boolean, isLoading: Boolean = false) {
        hasNext = hasNextPage
        this.isLoading = isLoading
    }

    fun loadMore(currentPosition: Int, listSize: Int) {
        if (hasNext && !isLoading && (currentPosition >= listSize - PAGINATION_LOAD_FACOR)) {
            isLoading = true
            loadMoreCallback?.invoke()
        }
    }
}