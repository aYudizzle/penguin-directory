package lib

import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import kotlinx.coroutines.flow.MutableSharedFlow
import lib.presentation.pidwindow.UiEvent
import lib.presentation.pidwindow.PidWindowEvent
import lib.presentation.pidwindow.PidWindowState
import lib.presentation.pidwindow.PidWindowViewModel
import lib.presentation.pidwindow.components.PidWindowContent

@Composable
fun App(
    state: PidWindowState,
    onEvent: (PidWindowEvent) -> Unit,
    uiEvent: MutableSharedFlow<UiEvent>
) {
   PidWindowContent(
       uiEvent = uiEvent,
       onEvent = onEvent,
       state = state
   )
}

fun main() = application {
    val viewModel = remember { PidWindowViewModel() }
    val windowState = rememberWindowState(placement = WindowPlacement.Floating, width = 400.dp, height = 800.dp)

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "Penguin director(y) by ayupi.dev",
        icon = painterResource("piicon.png")
    ) {
        App(
            state = viewModel.state,
            onEvent = viewModel::onEvent,
            uiEvent = viewModel.eventFlow
        )
    }
}
