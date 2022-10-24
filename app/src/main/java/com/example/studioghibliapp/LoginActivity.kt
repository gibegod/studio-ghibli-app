package com.example.studioghibliapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.studioghibliapp.models.User

class LoginActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var btnLogin: Button
    lateinit var tvSignIn: TextView
    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var cbRememberUser: CheckBox
    private var channelId = "Log in"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sp = applicationContext.getSharedPreferences(Consts.SP_CREDENTIALS, MODE_PRIVATE)
        val savedUser = sp.getString(Consts.USER, null)
        val savedPassword = sp.getString(Consts.PASSWORD, null)
        if (savedUser != null && savedPassword != null) {
            startMainActivity(savedUser)
        }

        initialSetup()

        handleOnClickTvSignin()
        handleOnClickBtnLogin()

        createChannel(channelId, "Nofiticación de que el usuario activó la opción \"Recordar Usuario\"")
    }

    private fun initialSetup() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Inicio de sesión"

        btnLogin = findViewById(R.id.btn_login)
        tvSignIn = findViewById(R.id.tv_sign_in)
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        cbRememberUser = findViewById(R.id.cb_remember_user)
    }

    private fun handleOnClickTvSignin() {
        tvSignIn.setOnClickListener {
            val signInActivity = Intent(this@LoginActivity, SignInActivity::class.java)
            startActivity(signInActivity)
        }
    }

    private fun handleOnClickBtnLogin() {
        btnLogin.setOnClickListener(View.OnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this@LoginActivity,
                    "Complete ambos campos para iniciar sesión",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }
            val user = User()
            user.username = username
            user.password = password
            try {
                val logInUser = UserManager.getInstance(this@LoginActivity).logInUser(user)

                if (!logInUser) {
                    Toast.makeText(
                        this@LoginActivity,
                        "El usuario y/o la contraseña son incorrectos",
                        Toast.LENGTH_LONG
                    ).show()
                    return@OnClickListener
                }

                if (cbRememberUser.isChecked) {
                    val sp = applicationContext.getSharedPreferences(Consts.SP_CREDENTIALS, MODE_PRIVATE)
                    val spEditor = sp.edit()

                    spEditor.putString(Consts.USER, username)
                    spEditor.putString(Consts.PASSWORD, password)
                    spEditor.apply()

                    val notification = createNotification()
                    NotificationManagerCompat.from(this).notify(1, notification)
                }

                startMainActivity(username)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    private fun startMainActivity(username: String) {
        val mainActivity = Intent(this@LoginActivity, MainActivity::class.java)
        mainActivity.putExtra("Username", username)
        startActivity(mainActivity)
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(channelName: String, channelDescription: String){
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )
            channel.description = channelDescription
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, channelId)
            .setContentText("Se activó la funcionalidad de \"Recordar Usuario\"")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.ic_baseline_save_24)
            .build()
    }
}