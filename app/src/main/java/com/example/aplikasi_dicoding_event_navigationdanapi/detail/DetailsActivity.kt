package com.example.aplikasi_dicoding_event_navigationdanapi.detail

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.aplikasi_dicoding_event_navigationdanapi.R
import com.example.aplikasi_dicoding_event_navigationdanapi.core.utils.convertDateToString
import com.example.aplikasi_dicoding_event_navigationdanapi.core.utils.convertStringToFormattedString
import com.example.aplikasi_dicoding_event_navigationdanapi.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val eventId = intent.getStringExtra(EXTRA_ID)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailsViewModel::class.java]
        eventId.let {  id ->
            viewModel.getDetailData(id!!)
        }

        viewModel.listEventDetail.observe(this){ result ->
            binding.apply {
                eventImage.load(result.mediaCover)
                eventCategory.text = result.category
                eventTitle.text = result.name
                eventOrganizer.text = result.ownerName
                eventSummary.text = result.summary

                eventExpired.text = convertStringToFormattedString(result.endTime!!)
                eventQuota.text = result.quota.toString()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        const val EXTRA_ID = "extra_id"
    }
}