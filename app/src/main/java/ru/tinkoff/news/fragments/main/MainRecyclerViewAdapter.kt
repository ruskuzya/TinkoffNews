package ru.tinkoff.news.fragments.main

import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import ru.tinkoff.news.R
import ru.tinkoff.news.db.models.NewsObj


internal class MainRecyclerViewAdapter(data: OrderedRealmCollection<NewsObj>, val listener: OnItemClickListener) :
        RealmRecyclerViewAdapter<NewsObj, MainRecyclerViewAdapter.MyViewHolder>(data, true) {

    interface OnItemClickListener {
        fun onItemClick(item: NewsObj)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_news_main, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = data!!.elementAt(position)
        holder.data = obj

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.title.text = Html.fromHtml(obj!!.title, Html.FROM_HTML_MODE_LEGACY)
        } else {
            holder.title.text = Html.fromHtml(obj!!.title)
        }

        holder.itemView.setOnClickListener { listener.onItemClick(obj) }
    }

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var data: NewsObj? = null

        init {
            title = view.findViewById(R.id.title)
        }
    }
}