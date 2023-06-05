package com.compose.runtimepermissions.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.compose.runtimepermissions.PermissionStates
import com.compose.runtimepermissions.getNeededPermission


@Composable
fun PermissionsScreen() {

    val activity = LocalContext.current as Activity

    val viewModel: PermissionViewModel = viewModel()

//    val permissionDialog = remember {
//        mutableStateListOf<PermissionStates>()
//    }

    /*to launch a single permission request*/
    val singlePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted)
                viewModel.addPermissionToList(PermissionStates.RECORD_AUDIO)
        }
    )

    /*to launch multiple permission request*/
    val multiplePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            permissions.entries.forEach { entry ->
                if (!entry.value)
                    viewModel.addPermissionToList(getNeededPermission(entry.key))
//                    permissionDialog.add(getNeededPermission(entry.key))
            }
        }
    )


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            16.dp,
            Alignment.CenterVertically
        )
    ) {
        Button(
            onClick = {
                singlePermissionLauncher.launch(PermissionStates.RECORD_AUDIO.permission)
            }
        ) {
            Text(text = "Request Single Permission")
        }

        Button(
            onClick = {
                multiplePermissionLauncher.launch(
                    arrayOf(
                        PermissionStates.COARSE_LOCATION.permission,
                        PermissionStates.READ_CALENDAR.permission,
                        PermissionStates.READ_CONTACTS.permission
                    )
                )
            }
        ) {
            Text(text = "Request multiple Permissions")
        }

    }

    viewModel.permissionsDeclined.forEach { permission ->
        PermissionAlertDialog(
            title = "App Permissions",
            neededPermission = permission,
            isPermissionDeclined = !activity.shouldShowRequestPermissionRationale(permission.permission),
            onDismiss = {
                viewModel.removePermissionFromList(permission)
            },
            onOkClick = {
                viewModel.removePermissionFromList(permission)
                multiplePermissionLauncher.launch(arrayOf(permission.permission))
            }
        ) {
            viewModel.removePermissionFromList(permission)
            activity.goToAppSetting()
        }
    }
}


fun Activity.goToAppSetting() {
    val i = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )
    startActivity(i)
}


@Composable
fun PermissionAlertDialog(
    title: String,
    neededPermission: PermissionStates,
    isPermissionDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGotoAppSettingClick: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            if (isPermissionDeclined) {

                Button(
                    onClick =
                    onGotoAppSettingClick
                ) {
                    Text(text = "Open App Settings")
                }

            } else {

                Button(
                    onClick =
                    onOkClick
                ) {
                    Text(text = "Allow")
                }

            }

        },
        dismissButton = {
            Button(
                onClick =
                onDismiss
            ) {
                Text(text = "Deny")
            }
        },
        title = { Text(text = title) },
        text = { Text(text = neededPermission.permissionTextProvider(isPermissionDeclined)) }
    )

}

@Preview(showBackground = true)
@Composable
fun AlertDialogPreview() {
    MaterialTheme {
        PermissionAlertDialog(
            title = "App Permissions",
            neededPermission = getNeededPermission(android.Manifest.permission.RECORD_AUDIO),
            isPermissionDeclined = true,
            onDismiss = {},
            onOkClick = {}
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun PermissionScreenPreview() {
    MaterialTheme {
        PermissionsScreen()
    }
}