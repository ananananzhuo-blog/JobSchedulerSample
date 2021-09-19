

## 概览
JobScheduler是android系统提供的一个任务执行框架，通过JobScheduler我们可以根据不同的策略调度任务的执行。

例如：

1. 指定任务可以在指定的网络下执行
2. 指定任务只在充电的状态下进行
3. 设置任务的截止时间
4. 指定延迟一段时间后执行任务
5. 指定任务在手机空闲的情况下执行

如果app被杀死则无法最终执行任务，如果有这种需要建议使用WorkManager

## 使用

### 基本使用
#### 创建自定义JobService并在manifest中配置

```kt
class ExeTaskAfter2SecondService: JobService() {
    override fun onStartJob(params: JobParameters?): Boolean {
        logEE("延迟五秒执行：onStartJob")
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        logEE("延迟五秒执行：onStopJob")
        return true
    }
}
```

```xml
<service android:name=".service.ExeTaskAfter2SecondService"
            android:permission="android.permission.BIND_JOB_SERVICE"
            />
```

#### 调度任务执行

```
val mJobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler//获取系统服务
            val mBuilder = JobInfo.Builder(1, ComponentName(this, ExeTaskAfter2SecondService::class.java))//Builder第一个参数是Job的id，我们可以通过这个id取消任务，第二个参数可以配置要执行任务逻辑的Service
            var mJobInfo = mBuilder
                .setMinimumLatency(5000)//指定5s后执行任务
                .build()
            mJobScheduler.schedule(mJobInfo)//开始调度任务执行
```
#### 取消任务执行

```kt
 mJobScheduler.cancel(1)//这个id就是schedule时指定的任务id
 mJobScheduler.cancelAll()//取消全部任务
```
### 设置任务的截止时间

```kt
 var mJobInfo = mBuilder
                .setOverrideDeadline(5000)//五秒内一定会执行一次
                .build()
            mJobScheduler.schedule(mJobInfo)
```
### 设置任务执行的网络状态
#### 只在移动网络的时候执行
如果开始任务时没有连接无线网络则不执行，然后当我们连接无线网络时会自动开始执行网络
```kt
 val mBuilder = JobInfo.Builder(
                1,
                ComponentName(this, ExeTaskAfter2SecondService::class.java)
            )//Builder第一个参数是Job的id，我们可以通过这个id取消任务，第二个参数可以配置要执行任务逻辑的Service
            var mJobInfo = mBuilder
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_CELLULAR)
                .build()
            mJobScheduler.schedule(mJobInfo)//开始调度任务执行
```
### 指定job执行时的网络流量大小限制
感觉基本用不上，打不情况我们不会考虑流量，所以我也没有测试
```kt
setEstimatedNetworkBytes (long downloadBytes, long uploadBytes)
```
### 设置非低电量时执行

```
setRequiresBatteryNotLow
```


#### 必须有网络连接的时候才能执行
NETWORK_TYPE_ANY

#### 无论是否有网络都可以执行
NETWORK_TYPE_NONE
#### 只有wifi情况下才能执行
NETWORK_TYPE_UNMETERED
### 指定任务在手机空闲时执行
setRequiresDeviceIdle

这种方式很难预测
```kt
 val mBuilder = JobInfo.Builder(
                1,
                ComponentName(this, ExeTaskAfter2SecondService::class.java)
            )//Builder第一个参数是Job的id，我们可以通过这个id取消任务，第二个参数可以配置要执行任务逻辑的Service
            var mJobInfo = mBuilder
//                .setOverrideDeadline(5000)//如果设置了dealline时间，则即使设置了setRequiresDeviceIdle到达截止时间时也会执行
                .setRequiresDeviceIdle(true)
                .build()
            mJobScheduler.schedule(mJobInfo)//开始调度任务执行
```
### 设置充电的状态下才能执行

如果我们执行任务时，手机没有充电则不会执行，当我们插上电源的时候任务会自动开始执行

```kt
mJobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
 val mBuilder = JobInfo.Builder(
                1,
                ComponentName(this, ExeTaskAfter2SecondService::class.java)
            )//Builder第一个参数是Job的id，我们可以通过这个id取消任务，第二个参数可以配置要执行任务逻辑的Service
            var mJobInfo = mBuilder
                .setRequiresCharging(true)//设置充电时才能执行
                .build()
            mJobScheduler.schedule(mJobInfo)//开始调度任务执行
```


## api介绍

### JobService.onStartJob
JobScheduler.schedule调用的时候会回调onStartJob方法


onStartJob方法的返回值：

如果返回false表示该任务随方法执行完成而结束，后续不会再调用onStopJob方法（即使我们主动调用了cancel方法）

如果返回true则方法可以被反复执行



### JobService.onStopJob
调用JobSchdeuler.cancel会触发onStopJob回调

### JobService.jobFinished

```
jobFinished(params,false)：第一个参数JobParameters，第二个参数表示是否重复执行
```

主动通知系统已经完成了任务






