package com.bleudev.mmc.mixin.client;

import com.bleudev.mmc.compat.JvmCompat;
import com.terraformersmc.modmenu.ModMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ModMenu.class)
public class ModMenuMixin {
    @Inject(method = "getConfigScreen", at = @At("RETURN"))
    private static void setLastModId(String modId, Screen parent, CallbackInfoReturnable<@Nullable Screen> cir) {
        if (cir.getReturnValue() != null) JvmCompat.jvmSetLastModId(modId);
    }
}
