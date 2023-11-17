package com.samkt.filmio.featureSettings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val isDarkTheme = viewModel.theme.collectAsState().value
    SettingsScreenContent(
        onSignUpClicked = viewModel::onSignUpClicked,
        onDismissRequest = viewModel::onDismissDialog,
        openDialog = viewModel.openDialog,
        isDarkTheme = isDarkTheme ?: false,
        setDarkTheme = viewModel::setDarkTheme
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    onSignUpClicked: () -> Unit,
    onDismissRequest: () -> Unit,
    openDialog: Boolean,
    isDarkTheme: Boolean,
    setDarkTheme: (Boolean) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "My Profile",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Sign in to synchronize your movies and series",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Button(
                        shape = RoundedCornerShape(8.dp),
                        onClick = onSignUpClicked
                    ) {
                        Text(text = "Continue")
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Enable Dark Theme")
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = setDarkTheme
                )
            }
        }

        if (openDialog) {
            AlertDialog(
                onDismissRequest = onDismissRequest,
                title = { Text(text = "Coming soon") },
                text = {
                    Text(text = "This feature is coming soon.Stay on the look out for updates.")
                },
                confirmButton = {
                    TextButton(
                        onClick = onDismissRequest
                    ) {
                        Text(text = "OKAY")
                    }
                }
            )
        }
    }
}
