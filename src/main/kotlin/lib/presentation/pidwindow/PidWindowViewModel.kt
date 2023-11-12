package lib.presentation.pidwindow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import lib.model.Settings
import lib.service.SettingsService
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PidWindowViewModel(
    private val settingsService: SettingsService = SettingsService()
) {
    var state by mutableStateOf(PidWindowState())
        private set

    var eventFlow = MutableSharedFlow<UiEvent>()
        private set

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        val settings = settingsService.loadSettings()
        state = state.copy(
            directoryPath = settings.directoryPath,
            createFolderOption1 = settings.createFolderOption1,
            createFolderOption2 = settings.createFolderOption2,
            createFolderOption3 = settings.createFolderOption3,
        )
    }

    fun onEvent(event: PidWindowEvent) {
        when (event) {
            is PidWindowEvent.OnFirstBoxChange -> {
                state = state.copy(createFolderOption1 = event.value)
            }

            is PidWindowEvent.OnSecondBoxChange -> {
                state = state.copy(createFolderOption2 = event.value)
            }

            is PidWindowEvent.OnThirdBoxChange -> {
                state = state.copy(createFolderOption3 = event.value)
            }

            PidWindowEvent.Save -> {
                try {
                    saveSettings()
                    coroutineScope.launch { eventFlow.emit(UiEvent.ShowSnackbar("Settings saved successfully!"))  }

                } catch (e: Exception){
                    coroutineScope.launch { eventFlow.emit(UiEvent.ShowSnackbar("Oops, Something went wrong!"))  }
                }
            }
            PidWindowEvent.CreateDirs -> {
                try {
                    createDirs()
                    coroutineScope.launch { eventFlow.emit(UiEvent.ShowSnackbar("Directories successfully created!")) }
                } catch (e: Exception) {
                    coroutineScope.launch { eventFlow.emit(UiEvent.ShowSnackbar("Oops, Something went wrong!"))  }
                }
            }
            is PidWindowEvent.OnPathFieldChange -> {
                state = state.copy(directoryPath = event.value)
            }

            is PidWindowEvent.OnCustomFolderNameChange -> {
                state = state.copy(customDirectoryName = event.value)
            }

            is PidWindowEvent.OnCustomFolderCheckboxChange -> {
                state = if (event.value) {
                    state.copy(customDateCheckBox = event.value)
                } else {
                    state.copy(
                        customDateCheckBox = event.value,
                        customDirectoryName = ""
                    )
                }
            }
        }
    }

    private fun createDirs() {
        if (state.directoryPath.isNotEmpty()) {
            val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val currentDate = LocalDate.now().format(dateFormat)
            val folderPath = if (!state.customDateCheckBox) {
                File(state.directoryPath, currentDate)
            } else {
                File(state.directoryPath, state.customDirectoryName)
            }

            if (!folderPath.exists()) {
                folderPath.mkdirs()
            }

            if (state.createFolderOption1) {
                val childPath = "Canon"
                File(folderPath, childPath).mkdir()
                File("$folderPath\\$childPath\\", "CR3").mkdir()
            }

            if (state.createFolderOption2) {
                val childPath = "Nikon"
                File(folderPath, childPath).mkdir()
                File("$folderPath\\$childPath\\", "NEF").mkdir()
            }

            if (state.createFolderOption3) {
                val childPath = "Pixel 8 Pro"
                File(folderPath, childPath).mkdir()
                File("$folderPath\\$childPath\\", "RAW").mkdir()
            }
        }
    }

    private fun saveSettings() {
        val actualSettings = Settings(
            directoryPath = state.directoryPath,
            createFolderOption1 = state.createFolderOption1,
            createFolderOption2 = state.createFolderOption2,
            createFolderOption3 = state.createFolderOption3,
        )
        settingsService.saveSettings(actualSettings)
    }
}