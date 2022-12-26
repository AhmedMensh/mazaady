package com.example.mazaady.domain.models

import android.os.Parcelable
import com.example.mazaady.core.adapter.AdapterItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class PropertyOptionModel(
    val child: Boolean,
    val id: Int,
    val name: String,
    val parent: Int,
    val slug: String,
    var isOtherOption: Boolean = false,
    var isSelected: Boolean = false
) : Parcelable, AdapterItem {
    override fun id(): String {
        return id.toString()
    }
}