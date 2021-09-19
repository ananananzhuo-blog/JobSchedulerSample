package com.ananananzhuo.jobschedulersample

import android.app.job.JobScheduler
import android.app.job.JobService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ananananzhuo.mvvm.activity.CustomAdapterActivity
import com.ananananzhuo.mvvm.bean.bean.ItemData
import android.content.ComponentName

import android.R.id
import android.annotation.SuppressLint

import android.app.job.JobInfo
import com.ananananzhuo.jobschedulersample.service.ExeTaskAfter2SecondService

/**
 * 让任务延迟五秒执行
 */
class ExeTaskActivity : CustomAdapterActivity() {
    private lateinit var mJobScheduler: JobScheduler
    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mJobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
    }

    override fun getAdapterDatas(): MutableList<ItemData> = mutableListOf(
        ItemData(title = "五秒后执行") {
            val mBuilder = JobInfo.Builder(
                1,
                ComponentName(this, ExeTaskAfter2SecondService::class.java)
            )//Builder第一个参数是Job的id，我们可以通过这个id取消任务，第二个参数可以配置要执行任务逻辑的Service
            var mJobInfo = mBuilder

                .setMinimumLatency(5000)//指定5s后执行任务
                .build()
            mJobScheduler.schedule(mJobInfo)//开始调度任务执行
        },
        ItemData(title = "取消任务执行") {
            mJobScheduler.cancel(1)
        }
    )


    override fun showFirstItem(): Boolean = false

}