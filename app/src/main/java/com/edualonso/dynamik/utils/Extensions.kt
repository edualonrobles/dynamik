package com.edualonso.dynamik.utils

import android.support.v7.util.DiffUtil

/**
 * Created by Eduardo Alonso Robles on 10/12/2018.
 * Dynamik.
 */
/**
 * Calculate diff from both lists.
 * List types must implement equals in order to distinguish between two items
 * @param list the list to calculate diff
 * @param itemCallback the item callback to calculate diffs. If no value provided [DefaultItemCallback] will be used,
 * which assumes item type implements equals.
 */
fun <T> List<T>.calculateDiff(
    list: List<T>,
    itemCallback: DiffUtil.ItemCallback<T> = DefaultItemCallback()
): DiffUtil.DiffResult =
    DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            itemCallback.areItemsTheSame(get(oldItemPosition), list[newItemPosition])

        override fun getOldListSize(): Int = size

        override fun getNewListSize(): Int = list.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            itemCallback.areContentsTheSame(get(oldItemPosition), list[newItemPosition])
    })