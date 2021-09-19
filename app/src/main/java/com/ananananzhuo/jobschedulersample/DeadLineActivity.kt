package com.ananananzhuo.jobschedulersample

import android.annotation.SuppressLint
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Bundle
import com.ananananzhuo.jobschedulersample.service.ExeTaskAfter2SecondService
import com.ananananzhuo.mvvm.activity.CustomAdapterActivity
import com.ananananzhuo.mvvm.bean.bean.ItemData

/**
 * author  :mayong
 * function:设置带截止时间的job，到时间之前必定执行
 * date    :2021/9/19
 **/
class DeadLineActivity : CustomAdapterActivity() {
    lateinit var mJobScheduler: JobScheduler
    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mJobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
    }

    override fun getAdapterDatas(): MutableList<ItemData> = mutableListOf(
        ItemData(title = "设置任务截止时间") {
            val mBuilder = JobInfo.Builder(
                1,
                ComponentName(this, ExeTaskAfter2SecondService::class.java)
            )//Builder第一个参数是Job的id，我们可以通过这个id取消任务，第二个参数可以配置要执行任务逻辑的Service
            var mJobInfo = mBuilder
                .setOverrideDeadline(5000)//五秒内一定会执行一次
                .build()
            mJobScheduler.schedule(mJobInfo)//开始调度任务执行
        }
    )

    override fun showFirstItem(): Boolean = false
}