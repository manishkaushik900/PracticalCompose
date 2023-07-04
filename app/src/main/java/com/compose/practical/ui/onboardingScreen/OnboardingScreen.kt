package com.compose.practical.ui.onboardingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.practical.R

val onboardPages = listOf(
    OnboardingPage(
        imageRes = R.drawable.image1,
        title = "Welcome to Onboarding",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
    ),
    OnboardingPage(
        imageRes = R.drawable.image2,
        title = "Explore Exciting Features",
        description = "Praesent at semper est, nec consectetur justo."
    ),
    OnboardingPage(
        imageRes = R.drawable.image3,
        title = "Get Started Now",
        description = "In auctor ultrices turpis at blandit."
    )
)


@Composable
fun OnboardScreen(onboardPages: List<OnboardingPage> = emptyList()) {

    val currentPage = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        /*Image(
            painter = painterResource(id = onboardPages[currentPage.value].imageRes),
            contentDescription = null,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
*/
        ImageWithBottomFade(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            imageRes = onboardPages[currentPage.value].imageRes
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            Text(
                text = onboardPages[currentPage.value].title,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = onboardPages[currentPage.value].description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Button(
            onClick = {
                if (currentPage.value < onboardPages.size - 1) {
                    currentPage.value++
                } else {
                    // Handle onboarding completion
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        ) {
            Text(text = if (currentPage.value < onboardPages.size - 1) "Next" else "Get Started")
        }

        TabSelector(currentPage = currentPage.value) { index ->
            currentPage.value = index
        }
    }
}


@Composable
fun ImageWithBottomFade(modifier: Modifier = Modifier, imageRes: Int) {
    Box(modifier =modifier) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter)
                .graphicsLayer {
                    // Apply alpha to create the fading effect
                    alpha = 0.6f
                }
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            Pair(0.8f, Color.Transparent),
                            Pair(1f, Color.White)
                        )
                    )
                )
        )
    }
}

@Composable
fun TabSelector(currentPage: Int, onTabSelected: (Int) -> Unit) {
    TabRow(
        selectedTabIndex = currentPage,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        onboardPages.forEachIndexed { index, _ ->
            Tab(
                selected = index == currentPage,
                onClick = {
                    onTabSelected(index)
                },
                modifier = Modifier.padding(16.dp),
                content = {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = if (index == currentPage) MaterialTheme.colorScheme.onPrimary
                                else Color.LightGray,
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                }
            )
        }
    }
}

data class OnboardingPage(
    val imageRes: Int,
    val title: String,
    val description: String
)

@Preview
@Composable
fun PreviewOnboardingScreen() {
    MaterialTheme {
        Surface {
            OnboardScreen(onboardPages)
        }
    }

}