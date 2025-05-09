package com.karthek.android.s.ainfo.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContentLoading(modifier: Modifier = Modifier) {
	CircularProgressIndicator(
		modifier = modifier
			.fillMaxWidth()
			.padding(top = 48.dp)
			.size(64.dp)
			.wrapContentSize(Alignment.Center),
		strokeWidth = 4.dp
	)
}