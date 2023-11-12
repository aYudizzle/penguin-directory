package lib.model

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val directoryPath: String = "",
    val createFolderOption1: Boolean = false,
    val createFolderOption2: Boolean = false,
    val createFolderOption3: Boolean = false
)
