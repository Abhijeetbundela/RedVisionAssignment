package com.example.redvisionassignment.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redvisionassignment.R
import com.example.redvisionassignment.adapter.DataAdapter
import com.example.redvisionassignment.model.Data
import com.example.redvisionassignment.viewModel.DataVM
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class HomeActivity : AppCompatActivity(), DataAdapter.AdapterCallback {

    private val dataList by lazy { ArrayList<Data>() }

    private val dataAdapter: DataAdapter by lazy { DataAdapter(dataList, this, this) }

    private val dataViewModel by lazy { ViewModelProvider(this).get(DataVM::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        data_recycler_view.recycledViewPool.setMaxRecycledViews(1, 0)
        data_recycler_view.layoutManager = LinearLayoutManager(this@HomeActivity)
        data_recycler_view.adapter = dataAdapter

        create_btn.setOnClickListener {
            startActivity(Intent(this@HomeActivity, RegistrationActivity::class.java))
        }

        dataViewModel.data.observe(this, androidx.lifecycle.Observer {
            Log.d("MYT", "Add $it")
            dataAdapter.addItem(it)
        })

    }

    override fun update(data: Data) {

        Log.d("MYT", "Update $data")
        val updateIntent = Intent(this@HomeActivity, RegistrationActivity::class.java)
        updateIntent.putExtra("data", data)
        updateIntent.putExtra("isUpdate", data)
        startActivity(updateIntent)
    }

    override fun like(data: Data) {
        data.like = !data.like
        dataViewModel.update(data)
    }

    override fun delete(data: Data) {

        Log.d("MYT", "delete $data")
        val builder =
            AlertDialog.Builder(this)

        builder.setCancelable(false).setTitle("Delete Data")
            .setMessage("Are you sure you want to delete ?")

        builder.setPositiveButton(
            "Delete"
        ) { _, _ -> }.setNegativeButton(
            "Cancel"
        ) { _, _ -> }

        val alertDialog = builder.create()
        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setOnClickListener {
                dataViewModel.delete(data)
                alertDialog.dismiss()
            }

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setOnClickListener { alertDialog.dismiss() }
    }
}