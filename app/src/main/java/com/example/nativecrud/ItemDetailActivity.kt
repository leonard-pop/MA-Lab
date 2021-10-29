package com.example.nativecrud

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_item_detail.*

class ItemDetailActivity : AppCompatActivity() {
    private var id: Int = 0;
    private var fragment: ItemDetailFragment? = null;
    private var action: ItemDetailFragment.Action? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(detail_toolbar)
        Log.d("tag", intent.getStringExtra(ItemDetailFragment.ARG_ACTION).toString())
        action = ItemDetailFragment.Action.valueOf(intent.getStringExtra(ItemDetailFragment.ARG_ACTION)
            .toString())

        if(action == ItemDetailFragment.Action.VIEW) {
            fab.setOnClickListener { view ->
                fragment?.update()
                var dialog = AlertDialog.Builder(this)
                    .setTitle("Success")
                    .setMessage("Item updated")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                        finish()
                    })
                    .create().show()
            }
        } else if(action == ItemDetailFragment.Action.ADD) {
            fab.setOnClickListener { view ->
                fragment?.create()
                var dialog = AlertDialog.Builder(this)
                    .setTitle("Success")
                    .setMessage("Item added")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                        finish()
                    })
                    .create().show()
            }
        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            id = intent.getIntExtra(ItemDetailFragment.ARG_ITEM_ID, -1)

            fragment = ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(
                        ItemDetailFragment.ARG_ITEM_ID,
                        intent.getIntExtra(ItemDetailFragment.ARG_ITEM_ID, -1)
                    )
                    putString(
                        ItemDetailFragment.ARG_ACTION,
                        intent.getStringExtra(ItemDetailFragment.ARG_ACTION)
                    )
                }
            }


            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment!!)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                NavUtils.navigateUpTo(this, Intent(this, ItemListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
