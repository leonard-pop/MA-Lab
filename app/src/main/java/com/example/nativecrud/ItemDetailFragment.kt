package com.example.nativecrud

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nativecrud.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.view.*

class ItemDetailFragment : Fragment() {

    private var item: DummyContent.FoodItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = DummyContent.ITEM_MAP[it.getInt(ARG_ITEM_ID)]
                item?.name?.let { it1 -> Log.d("tag", it1) }
                activity?.toolbar_layout?.title = item?.name
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // Show the dummy content as text in a TextView.
        item?.let {
            rootView.root.name.setText(it.name)
            rootView.root.category.setText(it.category)
            rootView.root.expiry_date.setText(it.expiryDate)
            rootView.root.notification_interval.setText(it.notificationInterval)
            rootView.root.notification_before_count.setText(it.notifyBeforeCount.toString())
            rootView.root.notification_before_scale.setText(it.notifyBeforeScale)
        }
        return rootView
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }

    fun update() {
        Log.d("tag", "Updating: " + this.item?.id.toString())
        DummyContent.ITEM_MAP[this.item?.id]?.name = view?.root?.name?.text.toString()
        DummyContent.ITEM_MAP[this.item?.id]?.category = view?.root?.category?.text.toString()
        DummyContent.ITEM_MAP[this.item?.id]?.expiryDate = view?.root?.expiry_date?.text.toString()
        DummyContent.ITEM_MAP[this.item?.id]?.notificationInterval = view?.root?.notification_interval?.text.toString()
        DummyContent.ITEM_MAP[this.item?.id]?.notifyBeforeCount = view?.root?.notification_before_count?.text.toString().toInt()
        DummyContent.ITEM_MAP[this.item?.id]?.notifyBeforeScale = view?.root?.notification_before_scale?.text.toString()
    }
}
