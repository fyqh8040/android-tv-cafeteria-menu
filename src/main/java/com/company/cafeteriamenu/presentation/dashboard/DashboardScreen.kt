package com.company.cafeteriamenu.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.*
import com.company.cafeteriamenu.domain.model.DailyMenu
import com.company.cafeteriamenu.domain.model.MenuConfig
import com.company.cafeteriamenu.presentation.common.AutoScrollingDishList
import com.company.cafeteriamenu.presentation.menu.DishCard
import com.company.cafeteriamenu.presentation.menu.DishItem
import com.company.cafeteriamenu.presentation.menu.MenuSection
import java.text.SimpleDateFormat
import java.util.*

/**
 * 主界面组件，显示今日菜单
 * @param modifier 修饰符
 * @param menuData 今日菜单数据
 * @param configs 菜单配置
 * @param networkStatus 网络状态
 * @param onRefresh 刷新回调
 */
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    menuData: List<DailyMenu> = emptyList(),
    configs: MenuConfig = MenuConfig(),
    networkStatus: Boolean = true,
    onRefresh: () -> Unit = {}
) {
    // 获取当前时间和日期
    val currentTime = remember {
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.CHINA)
        mutableStateOf(formatter.format(Date()))
    }
    val currentDate = remember {
        val formatter = SimpleDateFormat("yyyy年MM月dd日 EEEE", Locale.CHINA)
        formatter.format(Date())
    }
    
    // 定期更新时间
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1000)
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.CHINA)
        currentTime.value = formatter.format(Date())
    }
    
    // 确定当前餐段
    val currentMealPeriod = remember {
        determineCurrentMealPeriod()
    }
    
    // 根据配置选择显示模式
    val displayMode = configs.displayMode
    
    Surface(
        modifier = modifier,
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 顶部栏
            Header(
                brandLogo = "CAFETERIA HUB",
                date = currentDate,
                time = currentTime.value,
                currentMealPeriod = currentMealPeriod
            )
            
            // 主体内容
            Body(
                menuData = menuData,
                displayMode = displayMode,
                configs = configs
            )
            
            // 底部栏
            Footer()
        }
    }
}

/**
 * 确定当前餐段
 * @return 当前餐段名称
 */
private fun determineCurrentMealPeriod(): String {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    
    return when (hour) {
        in 6..9 -> "今日早餐 BREAKFAST MENU"
        in 10..13 -> "今日午餐 LUNCH MENU"
        in 17..20 -> "今日晚餐 DINNER MENU"
        else -> "非供餐时间"
    }
}

/**
 * 顶部栏组件
 * @param brandLogo 品牌Logo
 * @param date 当前日期
 * @param time 当前时间
 * @param currentMealPeriod 当前餐段
 */
@Composable
private fun Header(
    brandLogo: String,
    date: String,
    time: String,
    currentMealPeriod: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth().height(80.dp),
        color = Color(0xFFF5F5F5)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 左侧：品牌Logo、日期、时间
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = brandLogo,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6B35),
                    modifier = Modifier.padding(end = 24.dp)
                )
                
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = date,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF333333)
                    )
                    Text(
                        text = time,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF333333)
                    )
                }
            }
            
            // 中间：当前餐段
            Text(
                text = currentMealPeriod,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            
            // 右侧：留空
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

/**
 * 主体内容组件
 * @param menuData 今日菜单数据
 * @param displayMode 显示模式
 * @param configs 菜单配置
 */
@Composable
private fun Body(
    menuData: List<DailyMenu>,
    displayMode: String,
    configs: MenuConfig
) {
    Surface(
        modifier = Modifier.weight(1f),
        color = Color.White
    ) {
        if (menuData.isNotEmpty()) {
            // 根据显示模式选择不同的布局
            when (displayMode) {
                "image" -> {
                    ImageModeLayout(
                        menuData = menuData,
                        configs = configs
                    )
                }
                "no_image" -> {
                    NoImageModeLayout(
                        menuData = menuData,
                        configs = configs
                    )
                }
                else -> {
                    ImageModeLayout(
                        menuData = menuData,
                        configs = configs
                    )
                }
            }
        } else {
            // 菜单数据为空时显示提示
            EmptyState()
        }
    }
}

/**
 * 图片模式布局
 * @param menuData 今日菜单数据
 * @param configs 菜单配置
 */
@Composable
private fun ImageModeLayout(
    menuData: List<DailyMenu>,
    configs: MenuConfig
) {
    // 使用三列网格布局
    Row(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // 每列显示一个分类的菜品
        menuData.forEachIndexed { index, menu ->
            // 根据分类设置不同的背景色
            val categoryColor = when (index % 3) {
                0 -> Color(0xFFFF6B35) // 活力橙
                1 -> Color(0xFFD93A3A) // 深红
                2 -> Color(0xFF2ECC71) // 活力绿
                else -> Color(0xFFFF6B35)
            }
            
            Column(
                modifier = Modifier.weight(1f).padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 分类标题
                Text(
                    text = menu.category,
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
                    items = menu.dishes,
                    scrollSpeed = configs.scrollSpeed,
                    autoScroll = configs.autoScroll
                ) {
                    DishCard(
                        dish = it,
                        modifier = Modifier
                            .width(300.dp)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

/**
 * 无图模式布局
 * @param menuData 今日菜单数据
 * @param configs 菜单配置
 */
@Composable
private fun NoImageModeLayout(
    menuData: List<DailyMenu>,
    configs: MenuConfig
) {
    // 使用三列网格布局
    Row(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // 每列显示一个分类的菜品
        menuData.forEachIndexed { index, menu ->
            // 根据分类设置不同的背景色
            val categoryColor = when (index % 3) {
                0 -> Color(0xFFFF6B35) // 活力橙
                1 -> Color(0xFFD93A3A) // 深红
                2 -> Color(0xFFFF6B35) // 活力绿
                else -> Color(0xFFFF6B35)
            }
            
            Column(
                modifier = Modifier.weight(1f).padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 分类标题
                Text(
                    text = menu.category,
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
                    items = menu.dishes,
                    scrollSpeed = configs.scrollSpeed,
                    autoScroll = configs.autoScroll
                ) {
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
}

/**
 * 底部栏组件
 */
@Composable
private fun Footer() {
    Surface(
        modifier = Modifier.height(40.dp),
        color = Color.Transparent
    ) {
        // 隐藏或仅显示滚动提示
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "向上滚动查看更多菜品",
                fontSize = 14.sp,
                color = Color(0xFF999999),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * 空状态组件
 */
@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "暂无菜单数据",
            fontSize = 24.sp,
            color = Color(0xFF999999)
        )
    }
}