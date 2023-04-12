package com.noor.charginganimation.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noor.charginganimation.core.utils.PrefUtils.isSetupComplete

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSetupComplete)
            startActivity(Intent(this, MainActivity::class.java))
        else
            startActivity(Intent(this, SetupActivity::class.java))
        finish()
    }
}