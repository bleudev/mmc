package com.bleudev.mmc.custom.key

import com.bleudev.mmc.compat.suggestions
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.ChatScreen

class OpenConfigCommandKey: AbstractMmcKey() {
    override fun register(): KeyMapping {
        return KeyBindingHelper.registerKeyBinding(KeyMapping("key.mmc.open_config_command", -1, CATEGORY_GENERAL))
    }

    override fun onPressed(mc: Minecraft) {
        val screen = ChatScreen("/config ", false)
        mc.setScreen(screen)
        screen.suggestions.showSuggestions(true)
    }
}