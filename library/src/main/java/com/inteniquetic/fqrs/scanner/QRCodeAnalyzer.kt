package com.inteniquetic.fqrs.scanner

import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.inteniquetic.fqrs.decoder.QRDecoder
import java.io.ByteArrayOutputStream

class QRCodeAnalyzer(private val onQRCodeDetected: (String) -> Unit) : ImageAnalysis.Analyzer {
    private val qrDecoder = QRDecoder()

    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val byteArray = inputImageToByteArray(mediaImage)

            val decodedData = qrDecoder.decodeQRCode(byteArray, mediaImage.width, mediaImage.height)

            if (decodedData.isNotEmpty()) {
                onQRCodeDetected(decodedData)
            }
        }
        imageProxy.close()
    }

    private fun inputImageToByteArray(mediaImage: Image): ByteArray {
        val planes = mediaImage.planes
        val yBuffer = planes[0].buffer
        val uBuffer = planes[1].buffer
        val vBuffer = planes[2].buffer

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, mediaImage.width, mediaImage.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 100, out)
        return out.toByteArray()
    }
}