package com.restoo.restaurentapp.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.restoo.restaurentapp.R
import com.restoo.restaurentapp.model.FoodItem


class FavFoodAdapter (val list : ArrayList<FoodItem>,val context : FragmentActivity) : RecyclerView.Adapter<FavFoodAdapter.FavFoodViewHolder>() {

    class FavFoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mImgFoodItem: ImageView
        var mTxtFoodName : TextView
        init {
            mImgFoodItem = view
                    .findViewById<ImageView>(R.id.img_fooditem)
            mTxtFoodName = view.findViewById(R.id.fooditemname)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): FavFoodViewHolder {
        val itemView: View = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_item_fooditems,
                        parent,
                        false)

        return FavFoodViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: FavFoodViewHolder, position: Int) {
        val  listdata = list.get(position)
        holder.mTxtFoodName.setText(listdata.foodname)
        Glide.with(context).load(listdata.FoodImage).into(holder.mImgFoodItem)
    }
}