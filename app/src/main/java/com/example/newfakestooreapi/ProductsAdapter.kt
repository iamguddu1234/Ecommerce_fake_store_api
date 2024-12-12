package com.example.newfakestooreapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class ProductsAdapter(
    private var productList: List<Product>,
    private val onItemClicked: (Product) -> Unit,
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsAdapter.ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_layout, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsAdapter.ProductViewHolder, position: Int) {

        val product = productList[position]
        Glide.with(holder.itemView.context)
            .load(product.image) // Assuming product.imageUrl is the field holding the image URL
//            .placeholder(R.drawable.placeholder) // Optional placeholder
//            .error(R.drawable.error_image) // Optional error image
            .into(holder.image)

        holder.title.text = product.title
        holder.price.text = product.price.toString()
        holder.brand.text = product.brand
        holder.color.text = product.color
        holder.discount.text = product.discount.toString()

        holder.itemView.setOnClickListener {
            onItemClicked(product)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image = itemView.findViewById<ImageView>(R.id.imageView)
        val title = itemView.findViewById<TextView>(R.id.title)
        val price = itemView.findViewById<TextView>(R.id.price)
        val brand = itemView.findViewById<TextView>(R.id.brand)
        val color = itemView.findViewById<TextView>(R.id.color)
        val discount = itemView.findViewById<TextView>(R.id.discount)

    }

    fun updateProduct(newProduct: List<Product>) {
//        productList = newProduct

        //use  Below Line when page api call
        val currentList = productList.toMutableList()
        newProduct.forEach { product ->
            if (!currentList.contains(product)) {
                currentList.add(product)
            }
        }
        productList = currentList
        //use  Above Line when page api call




        notifyDataSetChanged()
    }


}

// DiffCallback for better performance when updating the list
//class ProductDiffCallback:DiffUtil.ItemCallback<Product>(){
//    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
//        return oldItem == newItem
//    }
//}


