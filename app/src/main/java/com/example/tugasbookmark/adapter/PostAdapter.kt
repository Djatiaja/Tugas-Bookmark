package com.example.tugasbookmark.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecyclerListener
import com.example.localdatabase.database.Post
import com.example.tugasbookmark.Dao.PostDao
import com.example.tugasbookmark.R
import com.example.tugasbookmark.databinding.ItemPostBinding
import java.util.concurrent.ExecutorService

class PostAdapter(
    private val posts:List<Post>,
):RecyclerView.Adapter<PostAdapter.ItemPost>() {

    inner class ItemPost(
        private val binding: ItemPostBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data:Post){
            with(binding){
                id.text = data.id.toString()
                txtTitle.text = data.title
                txtDescription.text = data.description

                if (data.bookmark){
                    imageBookmark.setBackgroundResource(R.drawable.baseline_bookmark_24)
                }else{
                    imageBookmark.setBackgroundResource(R.drawable.baseline_bookmark_border_24)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPost {
        val binding =ItemPostBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent, false
        )
        return  ItemPost(binding)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ItemPost, position: Int) {
        holder.bind(posts[position])
    }

    fun getItem(id:Int): Post {
        return posts[id]
    }


}


