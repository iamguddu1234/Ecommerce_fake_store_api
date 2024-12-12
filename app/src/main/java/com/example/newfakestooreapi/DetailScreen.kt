package com.example.newfakestooreapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_screen)

        val productImage = findViewById<ImageView>(R.id.Pimage)
        val productTitle = findViewById<TextView>(R.id.Ptitle)
        val productDescription = findViewById<TextView>(R.id.Pdescription)
        val productBrand = findViewById<TextView>(R.id.Pbrand)
        val productModel = findViewById<TextView>(R.id.Pmodel)
        val productCate = findViewById<TextView>(R.id.Pcate)
        val productDisc = findViewById<TextView>(R.id.Pdis)
        val productColor = findViewById<TextView>(R.id.Pcolor)
        val productPrice = findViewById<TextView>(R.id.Pprice)


        //get data from intent
        val pImage = intent.getStringExtra("image")
        val ptitle = intent.getStringExtra("title")
        val pdescription = intent.getStringExtra("des")

        val pcolor = intent.getStringExtra("color")
        val pcate = intent.getStringExtra("cate")
        val pdisc = intent.getDoubleExtra("dis",0.0)

        val pprice = intent.getDoubleExtra("price",0.0)
        val pmodel = intent.getStringExtra("model")
        val pbrand = intent.getStringExtra("brand")


        Glide.with(this).load(pImage).into(productImage)
        productTitle.text = ptitle
        productDescription.text = pdescription
        productBrand.text = pbrand
        productModel.text = pmodel
        productDisc.text = "$pdisc % OFF"
        productColor.text = pcolor
        productCate.text = pcate
        productPrice.text = "$pprice"


    }
}