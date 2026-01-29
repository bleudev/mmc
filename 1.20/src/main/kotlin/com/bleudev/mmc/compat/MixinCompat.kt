package com.bleudev.mmc.compat

import com.bleudev.mmc.mixin.client.accessor.ChatScreenAccessor
import net.minecraft.client.gui.components.CommandSuggestions
import net.minecraft.client.gui.screens.ChatScreen

// Accessors
val ChatScreen.suggestions: CommandSuggestions
    get() = (this as ChatScreenAccessor).`mmc$commandSuggestions`()
