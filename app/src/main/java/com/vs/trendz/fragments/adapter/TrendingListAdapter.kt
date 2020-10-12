package com.vs.trendz.fragments.adapter


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vs.trendz.databinding.ListItemTrendingRepoBinding
import com.vs.trendz.model.TrendingRepositoryResponseData
import kotlinx.android.synthetic.main.list_item_trending_repo.view.*

/*
* Recycler view
* */
class TrendingListAdapter(
    val context: Context
) : RecyclerView.Adapter<TrendingListAdapter.ViewHolder>() {


    // used to filtered data
    private var responseRepositoryData = ArrayList<TrendingRepositoryResponseData>()

    // default array
    private var responseRepositoryAllData = ArrayList<TrendingRepositoryResponseData>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ListItemTrendingRepoBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = responseRepositoryData[position]
        item.run {
            (holder as ViewHolder).apply {
                bind(this@run)
                itemView.tag = this@run

                itemView.tv_author_name.text = item.author
                itemView.tv_project_name.text = item.name
                itemView.tv_description.text = item.description
                itemView.tv_programming_language.text = item.language
                itemView.tv_star_count.text = item.stars.toString()


                Glide
                    .with(holder.itemView)
                    .load(item.avatar)
                    .centerCrop()
                    .into(itemView.imageView)


                /*
                    creating dynamic drawable as color indicator
                */

                val shape = GradientDrawable()
                shape.shape = GradientDrawable.OVAL
                shape.setColor(Color.parseColor(item.languageColor))
                itemView.colorIndicator.background = shape
            }
        }
    }

    override fun getItemCount(): Int {
        return responseRepositoryData.size
    }

    fun updateList(it: ArrayList<TrendingRepositoryResponseData>) {
        responseRepositoryData = it
        responseRepositoryAllData = ArrayList(it)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ListItemTrendingRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TrendingRepositoryResponseData) {
            binding.apply {
                dataModel = item
                executePendingBindings()
            }
        }
    }
}