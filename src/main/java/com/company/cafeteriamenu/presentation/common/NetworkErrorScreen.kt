package com.company.cafeteriamenu.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Icon
import androidx.tv.material3.icons.Icons
import androidx.tv.material3.icons.rounded.WifiOff

/**
 * 网络故障态界面组件
 * @param modifier 修饰符
 * @param message 显示消息
 */
@Composable
fun NetworkErrorScreen(
    modifier: Modifier = Modifier,
    message: String = "网络连接中断"
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFFF3CD), // 暖黄色调
                    Color(0xFFFCEEB5)
                )
            ))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 断网图标
            Icon(
                imageVector = Icons.Rounded.WifiOff,
                contentDescription = "网络连接中断",
                modifier = Modifier.size(120.dp),
                tint = Color(0xFFD93A3A)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // 错误消息
            Text(
                text = message,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 辅助消息
            Text(
                text = "系统正在尝试自动重连，请耐心等待...",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF666666),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 错误代码
            Text(
                text = "Error Code: E-502",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF999999),
                textAlign = TextAlign.Center
            )
        }
    }
}