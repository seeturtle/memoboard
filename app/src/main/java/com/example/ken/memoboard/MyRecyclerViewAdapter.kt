package com.example.ken.memoboard

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ken.memoboard.model.Board
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter


internal class MyRecyclerViewAdapter(data: OrderedRealmCollection<Board>) :
        RealmRecyclerViewAdapter<Board, MyRecyclerViewAdapter.MyViewHolder>(data, true) {


    init {
        // Only set this if the model class has a primary key that is also a integer or long.
        // In that case, {@code getItemId(int)} must also be overridden to return the key.
        // See https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#hasStableIds()
        // See https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#getItemId(int)
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(itemView)
    }

    // 実際にアイテムに受け取ったデータをセット
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = getItem(position)
        val itemId = obj!!.id

        //ビューホルダーに値を入れる
        holder.id?.text = obj.id.toString()
        holder.name?.text = obj.name
    }

    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id.toLong()
    }

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id: TextView? = null
        var name: TextView? = null

        init {
            //ビューホルダーの情報がレイアウトのどれと対応するか
            id = view.findViewById(R.id.textview)
            name = view.findViewById(R.id.textview2)
        }
    }
}
