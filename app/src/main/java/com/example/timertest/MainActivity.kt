package com.example.timertest

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    private var seconds=0
    private var isRunning=false
    private var wasRunning=false
    private lateinit var timerTextView: TextView
    private var handler = Handler()
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timerTextView= findViewById(R.id.timerTextView)
        if(savedInstanceState!= null){
        seconds= savedInstanceState.getInt("seconds")
        isRunning= savedInstanceState.getBoolean("isRunning")
            wasRunning= savedInstanceState.getBoolean("wasRunning")
        }
        runTimer()
    }

    override fun onPause() {
        super.onPause()
        wasRunning=isRunning
        isRunning=false
    }

    override fun onResume() {
        super.onResume()
        isRunning=wasRunning

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", seconds)
        outState.putBoolean("isRunning", isRunning)
        outState.putBoolean("wasRunning", wasRunning)
    }

    private fun runTimer() {
        handler.apply {
            runnable = Runnable {
                    val hours = seconds / 3600
                    val minutes = (seconds % 3600) / 60
                    val secs = seconds % 60

                    val time =
                        String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs)
                    timerTextView.text = time

                    if (isRunning) {
                        seconds++
                    }
                    handler.postDelayed(runnable, 1000)
            }
         post(runnable)
        }
    }

    fun onClickStartTimer(view: View) {
        isRunning=true
    }

    fun onClickPauseTimer(view: View) {
        isRunning=false
    }

    fun onClickResetTimer(view: View) {
        isRunning=false
        seconds=0
    }
}