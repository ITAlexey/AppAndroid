package com.example.simpleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleapp.R
import com.example.simpleapp.databinding.ItemRvPinBinding

class PinAdapter(
    @IntRange(from = 0) private var currentPinLen: Int = 0
) : RecyclerView.Adapter<PinAdapter.PinCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinCodeHolder {
        val itemView = ItemRvPinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PinCodeHolder(itemView)
    }

    override fun onBindViewHolder(holder: PinCodeHolder, position: Int) {
        var color = if (position < currentPinLen) {
            R.color.blue
        } else {
            R.color.gray
        }
        color = ContextCompat.getColor(holder.imgPinDot.context, color)
        holder.imgPinDot.setColorFilter(color)
    }

    fun updateState(pinCodeLen: Int) {
        currentPinLen = pinCodeLen
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
        PIN_SIZE

    class PinCodeHolder(binding: ItemRvPinBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgPinDot = binding.imgPinDot
    }

    companion object {
        const val PIN_SIZE = 4
    }
}