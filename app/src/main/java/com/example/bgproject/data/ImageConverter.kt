//package com.example.bgproject.data
//
//import android.content.Context
//import android.widget.ImageView
//import androidx.room.TypeConverter
//import com.bumptech.glide.Glide
//
//
//
//class ImageConverter{
//    private lateinit var context: Context
//
//    @TypeConverter
//    fun fromImage(imageView: ImageView): String {
//        // Return the file path or URL associated with the image view
//        return imageView.tag.toString()
//    }
//
//    @TypeConverter
//    fun toImage(value: String?): ImageView {
//        // Create a new image view and set its image based on the file path or URL
//        val imageView = ImageView(context)
//        imageView.tag = value
//        Glide.with(context).load(value).into(imageView)
//        return imageView
//    }
//}
