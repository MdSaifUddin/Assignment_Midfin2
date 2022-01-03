package com.bb.assignment_midfin

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bb.assignment_midfin.Util.Adapter
import com.bb.assignment_midfin.Util.Helper
import com.bb.assignment_midfin.Util.Item
import com.bb.assignment_midfin.Util.Repository
import com.bb.assignment_midfin.ViewModel.Factory
import com.bb.assignment_midfin.ViewModel.MainViewModel
import com.bb.assignment_midfin.databinding.ActivityMainBinding
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    var bitmap: Bitmap? = null
    var capture: ImageView? = null
    var adapter: Adapter? = null
    var list: ArrayList<Item> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initialize()
        fetchData()
    }

    fun initialize() {
        if (!checkPermission()) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(android.Manifest.permission.CAMERA),
                1
            )
        }
        var helper = Helper.getInstance(this@MainActivity).getDao()
        var repository = Repository(this@MainActivity, helper)
        viewModel = ViewModelProvider(this, Factory(repository)).get(MainViewModel::class.java)

        binding.fab.setOnClickListener {
            if (checkPermission()) {
                var builder = AlertDialog.Builder(this@MainActivity)
                var view = layoutInflater.inflate(R.layout.form, null)
                builder.setView(view)
                var dialog = builder.create()
                dialog.show()
                dialog.setOnDismissListener { capture = null }

                bitmap = null
                capture = view.findViewById(R.id.preview)
                view.findViewById<Button>(R.id.selectImage).setOnClickListener {
                    var intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                    resultLauncher.launch(intent)
                }
                view.findViewById<Button>(R.id.submit).setOnClickListener {
                    var name = view.findViewById<TextView>(R.id.name).text.toString()
                    var location = view.findViewById<TextView>(R.id.location).text.toString()
                    var cost = view.findViewById<TextView>(R.id.cost).text.toString()
                    when {
                        bitmap == null -> showToast("Select Image!")
                        name.isEmpty() -> showToast("Enter Name!")
                        location.isEmpty() -> showToast("Enter Location!")
                        cost.isEmpty() -> showToast("Enter Cost!")
                        else -> {
                            viewModel.addItem(0, name, location, cost, bitmap as Bitmap)
                            dialog.dismiss()
                        }

                    }
                }
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(android.Manifest.permission.CAMERA),
                    1
                )
            }
        }

    }

    //////////
    fun fetchData() {
        viewModel.list.observe(this, {
            Log.i("db","${it.size}")
            if (adapter == null) {
                adapter = Adapter()
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.recyclerView.adapter = adapter
            }
            adapter?.submitList(it)

            if (it.isEmpty()) {
                binding.noRecord.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.noRecord.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        })
    }

    fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_DENIED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(applicationContext, "Permission Granted!", Toast.LENGTH_SHORT).show()
        }
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                bitmap = it.data?.extras?.get("data") as Bitmap
                capture?.setImageBitmap(bitmap)
            }
        }

    fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}