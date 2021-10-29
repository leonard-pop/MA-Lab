package com.example.nativecrud

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.nativecrud.dummy.DummyContent
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.item_list_content.view.*

class ItemListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            val intent = Intent(this, ItemDetailActivity::class.java).apply {
                putExtra(ItemDetailFragment.ARG_ACTION, ItemDetailFragment.Action.ADD.toString())
            }
            this.startActivity(intent)
        }

        setupRecyclerView(item_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(DummyContent.ITEMS, this)
    }

    /*
    private val onLongClickListener: View.OnLongClickListener = View.OnLongClickListener { v ->
        val item = v.tag as DummyContent.FoodItem
        var dialog = AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Delete item?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                DummyContent.deleteItem(item)
                dialog.dismiss()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            })
            .create().show()
        return@OnLongClickListener true;
    }
     */

    fun onLongClickListener(
        item: DummyContent.FoodItem,
        adapter: RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>,
        position: Int
    ): Boolean {
        var deleted: Boolean = false
        var dialog = AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Delete item?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                DummyContent.deleteItem(item)
                adapter.notifyItemRemoved(position)
                dialog.dismiss()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            })
            .create().show()

        return deleted
    }

    class SimpleItemRecyclerViewAdapter(
        private val values: List<DummyContent.FoodItem>,
        private val listActivity: ItemListActivity
    ) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyContent.FoodItem
            val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
                putExtra(ItemDetailFragment.ARG_ACTION, ItemDetailFragment.Action.VIEW.toString())
            }
            v.context.startActivity(intent)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.idView.text = item.name
            holder.contentView.text = item.category

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
                setOnLongClickListener(View.OnLongClickListener { v ->
                    val res = listActivity.onLongClickListener(
                        item,
                        this@SimpleItemRecyclerViewAdapter,
                        position
                    )

                    return@OnLongClickListener true
                })
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.id_text
            val contentView: TextView = view.content
        }
    }
}
