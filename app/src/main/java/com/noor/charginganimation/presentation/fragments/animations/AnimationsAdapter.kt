package com.noor.charginganimation.presentation.fragments.animations

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.noor.charginganimation.R
import com.noor.charginganimation.core.extensions.click
import com.noor.charginganimation.databinding.ItemAnimationBinding
import com.noor.charginganimation.domain.model.Animation

class AnimationsAdapter(
    private val onItemClick: (animation: Animation) -> Unit
//    private val onDeleteClick: (animation: Animation) -> Unit
) : RecyclerView.Adapter<AnimationsAdapter.ViewHolder>() {

    private val animations = ArrayList<Animation>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAnimationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(
            binding,
            onItemClick
//            onDeleteClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val anim = animations[position]

        holder.binding.apply {
            tvName.text = anim.name
            tvUrl.text = anim.url
            tvCategory.text = anim.category

            itemLayout.click {
                it.startAnimation(AnimationUtils.loadAnimation(root.context, R.anim.click_anim))
                holder.onItemClick(anim)
            }

            /*icDelete.click {
                it.startAnimation(AnimationUtils.loadAnimation(root.context, R.anim.click_anim))
                holder.onDeleteClick(anim)
            }*/
        }
    }

    fun setData(animList: List<Animation>) {
        this.animations.clear()
        this.animations.addAll(animList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = animations.size

    inner class ViewHolder(
        val binding: ItemAnimationBinding,
        val onItemClick: (animation: Animation) -> Unit
//        val onDeleteClick: (animation: Animation) -> Unit
    ) : RecyclerView.ViewHolder(binding.root)
}