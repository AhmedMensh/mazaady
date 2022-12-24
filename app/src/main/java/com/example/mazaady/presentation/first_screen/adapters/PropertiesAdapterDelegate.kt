package com.example.mazaady.presentation.first_screen.adapters

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.mazaady.core.adapter.AdapterDelegate
import com.example.mazaady.core.adapter.AdapterItem
import com.example.mazaady.core.adapter.BaseCommand
import com.example.mazaady.databinding.ListItemPropertyFeildBinding
import com.example.mazaady.domain.models.PropertyModel


class PropertiesAdapterDelegate :
    AdapterDelegate<PropertyModel, PropertiesAdapterDelegate.ViewHolder>(
        PropertyModel::class.java
    ) {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(

        ListItemPropertyFeildBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        model: PropertyModel,
        payload: List<AdapterItem.PayLoadable>
    ) {

        with(viewHolder.binding) {
            tiProperty.isHintEnabled = true
            tiProperty.hint = model.name
            etProperty.setText(model.selectedOptionModel?.name)
            if (model.options.isNullOrEmpty() || model.selectedOptionModel?.isOtherOption == true) {
                etProperty.isFocusable = true
                etProperty.isFocusableInTouchMode = true
                etProperty.isCursorVisible = true
                etProperty.imeOptions = EditorInfo.IME_ACTION_DONE
                etProperty.inputType = InputType.TYPE_CLASS_TEXT

                etProperty.doOnTextChanged { text, _, _, _ ->
                    handleCommand(
                        PropertiesAdapterCommand.UpdatePropertyOptionValue(
                            model,
                            etProperty.text.toString()
                        ))
                }

            } else {

                etProperty.setOnClickListener {
                    handleCommand(PropertiesAdapterCommand.ShowPropertyOptions(model))
                }
            }
        }
    }

    class ViewHolder(val binding: ListItemPropertyFeildBinding) :
        RecyclerView.ViewHolder(binding.root)


    sealed interface PropertiesAdapterCommand : BaseCommand {
        data class ShowPropertyOptions(val property: PropertyModel) : PropertiesAdapterCommand
        data class UpdatePropertyOptionValue(val property: PropertyModel, val value: String) :
            PropertiesAdapterCommand
    }
}