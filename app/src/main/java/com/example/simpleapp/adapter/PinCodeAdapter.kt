package com.example.simpleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleapp.R
import com.example.simpleapp.databinding.ItemRvPinBinding

class PinCodeAdapter(
    @IntRange(from = 0) private var currentPinLen: Int,
    @IntRange(from = 4) private val pinSize: Int
) : RecyclerView.Adapter<PinCodeAdapter.PinCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinCodeHolder {
        val itemView = ItemRvPinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PinCodeHolder(itemView)
    }

    override fun onBindViewHolder(holder: PinCodeHolder, position: Int) {
        if (position < currentPinLen) {
            holder.binding.imgPinDot.setBackgroundResource(R.drawable.ic_filled_dot)
        } else {
            holder.binding.imgPinDot.setBackgroundResource(R.drawable.ic_empty_dot)
        }
    }

    fun updateState(pinCodeLen: Int) {
        currentPinLen = pinCodeLen
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = pinSize

    class PinCodeHolder(val binding: ItemRvPinBinding) : RecyclerView.ViewHolder(binding.root)

}