package com.example.mazaady.domain.models

import com.example.mazaady.core.adapter.AdapterItem

class BidderModel(
    val id: Int,
) : AdapterItem{
    override fun id(): String {
        return "$id"
    }
}