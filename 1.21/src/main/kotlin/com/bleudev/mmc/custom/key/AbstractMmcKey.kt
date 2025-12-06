package com.bleudev.mmc.custom.key

import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft

abstract class AbstractMmcKey {
    protected abstract fun register(): KeyMapping
    protected abstract fun onPressed(mc: Minecraft)

    private val mapping: KeyMapping = register()
    fun tick(mc: Minecraft) {
        while (mapping.consumeClick()) onPressed(mc)
    }

    companion object {
        protected const val CATEGORY_GENERAL: String = "key.categories.mmc.general"

        val KEYS: MutableList<AbstractMmcKey> = ArrayList<AbstractMmcKey>()
        fun initializeKeys() {
            KEYS.add(OpenModListKey())
        }
    }
}