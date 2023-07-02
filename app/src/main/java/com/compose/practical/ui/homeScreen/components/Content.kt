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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.practical.ui.homeScreen.model.Destinations

@Composable
fun ContentArea(
    modifier: Modifier = Modifier,
    destinations: Destinations
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        destinations.icon?.let { icon ->
            Icon(
                modifier = Modifier.size(80.dp),
                imageVector = icon,
                contentDescription = destinations.path
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Text(text = destinations.path, fontSize = 18.sp)
    }

}