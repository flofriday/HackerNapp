package com.example.hnapp

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hnapp.data.Story
import com.example.hnapp.restapi.fetchStory
import com.example.hnapp.restapi.fetchTopStories
import com.example.hnapp.ui.theme.HNAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HNApp()
        }
    }
}


@Composable
fun HNApp() {
    var topStoryIds by remember { mutableStateOf<List<Int>?>(null) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = Unit) {
        try {
            topStoryIds = fetchTopStories()
        } catch (e: Exception) {
            println(e.stackTraceToString())
            errorMsg = e.toString()
        }
    }

    HNAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn {
                item {
                    Text(
                        text = "Hacker Napp 💤",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(24.dp)
                    )
                }

                topStoryIds?.let { ids ->
                    items(ids) { id ->
                        StoryItem(storyId = id)
                    }
                } ?: run {
                    item {
                        Text("Loading...")
                    }
                }

            }
        }
    }
}

@Composable
fun StoryItem(storyId: Int) {
    var story by remember { mutableStateOf<Story?>(null) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = Unit) {
        try {
            story = fetchStory(storyId)
        } catch (e: Exception) {
            println(e.stackTraceToString())
            errorMsg = e.toString()
        }
    }

    Column(Modifier.padding(all = 16.dp)) {
        var title = "Loading..."
        var description = ""

        story?.let { s ->
            title = s.title

            val domain = Uri.parse(s.url).host!!
            description = "$domain ‧ ${s.score} Points"
        }

        if (errorMsg != null) {
            title = "Error: $errorMsg#"
        }

        Text(
            text = title,
            fontSize = 20.sp,
            //fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            color = MaterialTheme.colorScheme.onBackground.copy(0.5F),
        )
    }
}