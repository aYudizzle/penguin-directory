package lib.service

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lib.model.Settings
import java.io.File

class SettingsService {
    private val settingsFileName = "settings.json"

    fun saveSettings(settings: Settings) {
        val settingsFile = File(settingsFileName)
        val jsonSettings = Json.encodeToString(settings)
        settingsFile.writeText(jsonSettings)
    }

    fun loadSettings(): Settings {
        return if(File(settingsFileName).exists()){
            Json.decodeFromString(File(settingsFileName).readText())
        } else {
            Settings()
        }
    }
}