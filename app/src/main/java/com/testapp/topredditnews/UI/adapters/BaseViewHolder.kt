package com.testapp.topredditnews.UI.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private var mCurrentPosition: Int = 0
    abstract fun clear()

    open fun onBind(position: Int) {
        mCurrentPosition = position
        clear()
    }

    fun getCurrentPosition(): Int = mCurrentPosition

}