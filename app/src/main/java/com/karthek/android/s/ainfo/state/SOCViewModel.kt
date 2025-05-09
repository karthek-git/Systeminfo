package com.karthek.android.s.ainfo.state

import android.os.Build
import androidx.lifecycle.ViewModel
import com.karthek.android.s.ainfo.SApplication.props

class SOCViewModel : ViewModel() {
	val manufacturer: String = props.socManufacturer
	val model: String = props.socModel
	val nCores = Runtime.getRuntime().availableProcessors().toString()
	val supportedABIs = Build.SUPPORTED_ABIS.joinInLines()
	val supported32BitABIs = Build.SUPPORTED_32_BIT_ABIS.joinInLines()
	val supported64bitABIs = Build.SUPPORTED_64_BIT_ABIS.joinInLines()
	val radioVersion: String = Build.getRadioVersion()
	val gpu = "NA"
	val isp = "NA"
	val dsp = "NA"
}

fun Array<String>.joinInLines() = joinToString("\n\n")