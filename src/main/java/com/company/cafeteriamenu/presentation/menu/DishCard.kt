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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Card
import androidx.tv.material3.Icon
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.company.cafeteriamenu.domain.model.Dish

/**
 * 菜品卡片组件，图片模式下使用
 * @param dish 菜品数据
 * @param modifier 修饰符
 */
@Composable
fun DishCard(
    dish: Dish,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(300.dp)
            .height(350.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // 菜品图片
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    if (!dish.imageUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = dish.imageUrl,
                            contentDescription = dish.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        // 占位图
                        Surface(
                            color = Color(0xFFF5F5F5),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = dish.name,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF999999)
                                )
                            }
                        }
                    }
                    
                    // 标签（辣度、素食）
                    Row(
                        modifier = Modifier
                            .absoluteTop(8.dp)
                            .absoluteRight(8.dp)
                            .background(color = Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(12.dp))
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        if (dish.isSpicy) {
                            Icon(
                                imageVector = Icons.Filled.FireExtinguisher,
                                contentDescription = "辣",
                                tint = Color.Red,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        if (dish.isVegetarian) {
                            Icon(
                                imageVector = Icons.Filled.LocalFlorist,
                                contentDescription = "素食",
                                tint = Color.Green,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
                
                // 菜品信息
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // 菜品名称
                    Text(
                        text = dish.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333),
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    // 英文名称
                    if (!dish.englishName.isNullOrEmpty()) {
                        Text(
                            text = dish.englishName,
                            fontSize = 16.sp,
                            color = Color(0xFF666666),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        )
                    }
                    
                    // 价格
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "¥${String.format("%.2f", dish.price)}/${dish.unit}",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF6B35)
                        )
                    }
                    
                    // 菜品描述
                    if (!dish.description.isNullOrEmpty()) {
                        Text(
                            text = dish.description,
                            fontSize = 14.sp,
                            color = Color(0xFF999999),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}