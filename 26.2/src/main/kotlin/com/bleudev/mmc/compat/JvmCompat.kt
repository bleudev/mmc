package com.bleudev.mmc.compat

import com.bleudev.mmc.lastModId

class JvmCompat {
    companion object {
        @JvmStatic
        fun jvmSetLastModId(new: String?) {
            lastModId = new
        }
    }
}