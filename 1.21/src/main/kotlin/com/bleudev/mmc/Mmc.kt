package com.bleudev.mmc

import com.bleudev.mmc.custom.key.AbstractMmcKey
import com.mojang.brigadier.arguments.StringArgumentType
import com.terraformersmc.modmenu.ModMenu
import com.terraformersmc.modmenu.gui.ModsScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.network.chat.Component

class Mmc : ClientModInitializer {
    private var configModId: String? = null

    private fun getAllMods(): List<String> = FabricLoader.getInstance().allMods.map { it.metadata.id }
    private fun getAllModsWithConfig(): List<String> = getAllMods().filter { ModMenu.hasConfigScreen(it) }

    override fun onInitializeClient() {
        AbstractMmcKey.initializeKeys()
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(ClientCommandManager
                .literal("mods")
                .executes {
                    shouldModsBeOpened = true
                    1
                }
            )
            dispatcher.register(ClientCommandManager
                .literal("config")
                .then(ClientCommandManager
                    .argument("modid", StringArgumentType.word())
                    .suggests { _, builder ->
                        getAllModsWithConfig().forEach {
                            if (it.startsWith(builder.remainingLowerCase))
                                builder.suggest(it)
                        }
                        builder.buildFuture()
                    }
                    .executes { ctx ->
                        val modid = StringArgumentType.getString(ctx, "modid")
                        if (modid in getAllModsWithConfig())
                            configModId = modid
                        else {
                            val txt: Component
                            if (modid in getAllMods())
                                txt = Component.translatable("text.mmc.config.unknown.config")
                            else txt = Component.translatable("text.mmc.config.unknown.mod")
                            ctx.source.sendFeedback(txt.withColor(0xff0000))
                        }
                        1
                    }
                )
            )
        }

        // Kludge :(
        // Why?!..
        ClientTickEvents.END_CLIENT_TICK.register { mc ->
            if (shouldModsBeOpened) {
                shouldModsBeOpened = false
                mc.setScreen(ModsScreen(mc.screen))
            }
            configModId?.let {
                mc.setScreen(ModMenu.getConfigScreen(configModId, mc.screen))
                configModId = null
            }

            AbstractMmcKey.KEYS.forEach { it.tick(mc) }
        }
    }
}
