package com.karthek.android.s.ainfo.state

import android.media.MediaCodecInfo
import android.media.MediaCodecList
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CodecsViewModel : ViewModel() {

	var loading by mutableStateOf(true)
	lateinit var decoderCodecInfoList: List<CodecInfo>
	lateinit var encoderCodecInfoList: List<CodecInfo>

	fun initCodecList() {
		var mDecoderCodecInfoList = mutableListOf<CodecInfo>()
		var mEncoderCodecInfoList = mutableListOf<CodecInfo>()
		MediaCodecList(MediaCodecList.ALL_CODECS).codecInfos.forEach { mediaCodecInfo ->
			val desc = "${mediaCodecInfo.supportedTypes.joinToString()} (${
				isHardwareAccelerated(mediaCodecInfo)
			})"
			CodecInfo(mediaCodecInfo.name, desc).let { codecInfo ->
				if (mediaCodecInfo.isEncoder) {
					mEncoderCodecInfoList.add(codecInfo)
				} else {
					mDecoderCodecInfoList.add(codecInfo)
				}
			}
		}
		decoderCodecInfoList = mDecoderCodecInfoList
		encoderCodecInfoList = mEncoderCodecInfoList
		loading = false
	}

	fun isHardwareAccelerated(mediaCodecInfo: MediaCodecInfo): String {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			return if (mediaCodecInfo.isHardwareAccelerated) "Hardware Accelerated" else "Software"
		} else {
			if (mediaCodecInfo.name.lowercase().endsWith("hw"))
				return "Hardware Accelerated"
		}
		return "NA"
	}

	data class CodecInfo(val name: String, val isHardwareAccelerated: String)

	init {
		viewModelScope.launch(Dispatchers.Default) {
			initCodecList()
		}
	}
}