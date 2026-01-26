package com.company.cafeteriamenu.presentation.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import com.company.cafeteriamenu.domain.model.DailyMenu
import com.company.cafeteriamenu.presentation.common.AutoScrollingDishList

/**
 * 菜单分类组件
 * @param section 菜单分类数据
 * @param categoryColor 分类颜色
 * @param displayMode 显示模式
 * @param modifier 修饰符
 */
@Composable
fun MenuSection(
    section: DailyMenu,
    categoryColor: Color,
    displayMode: String = "image",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 分类标题
        Text(
            text = section.category,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .background(color = categoryColor, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        
        // 菜品列表
        AutoScrollingDishList(
            modifier = Modifier.fillMaxHeight(),
            items = section.dishes,
            scrollSpeed = 50L, // 默认滚动速度
            autoScroll = true
        ) {
            if (displayMode == "image") {
                DishCard(
                    dish = it,
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp)
                )
            } else {
                DishItem(
                    dish = it,
                    categoryColor = categoryColor,
                    modifier = Modifier
                        .width(300.dp)
                        .padding(4.dp)
                )
            }
        }
    }
}