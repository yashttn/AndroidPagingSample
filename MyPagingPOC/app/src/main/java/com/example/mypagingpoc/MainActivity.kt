package com.example.mypagingpoc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRV()
    }

    private fun initRV() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        val itemViewModel: ItemViewModel =
            ViewModelProviders.of(this).get(ItemViewModel::class.java)
        val adapter = ItemAdapter(this)

        itemViewModel.itemPagedList.observe(this, Observer { items ->
            adapter.submitList(items)
        })

        recycler_view.adapter = adapter
    }
}
