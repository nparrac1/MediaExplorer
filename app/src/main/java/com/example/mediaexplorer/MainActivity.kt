package com.example.mediaexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mediaexplorer.ui.theme.navigation.NavigationGraph
import com.example.mediaexplorer.ui.theme.MediaExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MediaExplorerTheme {
                NavigationGraph()
            }
        }
    }
}