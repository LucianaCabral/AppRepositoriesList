package com.lucianacabral.applembretes.dataSource

import com.lucianacabral.applembretes.model.Task

object TaskDataSource{
    private val list = arrayListOf<Task>()
    fun getList() = list

    fun insertTask(task:Task){
        list.add(task.copy(id=list.size +1))


    }
}