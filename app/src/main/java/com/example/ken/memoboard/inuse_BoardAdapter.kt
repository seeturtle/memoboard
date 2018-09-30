package com.example.ken.memoboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ken.memoboard.model.Board
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter


class inuse_BoardAdapter(data: OrderedRealmCollection<Board>?) : RealmBaseAdapter<Board>(data) {

    inner class ViewHolder(cell: View) {
        val id = cell.findViewById<TextView>(android.R.id.text1)
        val title= cell.findViewById<TextView>(android.R.id.text2)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        when (convertView) {
            null -> {
                val layoutInfater = LayoutInflater.from(parent?.context)
                view = layoutInfater.inflate(android.R.layout.simple_list_item_2,
                        parent, false)
                viewHolder = this.ViewHolder(view)
                view.tag = viewHolder
            }
            else -> {
                view = convertView
                viewHolder = view.tag as ViewHolder
            }

        }
        adapterData?.run {
            val board = get(position)
            viewHolder.id.text = board.id.toString()
            viewHolder.title.text = board.name
        }
        return view
    }
}