package lib.presentation.pidwindow

data class PidWindowState(
    val directoryPath: String = "",
    val createFolderOption1: Boolean = false,
    val createFolderOption2: Boolean = false,
    val createFolderOption3: Boolean = false,
    // -- //
    val customDateCheckBox: Boolean = false,
    val customDirectoryName: String = "",
)