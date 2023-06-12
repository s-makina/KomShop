package com.komshop.data.retrofit

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.escape.Escaper
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.net.UrlEscapers
import com.komshop.log
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import okio.ByteString
import java.io.IOException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.SortedMap
import java.util.TreeMap
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random


class BasicAuthInterceptor(
    val consumerKey: String, val consumerSecret: String, val random: SecureRandom,
): Interceptor {

    private val ESCAPER: Escaper = UrlEscapers.urlFormParameterEscaper()
    private val OAUTH_CONSUMER_KEY = "oauth_consumer_key"
    private val OAUTH_NONCE = "oauth_nonce"
    private val OAUTH_SIGNATURE = "oauth_signature"
    private val OAUTH_SIGNATURE_METHOD = "oauth_signature_method"
    private val OAUTH_SIGNATURE_METHOD_VALUE = "HMAC-SHA1"
    private val OAUTH_TIMESTAMP = "oauth_timestamp"

    private val OAUTH_VERSION = "oauth_version"
    private val OAUTH_VERSION_VALUE = "1.0"


    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(signRequest(chain.request()));
    }

    @Throws(IOException::class)
    fun signRequest(request: Request): Request {
        val nonce = ByteArray(32)
        random.nextBytes(nonce)
        val oauthNonce = ByteString.of(*nonce).base64().replace("\\W".toRegex(), "")
        val oauthTimestamp = System.currentTimeMillis().toString()
        val consumerKeyValue: String = ESCAPER.escape(consumerKey)
        val parameters: SortedMap<String, String> = TreeMap()
        parameters[OAUTH_CONSUMER_KEY] = consumerKeyValue
        parameters[OAUTH_NONCE] = oauthNonce
        parameters[OAUTH_TIMESTAMP] = oauthTimestamp
        parameters[OAUTH_SIGNATURE_METHOD] = OAUTH_SIGNATURE_METHOD_VALUE
        parameters[OAUTH_VERSION] = OAUTH_VERSION_VALUE
        val url: HttpUrl = request.url
        for (i in 0 until url.querySize) {
            parameters[ESCAPER.escape(url.queryParameterName(i))] =
                ESCAPER.escape(url.queryParameterValue(i))
        }
        val requestBody = request.body
        val body = Buffer()
        requestBody?.writeTo(body)
        while (!body.exhausted()) {
            val keyEnd: Long = body.indexOf('='.code.toByte())
            check(keyEnd != -1L) { "Key with no value: " + body.readUtf8() }
            val key: String = body.readUtf8(keyEnd)
            body.skip(1) // Equals.
            val valueEnd: Long = body.indexOf('&'.code.toByte())
            val value: String = if (valueEnd == -1L) body.readUtf8() else body.readUtf8(valueEnd)
            if (valueEnd != -1L) body.skip(1) // Ampersand.
            parameters[key] = value
        }
        val base = Buffer()
        val method: String = request.method
        base.writeUtf8(method)
        base.writeByte('&'.code)
        base.writeUtf8(ESCAPER.escape(request.url.newBuilder().query(null).build().toString()))
        base.writeByte('&'.code)
        var first = true

        for ((key, value) in parameters) {
            if (!first) base.writeUtf8(ESCAPER.escape("&"))
            first = false
            base.writeUtf8(ESCAPER.escape(key))
            base.writeUtf8(ESCAPER.escape("="))
            base.writeUtf8(ESCAPER.escape(value))
        }

        val signingKey: String =
            ESCAPER.escape(consumerSecret) // + "&" + ESCAPER.escape(accessSecret);
        val keySpec = SecretKeySpec(signingKey.toByteArray(), "HmacSHA1")
        val mac: Mac
        try {
            mac = Mac.getInstance("HmacSHA1")
            mac.init(keySpec)
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException(e)
        } catch (e: InvalidKeyException) {
            throw IllegalStateException(e)
        }
        val result = mac.doFinal(base.readByteArray())
        val signature = ByteString.of(*result).base64()
        val authorization = (("OAuth "
                + OAUTH_CONSUMER_KEY + "=\"" + consumerKeyValue + "\", "
                + OAUTH_NONCE + "=\"" + oauthNonce + "\", "
                + OAUTH_SIGNATURE + "=\"" + ESCAPER.escape(signature)) + "\", "
                + OAUTH_SIGNATURE_METHOD + "=\"" + OAUTH_SIGNATURE_METHOD_VALUE + "\", "
                + OAUTH_TIMESTAMP + "=\"" + oauthTimestamp + "\", "
                + OAUTH_VERSION + "=\"" + OAUTH_VERSION_VALUE + "\"")

        log("--$authorization")
        return request.newBuilder()
            .addHeader("Authorization", authorization)
            .addHeader("Content-Type", "application/json;charset=UTF-8")
            .addHeader("Accept", "application/json;versions=1")
            .build()
    }


}