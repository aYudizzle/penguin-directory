package lib.presentation.pidwindow

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
}