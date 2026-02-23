package com.vandemunconnect.service

import io.agora.rtc2.RtcEngine
import org.webrtc.PeerConnectionFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConferenceManager @Inject constructor() {
    private var rtcEngine: RtcEngine? = null
    private var peerConnectionFactory: PeerConnectionFactory? = null

    fun initializeAgora(engine: RtcEngine) {
        rtcEngine = engine
    }

    fun initializeWebRtc(factory: PeerConnectionFactory) {
        peerConnectionFactory = factory
    }

    fun startVoiceCall(channelId: String) {
        rtcEngine?.joinChannel(null, channelId, "", 0)
    }

    fun endConference() {
        rtcEngine?.leaveChannel()
    }
}
