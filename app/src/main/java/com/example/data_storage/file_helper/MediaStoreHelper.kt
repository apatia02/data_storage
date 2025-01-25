package com.example.data_storage.file_helper

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.IOException

class MediaStoreHelper(private val context: Context) {

    fun saveToSharedStorage(fileName: String, content: String) {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName)
            put(MediaStore.Files.FileColumns.MIME_TYPE, "text/plain")
            put(
                MediaStore.Files.FileColumns.RELATIVE_PATH,
                Environment.DIRECTORY_DOCUMENTS + "/MyApp"
            )
        }

        val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

        if (uri != null) {
            try {
                resolver.openOutputStream(uri)?.use { outputStream ->
                    outputStream.write(content.toByteArray())
                    Log.d(LOG_TAG, "File saved to external storage: $uri")
                }
            } catch (e: IOException) {
                Log.e(LOG_TAG, "Failed to save file to MediaStore", e)
            }
        } else {
            Log.e(LOG_TAG, "Failed to create URI in MediaStore")
        }
    }

    fun readFromSharedStorage(fileName: String): String? {
        val resolver = context.contentResolver
        val projection =
            arrayOf(MediaStore.Files.FileColumns.DISPLAY_NAME, MediaStore.Files.FileColumns.DATA)
        val selection = "${MediaStore.Files.FileColumns.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(fileName)

        val cursor = resolver.query(
            MediaStore.Files.getContentUri("external"),
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex(MediaStore.Files.FileColumns.DATA)
                val filePath = it.getString(columnIndex)
                Log.d(LOG_TAG, "File path from MediaStore: $filePath")
                return File(filePath).readText() // Читаем содержимое файла
            }
        }

        Log.e(LOG_TAG, "File not found in MediaStore")
        return null
    }

    private companion object {
        const val LOG_TAG = "MediaStoreHelper"
    }
}