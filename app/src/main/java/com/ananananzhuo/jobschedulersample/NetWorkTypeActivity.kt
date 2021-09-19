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
 * function:
 * date    :2021/9/19
 **/
class NetWorkTypeActivity: CustomAdapterActivity() {
    lateinit var mJobScheduler: JobScheduler
    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mJobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
    }
    override fun getAdapterDatas(): MutableList<ItemData> = mutableListOf(
        ItemData(title = "指定网络流量情况下执行"){
            val mBuilder = JobInfo.Builder(
                1,
                ComponentName(this, ExeTaskAfter2SecondService::class.java)
            )//Builder第一个参数是Job的id，我们可以通过这个id取消任务，第二个参数可以配置要执行任务逻辑的Service
            var mJobInfo = mBuilder
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_CELLULAR)
                .build()
            mJobScheduler.schedule(mJobInfo)//开始调度任务执行
        }
    )

    override fun showFirstItem(): Boolean =false
}