package com.ananananzhuo.jobschedulersample.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.widget.Toast
import com.ananananzhuo.mvvm.utils.logEE

/**
 * author  :mayong
 * function:
 * date    :2021/9/19
 **/
class ExeTaskAfter2SecondService: JobService() {
    override fun onStartJob(params: JobParameters?): Boolean {
        logEE("任务开始执行：onStartJob")
        Toast.makeText(baseContext,"执行任务",Toast.LENGTH_LONG).show()
        jobFinished(params,false)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        logEE("任务结束执行：onStopJob")
        return true
    }

}