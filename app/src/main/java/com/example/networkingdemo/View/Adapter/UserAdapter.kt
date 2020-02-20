package com.example.networkingdemo.View.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.networkingdemo.Model.User.Result
import com.example.networkingdemo.R
import kotlinx.android.synthetic.main.list_row_items.view.*

class UserAdapter(val resultList: List<Result>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_row_items,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = resultList.size

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) =
        holder.populateItem(resultList[position])


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun populateItem(result: Result) {
            itemView.txt1.text = "${result.name.first} ${result.name.last}"
            itemView.txt2.text = result.email

            Glide.with(itemView).load(result.picture.thumbnail).into(itemView.imgView)

        }
    }
}
