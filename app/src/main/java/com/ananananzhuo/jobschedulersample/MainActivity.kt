package com.ananananzhuo.jobschedulersample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ananananzhuo.mvvm.activity.CustomAdapterActivity
import com.ananananzhuo.mvvm.bean.bean.ItemData

class MainActivity : CustomAdapterActivity() {
    override fun getAdapterDatas(): MutableList<ItemData> = mutableListOf(

        ItemData(title = "简单使用，5s后执行"){
            startActivity(Intent(this,ExeTaskActivity::class.java))
        },
        ItemData(title = "设置任务截止时间，最晚5s后结束"){
            startActivity(Intent(this,DeadLineActivity::class.java))
        },
        ItemData(title = "指定使用网络流量的时候才执行"){
            startActivity(Intent(this,NetWorkTypeActivity::class.java))
        },
        ItemData(title = "手机空闲时执行任务"){
            startActivity(Intent(this,RequireDeviceIdleActivity::class.java))
        },
        ItemData(title = "充电时执行任务"){
            startActivity(Intent(this,RequireChargeActivity::class.java))
        }
    )

    override fun showFirstItem(): Boolean =false

}