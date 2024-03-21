package com.example.lilinyxzhao_horchatas_sharedpreferencesapplication

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var editText: EditText
    private lateinit var imageView: ImageView
    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_TEXT_KEY = "text_key"
//    private val PREF_IMG_KEY = "img_key"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences =  getSharedPreferences("PREFERENCE_NAME",MODE_PRIVATE)
        button = findViewById(R.id.button)
        imageView = findViewById(R.id.imageView)
        editText = findViewById(R.id.editText)

        button.setOnClickListener {
            val images = arrayOf(R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6)
            val randomIndex = Random().nextInt(images.size)
            imageView.setImageResource(images[randomIndex])
        }
        val savedText = sharedPreferences.getString(PREF_TEXT_KEY, "")

    val encodedImage = sharedPreferences.getString("encodedImage", "DEFAULT")

    if (encodedImage != "DEFAULT") {
        val imageBytes = Base64.decode(encodedImage, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        imageView.setImageBitmap(decodedImage)
    }
    editText.setText(savedText)
    }
    override fun onDestroy() {
        super.onDestroy()

        val editor = sharedPreferences.edit()
        val selectedImage = imageView.drawable

        if (selectedImage != null) {
            val bitmap = (selectedImage as BitmapDrawable).bitmap
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val imageBytes = byteArrayOutputStream.toByteArray()
            val encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            editor.putString("encodedImage", encodedImage)
            editor.commit()
        }

        editor.putString(PREF_TEXT_KEY, editText.text.toString())
        editor.commit()
    }
}



