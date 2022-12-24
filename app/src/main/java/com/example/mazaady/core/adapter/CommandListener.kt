package com.example.mazaady.core.adapter


/**
 * @Author Abdullah Abo El~Makarem on 09/06/2022.
 */
interface BaseCommand
interface CommandListener {
    fun consumeCommand(action: BaseCommand)
}