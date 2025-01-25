package com.example.data_storage.file_helper

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.IOException

class FileHelper(private val context: Context) {

    fun saveToInternalStorage(fileName: String, content: String) {
        try {
            val file = File(context.filesDir, fileName)
            file.writeText(content)
            Log.d(LOG_TAG, "File saved to internal storage: ${file.absolutePath}")
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Failed to save file     to internal storage", e)
        }
    }

    fun readFromInternalStorage(fileName: String): String? {
        return try {
            val file = File(context.filesDir, fileName)
            file.readText()
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Failed to read file from internal storage", e)
            null
        }
    }

    fun saveToExternalStorage(fileName: String, content: String) {
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                try {
                    val downloadsDir = File(Environment.getExternalStorageDirectory(), "Download")
                    if (!downloadsDir.exists()) {
                        downloadsDir.mkdirs()
                    }

                    val file = File(downloadsDir, fileName)
                    file.writeText(content)
                    Log.d(LOG_TAG, "File saved to Downloads: ${file.absolutePath}")
                } catch (e: IOException) {
                    Log.e(LOG_TAG, "Failed to save file to external storage", e)
                }
            } else {
                Log.e(LOG_TAG, "External storage is not available or not writable")
            }
    }

    fun readFromExternalStorage(fileName: String): String? {
        return try {
                val downloadsDir = File(Environment.getExternalStorageDirectory(), "Download")
                val file = File(downloadsDir, fileName)
                file.readText()
            } catch (e: IOException) {
            Log.e(LOG_TAG, "Failed to read file from external storage", e)
                null
            }
    }

    private companion object {
        const val LOG_TAG = "FileHelper"
    }
}