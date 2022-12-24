package com.example.mazaady.core.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AdapterDelegate<MODEL, in VH>(val modelClass: Class<out MODEL>) {
    private var commandListener: CommandListener? = null

    fun attachCommandListener(listener: CommandListener) {
        this.commandListener = listener
    }

    fun deAttachListener() {
        this.commandListener = null
    }

    internal fun handleCommand(command: BaseCommand) = commandListener?.consumeCommand(command)

    abstract fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun onBindViewHolder(
        viewHolder: VH,
        model: MODEL,
        payload: List<AdapterItem.PayLoadable>
    )

    open fun onViewAttachedToWindow(holder: VH) {}
    open fun onViewDeAttachedToWindow(holder: VH) {}
}