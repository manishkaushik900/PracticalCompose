package com.compose.practical.ui.mutliSelectGrid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = false)
@Composable
fun PhotoGridPreview() {
    MaterialTheme {
        PhotoGrid()
    }

}

@Composable
private fun PhotoGrid() {
    val photos by rememberSaveable { mutableStateOf(List(100) { it }) }
    val selectedIds = rememberSaveable {
        mutableStateOf(emptySet<Int>())
    }
    val inSelectionMode by remember {
        derivedStateOf { selectedIds.value.isNotEmpty() }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        items(photos, key = { it }) { id ->

            val selected = selectedIds.value.contains(id)


            ImageItem(
                selected = selected,
                inSelectionMode = inSelectionMode,
                modifier = Modifier.clickable {
                    selectedIds.value = if (selected) {
                        selectedIds.value.minus(id)
                    } else {
                        selectedIds.value.plus(id)
                    }
                }
            )


        }
    }
}

@Composable
private fun ImageItem(
    selected: Boolean, inSelectionMode: Boolean, modifier: Modifier
) {

    Surface(
        tonalElevation = 3.dp,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = modifier.aspectRatio(1f)
    ) {

        if (inSelectionMode) {
            if (selected) {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
            } else {
                Icon(imageVector = Icons.Default.RadioButtonUnchecked,contentDescription = null)
            }
        } else {

        }


    }

}