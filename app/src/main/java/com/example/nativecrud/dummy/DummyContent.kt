package com.example.nativecrud.dummy

import android.icu.util.DateInterval
import java.util.*

object DummyContent {

  val ITEMS: MutableList<FoodItem> = ArrayList()

  val ITEM_MAP: MutableMap<Int, FoodItem> = HashMap()

  private const val COUNT = 25

  init {
    addItem(FoodItem(1, "White Bread", "Bread", "30/10/21",
      "Daily", 10, "Days"))
    addItem(FoodItem(1, "Multigrain Bread", "Bread", "20/10/21",
      "Daily", 15, "Days"))
  }

  private fun addItem(item: FoodItem) {
    ITEMS.add(item)
    ITEM_MAP[item.id] = item
  }

  /*
  private fun createDummyItem(position: Int): DummyItem {
    return DummyItem(position.toString(), "Item $position", makeDetails(position))
  }

  private fun makeDetails(position: Int): String {
    val builder = StringBuilder()
    builder.append("Details about Item: ").append(position)
    for (i in 0 until position) {
      builder.append("\nMore details information here.")
    }
    return builder.toString()
  }
   */

  data class FoodItem(val id: Int, val name: String, val category: String, val expiryDate: String,
                      val notificationInterval: String, val notifyBeforeCount: Int,
                      val notifyBeforeScale: String)

  data class DummyItem(val id: String, val content: String, val details: String) {
    override fun toString(): String = content
  }
}
