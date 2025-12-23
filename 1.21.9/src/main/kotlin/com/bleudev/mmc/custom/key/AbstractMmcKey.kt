package com.bleudev.mmc.custom.key

import com.bleudev.mmc.getResourceLocation
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
        @JvmStatic
        protected val CATEGORY_GENERAL: KeyMapping.Category = KeyMapping.Category.register(getResourceLocation("general"))

        val KEYS: MutableList<AbstractMmcKey> = ArrayList<AbstractMmcKey>()
        fun initializeKeys() {
            KEYS.add(OpenModListKey())
            KEYS.add(OpenConfigCommandKey())
        }
    }
}