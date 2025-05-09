package com.karthek.android.s.ainfo.ui.screens

import android.os.Build
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.DeveloperBoard
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.karthek.android.s.ainfo.R
import com.karthek.android.s.ainfo.state.DRMViewModel
import com.karthek.android.s.ainfo.state.SOCViewModel
import com.karthek.android.s.ainfo.ui.components.ContentLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
	val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(text = stringResource(id = R.string.app_name)) },
				scrollBehavior = scrollBehavior
			)
		},
		modifier = Modifier
			.fillMaxSize()
			.nestedScroll(scrollBehavior.nestedScrollConnection)
	) { paddingValues ->
		val navController = rememberNavController()
		NavHost(
			navController = navController,
			startDestination = "root",
			enterTransition = {
				fadeIn(animationSpec = tween(300, easing = LinearEasing)) +
						slideIntoContainer(
							animationSpec = tween(300, easing = EaseIn),
							towards = AnimatedContentTransitionScope.SlideDirection.Start
						)
			},
			exitTransition = {
				fadeOut(animationSpec = tween(300, easing = LinearEasing)) +
						slideOutOfContainer(
							animationSpec = tween(300, easing = EaseOut),
							towards = AnimatedContentTransitionScope.SlideDirection.End
						)
			}
		) {
			composable(
				route = "root",
				enterTransition = { EnterTransition.None },
				exitTransition = { ExitTransition.None }) {
				MainScreenContent(navController = navController, paddingValues = paddingValues)
			}
			composable(route = "root/build_info") {
				BuildScreen(paddingValues = paddingValues)
			}
			composable(route = "root/soc") {
				SOCScreen(paddingValues = paddingValues)
			}
			composable(route = "root/drm") {
				DRMScreen(paddingValues = paddingValues)
			}
		}
	}
}

@Composable
fun MainScreenContent(navController: NavController, paddingValues: PaddingValues) {
	CardComponent(modifier = Modifier.padding(paddingValues)) {
		ListComponent {
			MainListItem(
				text = stringResource(R.string.system),
				icon = Icons.Outlined.Android
			) { navController.navigate("root/build_info") }
			HorizontalDivider()
			MainListItem(
				text = stringResource(R.string.soc),
				icon = Icons.Outlined.DeveloperBoard
			) { navController.navigate("root/soc") }
			HorizontalDivider()
			MainListItem(
				text = stringResource(R.string.drm),
				icon = Icons.Outlined.Security
			) { navController.navigate("root/drm") }
		}
	}
}

@Composable
fun ListComponent(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
	Column(modifier = modifier.verticalScroll(rememberScrollState()), content = content)
}

@Composable
fun CardComponent(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
	Card(
		shape = RoundedCornerShape(8.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp) // todo(temp fix for compose m3 car elevation color issue)
		),
		elevation = CardDefaults.cardElevation(4.dp),
		modifier = modifier
			.padding(horizontal = 16.dp, vertical = 8.dp),
		content = content
	)
}

@Composable
fun MainListItem(text: String, icon: ImageVector, callback: () -> Unit) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clickable(onClick = callback)
			.padding(16.dp)
	) {
		Icon(
			imageVector = icon,
			contentDescription = text,
			modifier = Modifier.padding(end = 16.dp)
		)
		Text(
			text = text,
			style = MaterialTheme.typography.titleMedium,
			modifier = Modifier.weight(1f)
		)
		Icon(Icons.Outlined.ChevronRight, contentDescription = "")
	}
}

@Composable
fun InfoListItem(key: String = "key", value: String = "NA") {
	ListItem(
		modifier = Modifier,
		headlineContent = { Text(key, fontWeight = FontWeight.SemiBold, modifier = Modifier) },
		supportingContent = { Text(value, modifier = Modifier) }
	)
}

@Composable
fun BuildScreen(paddingValues: PaddingValues) {
	ListComponent(
		modifier = Modifier.padding(paddingValues)
	) {
		InfoListItem(stringResource(R.string.brand), Build.BRAND)
		InfoListItem(stringResource(R.string.device), Build.DEVICE)
		InfoListItem(stringResource(R.string.manufacturer), Build.MANUFACTURER)
		InfoListItem(stringResource(R.string.model), Build.MODEL)
		InfoListItem(stringResource(R.string.board), Build.BOARD)
		InfoListItem(stringResource(R.string.hardware), Build.HARDWARE)
		InfoListItem(stringResource(R.string.product), Build.PRODUCT)
	}
}


@Composable
fun SOCScreen(paddingValues: PaddingValues, viewModel: SOCViewModel = viewModel()) {
	ListComponent(modifier = Modifier.padding(paddingValues)) {
		InfoListItem(stringResource(R.string.soc_manufacturer), viewModel.manufacturer)
		InfoListItem(stringResource(R.string.model), viewModel.model)
		InfoListItem(stringResource(R.string.no_cores), viewModel.nCores)
		InfoListItem(stringResource(R.string.supported_abis), viewModel.supportedABIs)
		InfoListItem(stringResource(R.string.supported_32bit_abis), viewModel.supported32BitABIs)
		InfoListItem(stringResource(R.string.supported_64bit_abis), viewModel.supported64bitABIs)
		InfoListItem(stringResource(R.string.gpu), viewModel.gpu)
		InfoListItem(stringResource(R.string.isp), viewModel.isp)
		InfoListItem(stringResource(R.string.dsp), viewModel.dsp)
		InfoListItem(stringResource(R.string.radio_version), viewModel.radioVersion)
	}
}

@Composable
fun DRMScreen(paddingValues: PaddingValues, drmViewModel: DRMViewModel = viewModel()) {
	Column(
		modifier = Modifier
			.padding(paddingValues)
			.verticalScroll(rememberScrollState())
	) {
		if (drmViewModel.loading) {
			ContentLoading()
		} else {
			drmViewModel.drmInfoList.forEach {
				CardComponent {
					CompositionLocalProvider(LocalAbsoluteTonalElevation provides 4.dp) {
						InfoListItem("Vendor", it.vendor)
						InfoListItem("Version", it.version)
						if (it.securityLevel != "NA") {
							InfoListItem("Security Level", it.securityLevel)
						}
						InfoListItem("Description", it.description)
						InfoListItem("Crypto Scheme UUID", it.UUID)
						InfoListItem("Lowest Connected HDCP Level", it.lowestConnectedHDCPLevel)
						InfoListItem("Maximum HDCP Level", it.maxHDCPLevel)
						InfoListItem("Current Sessions", it.currentSessionCount)
						InfoListItem("Maximum Sessions", it.maxSessionCount)
						InfoListItem(
							"Supported CryptoSession Algorithms",
							it.supportedCryptoSessionOprs
						)
					}
				}
			}
		}
	}
}
