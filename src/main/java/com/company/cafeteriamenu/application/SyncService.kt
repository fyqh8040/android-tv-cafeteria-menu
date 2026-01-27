package com.company.cafeteriamenu.application

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class SyncService(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            // 执行数据同步
            // TODO: 实现实际的数据同步逻辑
            // 1. 从API获取最新菜单数据
            // 2. 更新本地数据库
            // 3. 返回同步结果
            
            // 临时实现：模拟同步成功
            kotlinx.coroutines.delay(1000)
            
            // 记录同步成功
            logSyncResult(true, "同步成功")
            
            Result.success(
                Data.Builder()
                    .putBoolean("success", true)
                    .putLong("timestamp", System.currentTimeMillis())
                    .build()
            )
        } catch (e: Exception) {
            // 记录同步失败
            logSyncResult(false, e.localizedMessage ?: "未知错误")
            
            if (runAttemptCount < MAX_RETRY_ATTEMPTS) {
                Result.retry()
            } else {
                Result.failure(
                    Data.Builder()
                        .putBoolean("success", false)
                        .putString("error", e.localizedMessage)
                        .build()
                )
            }
        }
    }
    
    private fun logSyncResult(success: Boolean, message: String) {
        // 这里可以记录到本地日志或上传到服务器
        val logMessage = "数据同步${if (success) "成功" else "失败"}: $message"
        android.util.Log.d("SyncService", logMessage)
    }
    
    companion object {
        const val MAX_RETRY_ATTEMPTS = 3
        const val SYNC_WORK_TAG = "menu_sync_work"
        
        fun createSyncWorkRequest(intervalMinutes: Long = 15): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<SyncService>(
                intervalMinutes, TimeUnit.MINUTES
            )
                .setInitialDelay(5, TimeUnit.MINUTES) // 首次延迟5分钟
                .addTag(SYNC_WORK_TAG)
                .setBackoffCriteria(
                    androidx.work.BackoffPolicy.EXPONENTIAL,
                    10, TimeUnit.MINUTES
                )
                .build()
        }
    }
}
