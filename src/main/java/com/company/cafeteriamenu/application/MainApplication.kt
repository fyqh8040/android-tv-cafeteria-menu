package com.company.cafeteriamenu.application

import android.app.Application
import com.company.cafeteriamenu.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * 应用类，负责初始化应用级别的组件
 */
@HiltAndroidApp
class MainApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // 初始化日志库
        initTimber()
    }
    
    /**
     * 初始化Timber日志库
     */
    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            // 开发环境下显示完整日志
            Timber.plant(Timber.DebugTree())
        } else {
            // 生产环境下使用自定义日志树，只记录重要日志
            Timber.plant(object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    // 生产环境下只记录ERROR和WARN级别的日志
                    if (priority == android.util.Log.ERROR || priority == android.util.Log.WARN) {
                        // 可以在这里将日志发送到远程日志服务
                        android.util.Log.println(priority, tag, message)
                    }
                }
            })
        }
    }
}