package com.inteniquetic.fqrs.decoder

import androidx.annotation.Keep

@Keep
class QRDecoder {
    external fun decodeQRCode(frameData: ByteArray, width: Int, height: Int): String

    companion object {
        init {
            System.loadLibrary("fqrs")
        }
    }
}