package com.example.simpleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleapp.Constants.PIN_SIZE
import com.example.simpleapp.R
import com.example.simpleapp.databinding.ItemRvPinBinding

class PinAdapter(
    @IntRange(from = 0) private var currentPinLen: Int
) : RecyclerView.Adapter<PinAdapter.PinCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinCodeHolder {
        val itemView = ItemRvPinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PinCodeHolder(itemView)
    }

    override fun onBindViewHolder(holder: PinCodeHolder, position: Int) {
        val color = if (position < currentPinLen) {
            ContextCompat.getColor(
                holder.imgPinDot.context,
                R.color.blue
            )
        } else {
            ContextCompat.getColor(
                holder.imgPinDot.context,
                R.color.gray
            )
        }
        holder.imgPinDot.setColorFilter(color)
    }

    fun updateState(pinCodeLen: Int) {
        currentPinLen = pinCodeLen
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = PIN_SIZE

    class PinCodeHolder(binding: ItemRvPinBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgPinDot = binding.imgPinDot
    }
}