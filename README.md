# 员工食堂菜单TV客户端 - Android TV应用

## 概述
这是一个专为Android TV设备设计的员工食堂菜单展示应用，支持自动启动、全屏显示、定时数据同步、断网缓存等功能。

## 技术要求
- **最小SDK版本**: 24 (Android 7.0 Nougat)
- **目标SDK版本**: 34 (Android 14)
- **开发语言**: Kotlin
- **架构**: MVVM + Clean Architecture
- **依赖注入**: Dagger Hilt
- **网络请求**: Retrofit + OkHttp
- **本地存储**: Room Database
- **异步处理**: Kotlin Coroutines + Flow
- **图片加载**: Coil

## 项目结构
```
app/
├── src/main/
│   ├── java/com/company/cafeteriamenu/
│   │   ├── application/
│   │   │   ├── MainActivity.kt      # 主Activity，自动启动
│   │   │   ├── MainApplication.kt   # 应用类
│   │   │   └── di/                  # 依赖注入模块
│   │   ├── data/
│   │   │   ├── local/               # 本地数据层
│   │   │   │   ├── database/
│   │   │   │   ├── dao/
│   │   │   │   └── entity/
│   │   │   ├── remote/              # 远程数据层
│   │   │   │   ├── api/
│   │   │   │   ├── model/
│   │   │   │   └── interceptor/
│   │   │   └── repository/          # 仓库层
│   │   ├── domain/                  # 领域层
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   └── usecase/
│   │   └── presentation/
│   │       ├── common/              # 通用组件
│   │       ├── dashboard/           # 主界面
│   │       ├── menu/                # 菜单展示
│   │       ├── settings/            # 设置界面
│   │       └── viewmodel/           # ViewModel
│   ├── res/                         # 资源文件
│   │   ├── layout/                  # 布局文件
│   │   ├── values/                  # 样式和字符串
│   │   └── drawable/                # 图片资源
│   └── AndroidManifest.xml          # 应用清单
├── build.gradle.kts                 # 模块构建配置
└── proguard-rules.pro               # 混淆规则
```

## 核心功能实现

### 1. 自动启动与全屏显示
```xml
<!-- AndroidManifest.xml -->
<activity
    android:name=".application.MainActivity"
    android:exported="true"
    android:launchMode="singleTask"
    android:screenOrientation="landscape"
    android:theme="@style/Theme.CafeteriaMenu.FullScreen">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
        <category android:name="android.intent.category.LEANBACK_LAUNCHER" />
    </entint-filter>
</activity>
```

### 2. 数据同步服务
```kotlin
// SyncService.kt
class SyncService : Worker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            // 增量同步逻辑
            val lastSync = preferences.getLastSyncTime()
            val syncData = repository.syncMenuData(lastSync)
            
            // 保存到本地数据库
            repository.saveMenuData(syncData.menuData)
            repository.saveConfigs(syncData.configs)
            
            // 更新同步时间
            preferences.updateLastSyncTime()
            
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < MAX_RETRY_COUNT) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }
}
```

### 3. 本地缓存策略
```kotlin
// MenuRepositoryImpl.kt
class MenuRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val networkMonitor: NetworkMonitor
) : MenuRepository {
    
    override suspend fun getTodayMenu(): Flow<Resource<List<DailyMenu>>> = flow {
        emit(Resource.Loading)
        
        // 首先从本地数据库获取
        val localData = localDataSource.getMenuData()
        if (localData.isNotEmpty()) {
            emit(Resource.Success(localData))
        }
        
        // 如果网络可用，尝试同步最新数据
        if (networkMonitor.isOnline()) {
            try {
                val remoteData = remoteDataSource.fetchMenuData()
                localDataSource.saveMenuData(remoteData)
                emit(Resource.Success(remoteData))
            } catch (e: Exception) {
                if (localData.isEmpty()) {
                    emit(Resource.Error("网络错误且无本地缓存", e))
                }
                // 有本地数据时不显示错误，继续使用缓存
            }
        } else if (localData.isEmpty()) {
            emit(Resource.Error("无网络连接且无本地缓存"))
        }
    }
}
```

### 4. 菜单展示界面
```kotlin
// MenuFragment.kt
class MenuFragment : Fragment() {
    
    private val viewModel: MenuViewModel by viewModels()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CafeteriaMenuTheme {
                    MenuScreen(
                        viewModel = viewModel,
                        onItemFocused = { item ->
                            // TV遥控器焦点处理
                        }
                    )
                }
            }
        }
    }
}
```

## 关键配置

### 构建配置 (build.gradle.kts)
```kotlin
android {
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.company.cafeteriamenu"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
        
        // TV设备特定配置
        buildConfigField("boolean", "IS_TV", "true")
    }
    
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    
    buildFeatures {
        compose = true
        viewBinding = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}

dependencies {
    // AndroidX
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    
    // Compose
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.tv:tv-foundation:1.0.0-alpha10")
    implementation("androidx.tv:-materialtv:1.0.0-alpha10")
    
    // 网络
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    
    // 本地存储
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")
    kapt("androidx.room:room-compiler:2.6.0")
    
    // 依赖注入
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    
    // 图片加载
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // 工作管理器
    implementation("androidx.work:work-runtime-ktx:2.9.0")
}
```

## 部署与打包

### 生成APK
```bash
./gradlew assembleRelease
```

### 安装到设备
```bash
adb install app/build/outputs/apk/release/app-release.apk
```

### 自动启动配置
1. 安装APK到TV设备
2. 在系统设置中设置为"开机自启动"
3. 配置设备为"信息亭模式"（Kiosk Mode）防止用户退出

## 远程更新机制
- 应用启动时检查更新
- 支持后台静默下载
- 用户确认后安装更新
- 提供手动检查更新选项

## 兼容性说明
- 支持Android TV 7.0及以上版本
- 适配不同屏幕分辨率（720p, 1080p, 4K）
- 支持横屏显示
- 兼容小米、创维、海信等主流Android TV设备

## 调试与日志
- 开发模式启用详细日志
- 生产环境日志上传到服务器
- 支持远程查看设备状态
- 崩溃报告自动收集