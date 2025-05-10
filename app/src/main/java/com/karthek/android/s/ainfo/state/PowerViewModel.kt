package com.karthek.android.s.ainfo.state

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PowerViewModel(val applicationContext: Context) : ViewModel() {

	var loading by mutableStateOf(true)
	lateinit var batteryInfo: BatteryInfo

	fun initBatteryInfo() {
		val batteryManager =
			applicationContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

		val batteryHealth = getBatteryHealth(applicationContext)

		val totalCapacity =
			batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER)
				.toString().slice(0..3)

		val currentCapacity =
			batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)

		val status = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val batteryStatus = batteryManager.getIntProperty(
				BatteryManager.BATTERY_PROPERTY_STATUS
			)
			when (batteryStatus) {
				BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
				BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
				BatteryManager.BATTERY_STATUS_FULL -> "Battery Full"
				BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not Charging"
				else -> "Unknown"
			}
		} else {
			"NA"
		}

		val batteryLevel: Int =
			batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

		// A more reliable way to get battery level percentage
		val iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
		val batteryIntent = applicationContext.registerReceiver(null, iFilter)
		val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
		val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
		val percentage = if (level != -1 && scale != -1) {
			(level.toFloat() / scale.toFloat() * 100.0f).toInt()
		} else {
			-1
		}

		batteryInfo = BatteryInfo(
			health = batteryHealth,
			currentCapacitymAh = "$currentCapacity mAh",
			totalCapacitymAh = "$totalCapacity mAh",
			percentage = "$percentage %",
			status = status
		)
		loading = false
	}

	fun getBatteryHealth(context: Context): String {
		val iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
		val batteryIntent = context.registerReceiver(null, iFilter)

		val health =
			batteryIntent?.getIntExtra(
				BatteryManager.EXTRA_HEALTH,
				BatteryManager.BATTERY_HEALTH_UNKNOWN
			)
				?: BatteryManager.BATTERY_HEALTH_UNKNOWN

		return when (health) {
			BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
			BatteryManager.BATTERY_HEALTH_COLD -> "Cold"
			BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
			BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheat"
			BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Over voltage"
			BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> "Unspecified failure"
			else -> "Unknown"
		}
	}

	data class BatteryInfo(
		val health: String,
		val currentCapacitymAh: String, // -1 if not available
		val totalCapacitymAh: String,    // -1 if not available, often estimates
		val percentage: String,               // -1 if not available
		val status: String,
	)

	init {
		viewModelScope.launch(Dispatchers.Default) {
			initBatteryInfo()
		}
	}

}