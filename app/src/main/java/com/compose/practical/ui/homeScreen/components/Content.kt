package com.compose.practical.ui.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.practical.ui.homeScreen.Tags.TAG_CONTENT_ICON
import com.compose.practical.ui.homeScreen.Tags.TAG_CONTENT_TITLE
import com.compose.practical.ui.homeScreen.model.Destinations

@Composable
fun ContentArea(
    modifier: Modifier = Modifier,
    destinations: Destinations
) {
    Column(
        modifier = modifier.testTag(destinations.path),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        destinations.icon?.let { icon ->
            Icon(
                modifier = Modifier.size(80.dp).testTag(TAG_CONTENT_ICON),
                imageVector = icon,
                contentDescription = destinations.title
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Text( modifier = Modifier.testTag(TAG_CONTENT_TITLE),text = destinations.title, fontSize = 18.sp)
    }

}