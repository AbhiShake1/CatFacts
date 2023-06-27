package com.app.catfacts

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Colors
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.app.catfacts.databinding.ActivityMainBinding
import com.app.catfacts.services.apiService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btn = binding.fetch
        btn.setOnClickListener {
            onFetch()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun onFetch(): Unit {
        GlobalScope.launch(Dispatchers.Main) {
            val progressBar = binding.progressBar
            val contentView = binding.content
            progressBar.isVisible = true

            val spinner = binding.spinner
            val count = spinner.selectedItemPosition + 5

            try {
                val response = apiService.getFacts(count)
                if (response.isSuccessful) {
                    contentView.setTextColor(Color.GREEN)
                    response.body()?.apply {
                        val text = mapIndexed { i, v -> "${i + 1}: ${v.text}" }.joinToString("") { "$it\n" }
                        contentView.text = text
                    }
                } else {
                    contentView.setTextColor(Color.RED)
                    contentView.text = response.errorBody()?.toString()
                }
            } catch (e: IOException) {
                contentView.setTextColor(Color.RED)
                contentView.text = "Something went wrong"
            } catch (e: SocketTimeoutException) {
                contentView.setTextColor(Color.RED)
                contentView.text = "Please check your internet connection"
            } catch (e: UnknownHostException){
                contentView.setTextColor(Color.RED)
                contentView.text = "No internet"
            }

            progressBar.isVisible = false
        }
    }
}