package com.compose.runtimepermissions

enum class PermissionStates(
    val permission:String,
    val title:String,
    val description:String,
    val deniedDescription:String
) {
    COARSE_LOCATION(
        permission = android.Manifest.permission.ACCESS_COARSE_LOCATION,
        title = "Approximate Location Permission",
        description = "This permission is needed to get your approximate location. Please grant the permission.",
        deniedDescription = "This permission is needed to get your approximate location. Please grant the permission in app settings."
    ),
    READ_CALENDAR(
        permission = android.Manifest.permission.READ_CALENDAR,
        title = "Read Calendar Permission",
        description = "This permission is needed to read your calendar. Please grant the permission.",
        deniedDescription = "This permission is needed to read your calendar. Please grant the permission in app settings."
    ),
    READ_CONTACTS(
        permission = android.Manifest.permission.READ_CONTACTS,
        title = "Read Contacts Permission",
        description = "This permission is needed to read your contacts. Please grant the permission.",
        deniedDescription = "This permission is needed to read your contacts. Please grant the permission in app settings."
    ),
    RECORD_AUDIO(
        permission = android.Manifest.permission.RECORD_AUDIO,
        title = "Record Audio Permission",
        description = "This permission is needed to record audio. Please grant the permission.",
        deniedDescription = "This permission is needed to record audio. Please grant the permission in app settings."
    );

    fun permissionTextProvider(isPermanentDenied: Boolean): String {
        return if (isPermanentDenied) this.deniedDescription else this.description
    }
}

fun getNeededPermission(permission: String): PermissionStates {
    return PermissionStates.values().find { it.permission == permission } ?: throw IllegalArgumentException("Permission $permission is not supported")
}