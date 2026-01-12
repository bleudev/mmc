package com.bleudev.mmc.custom.key

import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.ChatScreen

class OpenConfigCommandKey: AbstractMmcKey() {
    override fun register(): KeyMapping = KeyMappingHelper
        .registerKeyMapping(KeyMapping("key.mmc.open_config_command", -1, CATEGORY_GENERAL))

    override fun onPressed(mc: Minecraft) {
        mc.setScreen(ChatScreen("/config ", false))
    }
}