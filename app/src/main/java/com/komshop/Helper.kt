package com.komshop

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.text.DecimalFormat
import java.text.NumberFormat


fun log(content: Any?) {
    Log.i("BETH", "$content")
}

fun toast(context: Context, message: String?) {
    Toast.makeText(context, "$message", Toast.LENGTH_LONG).show()
}

inline fun <reified T> parseObject(data: JsonObject, typeToken: Type): T {
    val gson = GsonBuilder().create()
    return gson.fromJson(data, typeToken)
}

inline fun <reified T> parseObject(data: Any, typeToken: Type): T {
    val json: String = Gson().toJson(data)
    val gson = GsonBuilder().create()
    return gson.fromJson(json, typeToken)
}

inline fun <reified T> fromJson(json: String, typeToken: Type): T {
    val gson = GsonBuilder().create()
    return gson.fromJson(json, typeToken)
}

fun toJson(obj: Any): String {
    return Gson().toJson(obj)
}

//        inline fun <reified T> toArray(json: Any) = Gson().fromJson<T>(Gson().toJson(json), object : TypeToken<T>() {}.type)
inline fun <reified T> Gson.toArray(json: Any) =
    fromJson<T>(Gson().toJson(json), object : TypeToken<T>() {}.type)

fun vibrate(context: Context) {
    val v = (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
    // Vibrate for 500 milliseconds
    // Vibrate for 500 milliseconds
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        v.vibrate(
            VibrationEffect.createOneShot(
                500,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    } else {
        v.vibrate(500)
    }
}

fun processErrors(jsonObject: JsonObject?): String {
    var formattedError = ""
    jsonObject?.keySet()?.forEach { key ->
        var errString = ""
        val errors = jsonObject.getAsJsonArray(key)
        errors.forEach {
            errString += it.asString
        }
        formattedError += "$errString \n"
    }
    return formattedError
}

fun formatMoney(num: Double): String {
    return try {
        val formatter: NumberFormat = DecimalFormat("#,###.##")
        formatter.format(num)
    } catch (e: Exception) {
        "0.0"
    }
}

fun currentBidFormat(currentBid: Double): String {
    return if (currentBid == 0.0) "Be the first one to bid" else "MK "+ formatMoney(currentBid)
}

fun toInt(s: String): Int {
    return try {
        if (s.isEmpty()) 0 else s.toInt()
    } catch (e: Exception) {
        0
    }

}
fun sendWapMsg(context: Context, phoneNumber: String, message: String) {
    // on below line we are starting activity
    // to send the sms from whatsapp.
    context.startActivity(
        // on below line we are opening the intent.
        Intent(
            // on below line we are calling
            // uri to parse the data
            Intent.ACTION_VIEW,
            Uri.parse(
                // on below line we are passing uri,
                // message and whats app phone number.
                java.lang.String.format(
                    "https://api.whatsapp.com/send?phone=%s&text=%s",
                    phoneNumber,
                    message
                )
            )
        )
    )
}
