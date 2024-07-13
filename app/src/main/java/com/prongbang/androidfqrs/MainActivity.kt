package com.prongbang.androidfqrs

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.inteniquetic.fqrs.scanner.QRCodeScanner
import com.prongbang.androidfqrs.ui.theme.AndroidFqrsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidFqrsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var toast: Toast? = null
                    QRCodeScanner { qrCode ->
                        // Handle the detected QR code
                        println("Detected QR Code: $qrCode")
                        runOnUiThread {
                            toast?.cancel()
                            toast = Toast.makeText(this, qrCode, Toast.LENGTH_SHORT)
                            toast?.show()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidFqrsTheme {
        Greeting("Android")
    }
}