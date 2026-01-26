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

/**
 * 空闲/过渡态界面组件
 * @param modifier 修饰符
 * @param message 显示消息
 * @param startTime 开始供应时间
 */
@Composable
fun IdleStateScreen(
    modifier: Modifier = Modifier,
    message: String = "午餐准备中，11:30 开始供应",
    startTime: String = "11:30"
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF5F5F5),
                        Color(0xFFE8E8E8)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 品牌Logo
            Surface(
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
                    .background(color = Color(0xFFFF6B35), shape = RoundedCornerShape(24.dp)),
                color = Color(0xFFFF6B35)
            ) {
                Text(
                    text = "CAFETERIA",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // 提示消息
            Surface(
                modifier = Modifier
                    .width(600.dp)
                    .height(200.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp)),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = message,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                    
                    Text(
                        text = "供应时间: $startTime - ${getEndTime(startTime)}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF666666),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

/**
 * 根据开始时间获取结束时间
 * @param startTime 开始时间（格式：HH:MM）
 * @return 结束时间（格式：HH:MM）
 */
private fun getEndTime(startTime: String): String {
    // 解析开始时间
    val parts = startTime.split(":")
    if (parts.size != 2) return "13:30" // 默认结束时间
    
    val hour = parts[0].toIntOrNull() ?: 11
    val minute = parts[1].toIntOrNull() ?: 30
    
    // 计算结束时间（假设供应2小时）
    val endHour = (hour + 2) % 24
    return String.format("%02d:%02d", endHour, minute)
}