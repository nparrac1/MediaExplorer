package com.example.mediaexplorer.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

object ImageUtils {
    fun saveImageToInternalStorage(context: Context, imageUri: Uri): String {
        val inputStream = context.contentResolver.openInputStream(imageUri)
            ?: throw IllegalArgumentException("No se pudo abrir la imagen")
        
        // Leer la imagen como bitmap
        val bitmap = BitmapFactory.decodeStream(inputStream)
            ?: throw IllegalArgumentException("No se pudo decodificar la imagen")
        
        // Crear archivo con extensión .jpg
        val fileName = "IMG_${UUID.randomUUID()}.jpg"
        val file = File(context.filesDir, fileName)
        
        try {
            FileOutputStream(file).use { output ->
                // Comprimir y guardar como JPEG
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, output)
            }
            return file.absolutePath
        } catch (e: Exception) {
            throw IllegalArgumentException("Error al guardar la imagen: ${e.message}")
        } finally {
            bitmap.recycle()
        }
    }

    fun getImageUriFromPath(context: Context, path: String): Uri {
        val file = File(path)
        return if (file.exists()) {
            Uri.fromFile(file)
        } else {
            Uri.parse("file://$path")
        }
    }

    fun isResourceUri(uri: String): Boolean {
        return uri.startsWith("android.resource://")
    }
} 