package com.karthek.android.s.ainfo.state

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.Display
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

class DisplayViewModel(val applicationContext: Context) : ViewModel() {
	var loading by mutableStateOf(true)
	var resolution = ""
	var supportedRefreshRates = ""
	var hdrCaps = ""
}

@RequiresApi(Build.VERSION_CODES.N)
val hdrMap = mapOf(
	Display.HdrCapabilities.HDR_TYPE_HDR10 to "HDR10",
	Display.HdrCapabilities.HDR_TYPE_HDR10_PLUS to "HDR10+",
	Display.HdrCapabilities.HDR_TYPE_DOLBY_VISION to "Dolby Vision",
	Display.HdrCapabilities.HDR_TYPE_HLG to "HLG"
)

fun getDisplayInfo(context: Context): DisplayInfo {
	val display = getPhysicalDisplaySize(context) ?: return DisplayInfo("", "", "")
	val size = Point()
	@Suppress("DEPRECATION")
	display.getRealSize(size)
	val resolution = "${size.x} x ${size.y}"
	val supportedRefreshRates = display.supportedRefreshRates.map { it.roundToInt() }.joinToString()
	var hdrCaps = "NA"
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
		hdrCaps =
			display.hdrCapabilities.supportedHdrTypes.joinToString { hdrMap[it].toString() }.run {
				if (isEmpty()) "None" else this
			}
	}
	return DisplayInfo(resolution, supportedRefreshRates, hdrCaps)
}

fun getPhysicalDisplaySize(context: Context): Display? {
	val windowManager =
		context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
	val display: Display? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
		context.display
	} else {
		@Suppress("DEPRECATION")
		windowManager.defaultDisplay
	}
	return display
}


data class DisplayInfo(
	val resolution: String,
	val supportedRefreshRates: String,
	val hdrCaps: String,
)

