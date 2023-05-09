package com.baharudindayat.storyapp.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baharudindayat.storyapp.data.remote.response.Story
import com.baharudindayat.storyapp.databinding.ListStoryBinding
import com.baharudindayat.storyapp.ui.main.DetailActivity
import com.bumptech.glide.Glide


class MainAdapter : RecyclerView.Adapter<MainAdapter.StoryViewHolder>() {

    private val listStory = ArrayList<Story>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Story>) {
        listStory.clear()
        listStory.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StoryViewHolder(
        ListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(listStory[position])
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listStory[holder.adapterPosition])
        }
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_STORY, listStory[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listStory.size

    class StoryViewHolder(private val binding: ListStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listStory: Story) {
            with(binding) {
                Glide.with(binding.root.context)
                    .load(listStory.photoUrl)
                    .into(ivStory)
                tvName.text = listStory.name
                tvDescription.text = listStory.description
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(listStory: Story)
    }

}