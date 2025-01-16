package pt.ipca.smartrooms.net

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request


fun fetchImage(
    lifecycleScope : LifecycleCoroutineScope,
    url: String,
    callback: (Bitmap?) -> Unit
) {
    lifecycleScope.launch {
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            if (url.contains("https")) {
                val request = Request.Builder()
                    .url(url)
                    .build()
                client.newCall(request).execute().use { response ->
                    val input = response.body!!.byteStream()
                    val bitmap = BitmapFactory.decodeStream(input)
                    withContext(Dispatchers.Main) {
                        callback(bitmap)
                    }
                }
            }
        }
    }
}