package com.bleudev.mmc

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Files
import java.nio.file.Path

var shouldModsBeOpened: Boolean = false

// Data storing
var lastModId: String?
    get() = dataLoad().last
    set(new) {dataSave(dataLoad().withLast(new))}

private val dataPath: Path
    get() = FabricLoader.getInstance().configDir.resolve("mmc.data.json")

@Serializable
data class DataStorage(val last: String? = null) {
    fun withLast(new: String?): DataStorage = DataStorage(new)
}

private fun dataLoad(): DataStorage {
    try {
        return Json.decodeFromString<DataStorage>(Files.readString(dataPath))
    }
    catch (e: Throwable) {
        LOGGER.error("Error while loading data storage:\n${e.stackTrace.joinToString("\n")}\n\nPlease report about it.")
    }
    return DataStorage()
}

private fun dataSave(data: DataStorage) {
    try {
        Files.writeString(dataPath, Json{prettyPrint = true}.encodeToString(data))
    }
    catch (e: Throwable) {
        LOGGER.error("Error while saving data storage:\n${e.stackTrace.joinToString("\n")}\n\nPlease report about it.")
    }
}

fun dataInit() {
    try {
        if (!Files.exists(dataPath)) Files.writeString(dataPath, "{}")
    } catch (e: Throwable) {
        LOGGER.error("Error while initializing data storage:\n${e.stackTrace.joinToString("\n")}\n\nPlease report about it.")
    }
    dataSave(dataLoad())
}