package com.example.newfakestooreapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(
    private var categories: List<String>,
    private val onCategoryClick: (String)-> Unit
):RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val cateText = itemView.findViewById<TextView>(R.id.categoryId)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cate_view,parent,false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categories = categories[position]

        holder.cateText.text = categories

        holder.itemView.setOnClickListener {
            onCategoryClick(categories)
        }
    }

    fun updateCategories(cate: List<String>){
        categories = cate
        notifyDataSetChanged()
    }
}