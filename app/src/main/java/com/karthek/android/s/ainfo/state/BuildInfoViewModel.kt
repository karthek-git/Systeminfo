package com.karthek.android.s.ainfo.state

import android.os.Build
import androidx.lifecycle.ViewModel

class BuildInfoViewModel : ViewModel() {
	val brand = Build.BRAND
	val device = Build.DEVICE
	val manufacturer = Build.MANUFACTURER
	val model = Build.MODEL
	val board = Build.BOARD
	val hardware = Build.HARDWARE
	val product = Build.PRODUCT
}