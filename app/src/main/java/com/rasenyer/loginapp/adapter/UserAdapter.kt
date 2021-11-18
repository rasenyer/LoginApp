package com.rasenyer.loginapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.rasenyer.loginapp.R
import com.rasenyer.loginapp.model.User
import com.rasenyer.loginapp.databinding.ItemUserBinding

class UserAdapter(private val usersList: List<User>): RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding: ItemUserBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_user, viewGroup,false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) { myViewHolder.bind(usersList[position]) }

    override fun getItemCount(): Int { return usersList.size }

}

class MyViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(user: User){

        binding.mProfilePicture.load(user.link){
            transformations(CircleCropTransformation())
            placeholder(R.drawable.ic_profile_picture)
            crossfade(true)
            crossfade(400)
        }

        binding.mTextViewName.text = user.name
        binding.mTextViewUsername.text = user.username

    }

}