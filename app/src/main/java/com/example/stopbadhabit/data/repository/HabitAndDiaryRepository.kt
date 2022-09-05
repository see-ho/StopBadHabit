package com.example.roomdbtest.repository

import com.example.stopbadhabit.data.model.HabitAndModel.HabitAndDiary
import com.example.stopbadhabit.data.model.HabitAndModel.HabitAndDiaryDao

class HabitAndDiaryRepository(private val habitAndDiaryDao: HabitAndDiaryDao) {
    suspend fun getAllHabitAndDiary() : List<HabitAndDiary>? {
        return habitAndDiaryDao.getAllHabitAndDiary()
    }

    suspend fun getHabit(habit_id:Int) :HabitAndDiary? {
        return habitAndDiaryDao.getHabitAndDiary(habit_id)
    }
}