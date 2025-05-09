package com.karthek.android.s.ainfo.state

import android.media.MediaDrm
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class DRMViewModel() : ViewModel() {
	var loading by mutableStateOf(true)
	lateinit var drmInfoList: List<DRMInfo>

	fun getDRMInfo(uuid: UUID, querySecurityLevel: Boolean = false): DRMInfo {
		val mediaDRM = MediaDrm(uuid)
		var securityLevel = "NA"
		var deviceID = "NA"
		var lowestConnectedHDCPLevel = "NA"
		var maxHDCPLevel = "NA"
		var currentSessionCount = "NA"
		var maxSessionCount = "NA"

		try {
			if (querySecurityLevel) {
				securityLevel = mediaDRM.getPropertyString("securityLevel")
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
			lowestConnectedHDCPLevel = hdcpLevelMap.getOrElse(mediaDRM.connectedHdcpLevel, { "NA" })
			maxHDCPLevel = hdcpLevelMap.getOrElse(mediaDRM.maxHdcpLevel, { "NA" })
			currentSessionCount = mediaDRM.openSessionCount.toString()
			maxSessionCount = mediaDRM.maxSessionCount.toString()
		}

		val supportedCryptoSessionOprs =
			mediaDRM.getPropertyString(MediaDrm.PROPERTY_ALGORITHMS).run {
				if (this.isEmpty()) "None" else this.replace(",", ", ")
			}

		val drmInfo = DRMInfo(
			vendor = mediaDRM.getPropertyString(MediaDrm.PROPERTY_VENDOR),
			version = mediaDRM.getPropertyString(MediaDrm.PROPERTY_VERSION),
			securityLevel = securityLevel,
			description = mediaDRM.getPropertyString(MediaDrm.PROPERTY_DESCRIPTION),
			deviceID = deviceID,
			UUID = uuid.toString(),
			lowestConnectedHDCPLevel = lowestConnectedHDCPLevel,
			maxHDCPLevel = maxHDCPLevel,
			currentSessionCount = currentSessionCount,
			maxSessionCount = maxSessionCount,
			supportedCryptoSessionOprs = supportedCryptoSessionOprs
		)
		mediaDRM.close()
		return drmInfo
	}

	fun initDRMInfo() {
		val mDRMInfoList = mutableListOf<DRMInfo>()
		if (MediaDrm.isCryptoSchemeSupported(WIDEVINE_UUID)) {
			mDRMInfoList.add(getDRMInfo(WIDEVINE_UUID, true))
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			val cryptoSchemes = MediaDrm.getSupportedCryptoSchemes()
			Log.i("DRMViewModel", "initDRMInfo: $cryptoSchemes")
			mDRMInfoList.addAll(cryptoSchemes.map { getDRMInfo(it) })
		}
		listOf(PLAYREADY_UUID, PRIMETIME_UUID, FAIRPLAY_UUID, MARLIN_UUID).forEach { uuid ->
			if (MediaDrm.isCryptoSchemeSupported(uuid)) {
				mDRMInfoList.add(getDRMInfo(uuid))
			}
		}
		drmInfoList = mDRMInfoList
		loading = false
	}

	init {
		viewModelScope.launch(Dispatchers.Default) {
			initDRMInfo()
		}
	}
}

data class DRMInfo(
	val vendor: String,
	val version: String,
	val securityLevel: String,
	val description: String,
	val UUID: String,
	val deviceID: String,
	val lowestConnectedHDCPLevel: String,
	val maxHDCPLevel: String,
	val currentSessionCount: String,
	val maxSessionCount: String,
	val supportedCryptoSessionOprs: String,
)

@RequiresApi(Build.VERSION_CODES.P)
val hdcpLevelMap = mapOf(
	MediaDrm.HDCP_LEVEL_UNKNOWN to "Unknown",
	MediaDrm.HDCP_NONE to "None",
	MediaDrm.HDCP_NO_DIGITAL_OUTPUT to "No Digital Output",
	MediaDrm.HDCP_V1 to "1.0",
	MediaDrm.HDCP_V2 to "2.0",
	MediaDrm.HDCP_V2_1 to "2.1",
	MediaDrm.HDCP_V2_2 to "2.2",
	MediaDrm.HDCP_V2_3 to "2.3",
)

val WIDEVINE_UUID: UUID = UUID.fromString("edef8ba9-79d6-4ace-a3c8-27dcd51d21ed")
val PLAYREADY_UUID: UUID = UUID.fromString("9a04f079-9840-4286-ab92-e65be0885f95")
val PRIMETIME_UUID: UUID = UUID.fromString("f239e769-efa3-4850-9c16-a903c6932efb")
val FAIRPLAY_UUID: UUID = UUID.fromString("94ce86fb-07ff-4f43-adb8-93d2fa968ca2")
val MARLIN_UUID: UUID = UUID.fromString("5e629af5-38da-4063-8977-97ffbd9902d4")
