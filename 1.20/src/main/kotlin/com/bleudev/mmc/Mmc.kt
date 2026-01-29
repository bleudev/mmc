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
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val LOGGER: Logger = LoggerFactory.getLogger("ModMenuCommand")

class Mmc : ClientModInitializer {
    private var configModId: String? = null

    private fun getAllMods(): List<String> = FabricLoader.getInstance().allMods.map { it.metadata.id }
    private fun getAllModsWithConfig(): List<String> = getAllMods().filter { ModMenu.getConfigScreen(it, null) != null }

    override fun onInitializeClient() {
        AbstractMmcKey.initializeKeys()
        dataInit()
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
                .executes { ctx ->
                    if (lastModId == null) {
                        ctx.source.sendError(Component.translatable("commands.mmc.config.error.last.null"))
                        return@executes -1
                    }
                    configModId = lastModId
                    1
                }
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
                            val txt = if (modid in getAllMods())
                                Component.translatable("commands.mmc.config.error.unknown.config")
                            else Component.translatable("commands.mmc.config.error.unknown.mod")
                            ctx.source.sendFeedback(txt.withStyle(ChatFormatting.RED))
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
                lastModId = configModId
                mc.setScreen(ModMenu.getConfigScreen(configModId, mc.screen))
                configModId = null
            }

            AbstractMmcKey.KEYS.forEach { it.tick(mc) }
        }
    }
}
