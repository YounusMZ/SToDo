package com.example.stodo

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.example.stodo.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                startActivity(
                    Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                )
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    "WRITE_EXTERNAL_STORAGE"
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val permissionsRequired = Array<String>(1) {
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                }

                shouldShowRequestPermissionRationale("WRITE_EXTERNAL_STORAGE")
                requestPermissions(
                    permissionsRequired,
                    0
                )
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}
