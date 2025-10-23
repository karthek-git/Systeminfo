package com.karthek.android.s.ainfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.karthek.android.s.ainfo.ui.screens.MainScreen
import com.karthek.android.s.ainfo.ui.theme.AppTheme
import com.karthek.android.s.helper.ui.components.ScaleIndication

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent { ScreenContent() }
	}

	@Composable
	fun ScreenContent() {
		AppTheme {
			Surface(modifier = Modifier.fillMaxSize()) {
				CompositionLocalProvider(LocalIndication provides ScaleIndication) {
					MainScreen()
				}
			}
		}
	}
}