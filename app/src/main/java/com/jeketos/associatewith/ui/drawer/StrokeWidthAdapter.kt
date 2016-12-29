package com.jeketos.associatewith.ui.drawer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jeketos.associatewith.R
import kotlinx.android.synthetic.main.item_stroke_width.view.*

/**
 * Created by eugene.kotsogub on 12/27/16.
 *
 */
class StrokeWidthAdapter(val view: DrawerMVP.DrawerView) : RecyclerView.Adapter<StrokeWidthAdapter.Holder>() {

    val widthList = listOf(1f,3f,6f,9f,12f,15f,18f)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_stroke_width, parent, false)
        return Holder(v)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val layoutParams = LinearLayout.LayoutParams(holder.strokeWidth.layoutParams)
        layoutParams.height = widthList[position].toInt()
        val margin = holder.strokeWidth.context.resources.getDimension(R.dimen.item_margin).toInt()
        layoutParams.setMargins(margin, margin, margin, margin)
        holder.strokeWidth.layoutParams = layoutParams
        (holder.strokeWidth.parent as View).setOnClickListener {
            view.setStrokeWidth(widthList[position])
        }
    }

    override fun getItemCount(): Int {
        return widthList.size
    }


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val strokeWidth = itemView.strokeWidth!!
    }
}