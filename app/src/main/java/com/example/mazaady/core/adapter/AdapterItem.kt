package com.example.mazaady.core.adapter

/**
 * @Author Abdullah Abo El~Makarem on 09/06/2022.
 */
interface AdapterItem {
    fun id() = this.toString()
    fun payLoad(newValue: Any): PayLoadable = PayLoadable.None

    interface PayLoadable {
        object None : PayLoadable
    }
}