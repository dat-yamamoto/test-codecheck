package jp.co.yumemi.android.code_check

import androidx.recyclerview.widget.DiffUtil

object CodeUtils {
    val listCallback = object : DiffUtil.ItemCallback<item>(){
        override fun areItemsTheSame(oldItem: item, newItem: item): Boolean
        {
            return oldItem.name== newItem.name
        }

        override fun areContentsTheSame(oldItem: item, newItem: item): Boolean
        {
            return oldItem== newItem
        }
    }
}