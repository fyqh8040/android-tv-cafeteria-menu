package com.company.cafeteriamenu.presentation.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FireExtinguisher
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Icon
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import com.company.cafeteriamenu.domain.model.Dish

/**
 * 菜品项组件，无图模式下使用
 * @param dish 菜品数据
 * @param categoryColor 分类颜色
 * @param modifier 修饰符
 */
@Composable
fun DishItem(
    dish: Dish,
    categoryColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .width(300.dp)
            .height(120.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // 左侧垂直色块，区分档口类别
            Surface(
                color = categoryColor,
                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
                modifier = Modifier
                    .width(16.dp)
                    .fillMaxHeight()
            ) {}
            
            // 右侧菜品信息
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // 菜品名称和英文名称
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Top
                ) {
                    // 菜品名称（无图模式字号比图片模式大50%）
                    Text(
                        text = dish.name,
                        fontSize = 36.sp, // 比图片模式的24.sp大50%
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333),
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    // 英文名称
                    if (!dish.englishName.isNullOrEmpty()) {
                        Text(
                            text = dish.englishName,
                            fontSize = 24.sp, // 比图片模式的16.sp大50%
                            color = Color(0xFF666666),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        )
                    }
                }
                
                // 价格和标签
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 价格（无图模式字号比图片模式大50%）
                    Text(
                        text = "¥${String.format("%.2f", dish.price)}/${dish.unit}",
                        fontSize = 42.sp, // 比图片模式的28.sp大50%
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6B35)
                    )
                    
                    // 标签（辣度、素食）
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (dish.isSpicy) {
                            Icon(
                                imageVector = Icons.Filled.FireExtinguisher,
                                contentDescription = "辣",
                                tint = Color.Red,
                                modifier = Modifier.size(24.dp).padding(end = 8.dp)
                            )
                        }
                        if (dish.isVegetarian) {
                            Icon(
                                imageVector = Icons.Filled.LocalFlorist,
                                contentDescription = "素食",
                                tint = Color.Green,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}