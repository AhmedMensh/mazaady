package com.example.mazaady.domain.models

import android.os.Parcelable
import com.example.mazaady.core.adapter.AdapterItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(
    val children: List<SubCategoryModel?>?,
    val circleIcon: String,
    val description: String,
    val disableShipping: Int,
    val id: Int,
    val image: String,
    val name: String,
    val slug: String
) : Parcelable, AdapterItem {
    override fun id(): String {
        return "$id"
    }
}

