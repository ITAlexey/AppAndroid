package com.example.simpleapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleapp.databinding.ItemRvPincodeBinding

class PinCodeAdapter(private val pinCodeSize: Int, private var currentPinCodeLen: Int)
    : RecyclerView.Adapter<PinCodeAdapter.PinCodeHolder>() {

    class PinCodeHolder(val binding: ItemRvPincodeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinCodeHolder {
        val itemView = ItemRvPincodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PinCodeHolder(itemView)
    }

    override fun onBindViewHolder(holder: PinCodeHolder, position: Int) {
        if (position < currentPinCodeLen) {
            holder.binding.dotImg.setBackgroundResource(R.drawable.black_dot)
        } else {
            holder.binding.dotImg.setBackgroundResource(R.drawable.gray_dot)
        }
    }

    fun updateState(pinCodeLen: Int) {
        currentPinCodeLen = pinCodeLen
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = pinCodeSize

    fun getOriginState() {

    }

}