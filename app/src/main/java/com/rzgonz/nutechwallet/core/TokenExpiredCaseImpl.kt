package com.rzgonz.nutechwallet.core

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.rzgonz.nutechwallet.modules.login.LoginActivity

/**
 * Created by rzgonz on 12/02/23.
 *
 */
class TokenExpiredCaseImpl(
    private val context: AppCompatActivity
) : TokenExpiredCase {
    override fun forceToLogin() {
        val LAUNCH_SECOND_ACTIVITY = 1
        val i =  Intent(context, LoginActivity::class.java);
        context.startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
    }

}