package com.bleudev.mmc.mixin.client.accessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(ChatScreen.class)
public interface ChatScreenAccessor {
    @Accessor("commandSuggestions")
    CommandSuggestions mmc$commandSuggestions();
}
