package com.zfhrp6.androidapp1

data class BtId(val ssid: String)
data class LatLong(val longitude: Float, val latitude: Float)
data class Req(val latLong: LatLong, val dateTimeStr: String, val zoneIdStr: String, val Ids: List<BtId>)

