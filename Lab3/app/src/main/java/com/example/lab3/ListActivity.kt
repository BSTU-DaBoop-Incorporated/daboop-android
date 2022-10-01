package com.example.lab3

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.example.lab3.databinding.ActivityListBinding


class ListActivity : BaseFormActivity() {
    private lateinit var binding: ActivityListBinding

    private val CAMERA_PERMISSION = 10001
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val tableLayout =
            binding.gamesTableLayout


        readStateFromFile()


        viewModel.games.observe(this) { games ->
            tableLayout.removeAllViews()
            for (game in games) {
                val row = TableRow(this)
                val textView = TextView(this)
                textView.text = game.title.get()
                textView.setTextAppearance(this,
                    androidx.appcompat.R.style.TextAppearance_AppCompat_Medium)
                row.addView(textView)

                val editButton = Button(this)
                editButton.text = "Edit"
                editButton.setOnClickListener { 
                    sendGameIntent(game, super.games, MainActivity::class.java)
                }
                row.addView(editButton)
                tableLayout.addView(row)
            }
        }


    }
    
    fun addGame(view: View) {
        sendGameIntent(null, super.games, MainActivity::class.java)
    }
    
    fun sendEmailIntent(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_EMAIL, (view as TextView).text)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Game")
        intent.putExtra(Intent.EXTRA_TEXT, "Hello, it's me, Mario")
        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, "unresolved package", Toast.LENGTH_SHORT).show()
            return
        }
        startActivity(intent);

    }
    
    fun sendCallIntent(view: View) {
        val intent = Intent(Intent.ACTION_DIAL);
        intent.data = Uri.parse("tel:" + (view as TextView).text);
        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, "unresolved package", Toast.LENGTH_SHORT).show()
            return
        }
        startActivity(intent);
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    fun openPhotoIntent(view: View) {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION)
        } else {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(cameraIntent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                resultLauncher.launch(cameraIntent)
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            cameraIntentResult(data)
        }
    }

    fun cameraIntentResult(data: Intent?) {
            val photo = data?.extras!!["data"] as Bitmap?
            binding.imageView.setImageBitmap(photo)
    }


    fun openSocialNetworkIntent(view: View) {
        val id = "dQw4w9WgXcQ"
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$id"))
        try {
            startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            startActivity(webIntent)
        }
    }

}