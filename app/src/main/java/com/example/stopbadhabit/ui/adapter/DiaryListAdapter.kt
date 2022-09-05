package com.example.stopbadhabit.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stopbadhabit.R
import com.example.stopbadhabit.data.model.Diary.Diary
import com.example.stopbadhabit.data.model.Habit.Habit
import com.example.stopbadhabit.databinding.DiaryItemviewBinding
import com.example.stopbadhabit.databinding.FragmentDiaryWriteBinding
import com.example.stopbadhabit.databinding.HabitItemviewBinding

class DiaryListAdapter(
    val onClick: (Int) -> Unit
) : RecyclerView.Adapter<DiaryListAdapter.DiaryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiaryListAdapter.DiaryViewHolder =
        DiaryViewHolder(
            DiaryItemviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DiaryListAdapter.DiaryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    var list: List<Diary> = arrayListOf<Diary>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    //private var list: List<Diary> = arrayListOf(Diary(1,1,"2022-09-01","","","","하하"))


    inner class DiaryViewHolder(private val binding: DiaryItemviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(diary: Diary) {
            Log.e(javaClass.simpleName, "bind: $diary", )
            Glide.with(binding.root).load(R.drawable.bg_mob_hard1).into(binding.ivDiary)
            binding.tvDate.text = diary.diary_date
            binding.root.setOnClickListener {
                diary.diary_id?.let{
                    onClick(it)
                }
            }
        }
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun setData(newItems: ArrayList<Diary>) {
//        this.list = newItems
//        Log.e(javaClass.simpleName, "setData: $newItems", )
//        notifyDataSetChanged()
//    }
}