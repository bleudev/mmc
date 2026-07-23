package com.bleudev.mmc.custom.key

import com.bleudev.mmc.shouldModsBeOpened
import com.mojang.blaze3d.platform.InputConstants
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft

class OpenModListKey : AbstractMmcKey() {
    override fun register(): KeyMapping = KeyMappingHelper
        .registerKeyMapping(KeyMapping("key.mmc.open_mod_list", InputConstants.UNKNOWN.value, CATEGORY_GENERAL))

    override fun onPressed(mc: Minecraft) {
        shouldModsBeOpened = true
    }
}