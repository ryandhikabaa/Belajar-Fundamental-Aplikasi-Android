package com.ryandhikaba.belajarfundamentalaplikasiandroid.MyAppBar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ryandhikaba.belajarfundamentalaplikasiandroid.databinding.ActivityMabmenuBinding

class MABMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMabmenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMabmenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    Toast.makeText(this@MABMenuActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }
        }
    }
}