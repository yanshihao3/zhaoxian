package com.zq.zhaoxian.http.model

import java.io.Serializable

data class Weather(
    val error_code: Int,
    val reason: String,
    val result: Result
):Serializable

data class Wid(
    val day: String,
    val night: String
):Serializable


data class Result(
    val city: String,
    val future: List<Future>,
    val realtime: Realtime
):Serializable


data class Future(
    val date: String,
    val direct: String,
    val temperature: String,
    val weather: String,
    val wid: Wid
):Serializable


data class Realtime(
    val aqi: String,
    val direct: String,
    val humidity: String,
    val info: String,
    val power: String,
    val temperature: String,
    val wid: String
):Serializable
