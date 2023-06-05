package com.compose.runtimepermissions.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.compose.runtimepermissions.PermissionStates

class PermissionViewModel : ViewModel() {

    val permissionsDeclined = mutableStateListOf<PermissionStates>()

    fun removePermissionFromList(permission: PermissionStates) {
        permissionsDeclined.remove(permission)
    }

    fun addPermissionToList(
        permission: PermissionStates
    ) {
        permissionsDeclined.add(permission)
    }

}