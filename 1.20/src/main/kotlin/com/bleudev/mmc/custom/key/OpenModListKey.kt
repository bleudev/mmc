package com.bleudev.mmc.custom.key

import com.bleudev.mmc.shouldModsBeOpened
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft

class OpenModListKey : AbstractMmcKey() {
    override fun register(): KeyMapping {
        return KeyBindingHelper.registerKeyBinding(KeyMapping("key.mmc.open_mod_list", -1, CATEGORY_GENERAL))
    }

    override fun onPressed(mc: Minecraft) {
        shouldModsBeOpened = true
    }
}