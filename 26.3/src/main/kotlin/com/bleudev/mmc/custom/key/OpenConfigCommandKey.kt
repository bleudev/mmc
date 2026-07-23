package com.bleudev.mmc.custom.key

import com.bleudev.mmc.compat.suggestions
import com.mojang.blaze3d.platform.InputConstants
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.ChatScreen

class OpenConfigCommandKey: AbstractMmcKey() {
    override fun register(): KeyMapping = KeyMappingHelper
        .registerKeyMapping(KeyMapping("key.mmc.open_config_command", InputConstants.UNKNOWN.value, CATEGORY_GENERAL))

    override fun onPressed(mc: Minecraft) {
        val screen = ChatScreen("/config ", false)
        mc.gui.setScreen(screen)
        screen.suggestions.showSuggestions(true)
    }
}