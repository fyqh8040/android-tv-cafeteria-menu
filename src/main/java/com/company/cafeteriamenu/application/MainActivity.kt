package com.company.cafeteriamenu.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import com.company.cafeteriamenu.presentation.common.IdleStateScreen
import com.company.cafeteriamenu.presentation.common.NetworkErrorScreen
import com.company.cafeteriamenu.presentation.dashboard.DashboardScreen
import com.company.cafeteriamenu.presentation.settings.SettingsScreen
import com.company.cafeteriamenu.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 设置为全屏显示
        window.setDecorFitsSystemWindows(false)
        
        setContent {
            CafeteriaMenuTheme {
                MainApp()
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        // 确保保持全屏
        window.setDecorFitsSystemWindows(false)
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CafeteriaMenuTheme(
    content: @Composable () -> Unit
) {
    androidx.tv.material3.MaterialTheme(
        content = content
    )
}

@Composable
fun MainApp() {
    val viewModel: MainViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    
    // 应用启动时初始化
    LaunchedEffect(Unit) {
        viewModel.initializeApp()
    }
    
    // 根据状态显示不同界面
    when (val state = uiState) {
        is MainViewModel.UiState.Loading -> {
            LoadingScreen()
        }
        is MainViewModel.UiState.Error -> {
            ErrorScreen(
                message = state.message,
                onRetry = { viewModel.retryInitialization() }
            )
        }
        is MainViewModel.UiState.Success -> {
            // 检查网络状态
            if (!state.networkStatus) {
                NetworkErrorScreen()
            } else {
                DashboardScreen(
                    modifier = Modifier.fillMaxSize(),
                    menuData = state.menuData,
                    configs = state.configs,
                    networkStatus = state.networkStatus,
                    onRefresh = { viewModel.refreshData() }
                )
            }
        }
        is MainViewModel.UiState.Idle -> {
            IdleStateScreen(
                message = state.message
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    androidx.tv.material3.Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        androidx.tv.material3.CenterAlignedRow(
            modifier = Modifier.fillMaxSize()
        ) {
            androidx.tv.material3.CircularProgressIndicator()
            androidx.tv.material3.Text(
                text = "加载菜单数据中...",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    androidx.tv.material3.Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        androidx.tv.material3.CenterAlignedColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            androidx.compose.material.icons.Icons.Filled.Error?.let {
                androidx.tv.material3.Icon(
                    imageVector = it,
                    contentDescription = "错误",
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
            androidx.tv.material3.Text(
                text = "加载失败",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 16.dp)
            )
            androidx.tv.material3.Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp, horizontal = 32.dp),
                textAlign = androidx.compose.ui.text.TextAlign.Center
            )
            androidx.tv.material3.Button(
                onClick = onRetry,
                modifier = Modifier.padding(top = 24.dp)
            ) {
                androidx.tv.material3.Text("重试")
            }
        }
    }
}

