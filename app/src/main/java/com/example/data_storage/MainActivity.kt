package com.example.data_storage

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.data_storage.data_store.DataStoreHelper
import com.example.data_storage.file_helper.FileHelper
import com.example.data_storage.file_helper.MediaStoreHelper
import com.example.data_storage.model.User
import com.example.data_storage.shared.SharedPreferencesHelper
import com.example.data_storage.test.UserDaoTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var dataStoreHelper: DataStoreHelper
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var fileHelper: FileHelper
    private lateinit var mediaStoreHelper: MediaStoreHelper
    private lateinit var userDaoTest: UserDaoTest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initHelpers()
        initStoresWork()
        initFileHelperWorks()
        testDaoOperations()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("MainActivity", "Permission granted")
                performFileOperations()
            } else {
                Log.e("MainActivity", "Permission denied")
            }
        }
    }

    private fun initHelpers() {
        dataStoreHelper = DataStoreHelper(this)
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        fileHelper = FileHelper(this)
        mediaStoreHelper = MediaStoreHelper(this)
        userDaoTest = UserDaoTest(this)
    }

    private fun initStoresWork() {
        val user = User("John Doe", 28, "john.doe@example.com")
        lifecycleScope.launch {
            dataStoreHelper.saveUser(user)
            Log.d("DataStore", "User saved: $user")
            val savedDataStoreUser = dataStoreHelper.getUser()
            Log.d("DataStore", "Saved User: $savedDataStoreUser")
        }
        sharedPreferencesHelper.saveUser(user)
        Log.d("Shared", "User saved: $user")
        val savedSharedUser = sharedPreferencesHelper.getUser()
        Log.d("Shared", "Shared User: $savedSharedUser")
    }

    private fun initFileHelperWorks() {
        val internalFileName = "internal_file.txt"
        val internalContent = "Hello from Internal Storage"
        fileHelper.saveToInternalStorage(internalFileName, internalContent)
        val internalReadContent = fileHelper.readFromInternalStorage(internalFileName)
        Log.d("MainActivity", "Internal File Content: $internalReadContent")

        if (!checkStoragePermissions()) {
            requestStoragePermissions()
        } else {
            performFileOperations()
        }
    }

    private fun initMediaStoreHelperWorks() {
        val fileName = "shared_file.txt"
        val content = "Hello from Shared Storage"
        lifecycleScope.launch {
            mediaStoreHelper.saveToSharedStorage(fileName, content)
            val retrievedContent = mediaStoreHelper.readFromSharedStorage(fileName)
            Log.d("MainActivity", "Content from MediaStore: $retrievedContent")
        }
    }

    private fun performFileOperations() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val externalFileName = "external_file.txt"
            val externalContent = "Hello from External Storage"
            fileHelper.saveToExternalStorage(externalFileName, externalContent)
            val externalReadContent = fileHelper.readFromExternalStorage(externalFileName)
            Log.d("MainActivity", "External File Content: $externalReadContent")
        } else {
            initMediaStoreHelperWorks()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun checkStoragePermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun requestStoragePermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            REQUEST_STORAGE_PERMISSION
        )
    }

    private fun testDaoOperations() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                userDaoTest.runTest()
            }
        }
    }

    private companion object {
        const val REQUEST_STORAGE_PERMISSION = 100
    }
}