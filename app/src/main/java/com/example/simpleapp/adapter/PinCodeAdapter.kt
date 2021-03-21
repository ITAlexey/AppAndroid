package com.example.simpleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleapp.MainActivity.Companion.PIN_SIZE
import com.example.simpleapp.R
import com.example.simpleapp.databinding.ItemRvPincodeBinding

class PinCodeAdapter(private var currentPinCodeLen: Int)
    : RecyclerView.Adapter<PinCodeAdapter.PinCodeHolder>() {

    class PinCodeHolder(val binding: ItemRvPincodeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinCodeHolder {
        val itemView = ItemRvPincodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PinCodeHolder(itemView)
    }

    override fun onBindViewHolder(holder: PinCodeHolder, position: Int) {
        if (position < currentPinCodeLen) {
            holder.binding.imgPinDot.setBackgroundResource(R.drawable.ic_filled_dot)
        } else {
            holder.binding.imgPinDot.setBackgroundResource(R.drawable.ic_empty_dot)
        }
    }

    fun updateState(pinCodeLen: Int) {
        currentPinCodeLen = pinCodeLen
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = PIN_SIZE

}