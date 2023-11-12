package lib.presentation.pidwindow

sealed class PidWindowEvent {
    data class OnPathFieldChange(val value: String): PidWindowEvent()
    data class OnCustomFolderCheckboxChange(val value: Boolean): PidWindowEvent()
    data class OnCustomFolderNameChange(val value: String): PidWindowEvent()
    data class OnFirstBoxChange(val value: Boolean): PidWindowEvent()
    data class OnSecondBoxChange(val value: Boolean): PidWindowEvent()
    data class OnThirdBoxChange(val value: Boolean): PidWindowEvent()
    object CreateDirs: PidWindowEvent()
    object Save: PidWindowEvent()
}