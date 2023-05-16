package com.komshop.util

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.komshop.log

class CodeGeneratorHelper {
    companion object {
        fun generateBarcode(content: String?, width: Int = 1000, height: Int = 220): Bitmap? {
            var bitmap: Bitmap? = null
            try {
                val barcodeEncoder = BarcodeEncoder()
                bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.CODABAR, width, height)
            } catch (e: Exception) {
                log(e.message!!)
            }
            return bitmap
        }


        fun generateQRCode(content: String?, width: Int = 300, height: Int = 300): Bitmap? {
            var bitmap: Bitmap? = null
            try {
                val barcodeEncoder = BarcodeEncoder()
                bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, width, height)
            } catch (e: Exception) {
                log(e.message!!)
            }
            return bitmap
        }

    }
}