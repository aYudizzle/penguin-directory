package lib.presentation.pidwindow.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import lib.presentation.pidwindow.UiEvent
import lib.presentation.pidwindow.PidWindowState
import lib.presentation.pidwindow.PidWindowEvent

@Composable
fun PidWindowContent(
    state: PidWindowState,
    onEvent: (PidWindowEvent) -> Unit,
    uiEvent: MutableSharedFlow<UiEvent>
) {
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(true){
        uiEvent.collectLatest { event ->
            when(event){
                is UiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(message = event.message, duration = SnackbarDuration.Short)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { TopAppBar(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(modifier = Modifier.padding(horizontal = 10.dp).fillMaxWidth(), text = "Penguin Director(y)", textAlign = TextAlign.Center)
        } }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource("pilap.png"),
                contentDescription = null,
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(fraction = 0.9f),
                value = state.directoryPath,
                onValueChange = {
                    onEvent(PidWindowEvent.OnPathFieldChange(it))
                },
                label = { Text(text = "Filesystem Path") },
                textStyle = TextStyle(fontFamily = FontFamily.Monospace)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(fraction = 0.9f)
            ){
                Checkbox(
                    checked = state.customDateCheckBox,
                    onCheckedChange = { onEvent(PidWindowEvent.OnCustomFolderCheckboxChange(it)) }
                )
                OutlinedTextField(
                    value = state.customDirectoryName,
                    onValueChange = {
                        onEvent(PidWindowEvent.OnCustomFolderNameChange(it))
                    },
                    label = { Text(text = "Custom Folder") },
                    textStyle = TextStyle(fontFamily = FontFamily.Monospace),
                    enabled = state.customDateCheckBox
                )
            }
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.heightIn(min = 150.dp, max = 150.dp)
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = state.createFolderOption1,
                        onCheckedChange = {
                            onEvent(PidWindowEvent.OnFirstBoxChange(it))
                        }
                    )
                    Text(text = "Canon M50 Mark II")
                }

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = state.createFolderOption2,
                        onCheckedChange = { onEvent(PidWindowEvent.OnSecondBoxChange(it)) }
                    )
                    Text(text = "Nikon D5300")
                }

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = state.createFolderOption3,
                        onCheckedChange = { onEvent(PidWindowEvent.OnThirdBoxChange(it)) }
                    )
                    Text(text = "Pixel 8 Pro")
                }
            }
            Row(modifier = Modifier.padding(all = 10.dp)) {
                Button(onClick = {
                    onEvent(PidWindowEvent.CreateDirs)
                }) {
                    Text("Make Directories")
                }
                Spacer(modifier = Modifier.width(5.dp))
                Button(onClick = {
                    onEvent(PidWindowEvent.Save)
                }) {
                    Text("Save Settings")
                }
            }
        }
    }
}