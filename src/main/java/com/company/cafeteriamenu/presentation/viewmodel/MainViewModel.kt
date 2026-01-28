package com.company.cafeteriamenu.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.cafeteriamenu.domain.model.DailyMenu
import com.company.cafeteriamenu.domain.model.Dish
import com.company.cafeteriamenu.domain.model.MenuConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * 主界面ViewModel
 */
class MainViewModel : ViewModel() {
    
    // UI状态
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    // 模拟数据
    private val mockMenuData = listOf(
        DailyMenu(
            date = "2026-01-26",
            category = "精选套餐",
            dishes = listOf(
                Dish(
                    id = "1",
                    name = "香煎三文鱼",
                    englishName = "Pan-fried Salmon",
                    price = 28.0,
                    description = "香煎三文鱼，搭配新鲜蔬菜",
                    category = "精选套餐",
                    imageUrl = "https://example.com/salmon.jpg",
                    isSpicy = false,
                    isVegetarian = false
                ),
                Dish(
                    id = "2",
                    name = "黑椒牛扒",
                    englishName = "Black Pepper Steak",
                    price = 35.0,
                    description = "精选牛扒，黑椒汁调味",
                    category = "精选套餐",
                    imageUrl = "https://example.com/steak.jpg",
                    isSpicy = true,
                    isVegetarian = false
                ),
                Dish(
                    id = "3",
                    name = "咖喱鸡饭",
                    englishName = "Curry Chicken Rice",
                    price = 22.0,
                    description = "浓郁咖喱，搭配鲜嫩鸡肉",
                    category = "精选套餐",
                    imageUrl = "https://example.com/curry.jpg",
                    isSpicy = true,
                    isVegetarian = false
                ),
                Dish(
                    id = "4",
                    name = "素食套餐",
                    englishName = "Vegetarian Set",
                    price = 18.0,
                    description = "健康素食，营养均衡",
                    category = "精选套餐",
                    imageUrl = "https://example.com/vegetarian.jpg",
                    isSpicy = false,
                    isVegetarian = true
                )
            )
        ),
        DailyMenu(
            date = "2026-01-26",
            category = "风味面档",
            dishes = listOf(
                Dish(
                    id = "5",
                    name = "红烧牛肉面",
                    englishName = "Braised Beef Noodles",
                    price = 22.0,
                    description = "经典红烧牛肉，面条劲道",
                    category = "风味面档",
                    imageUrl = "https://example.com/beef-noodles.jpg",
                    isSpicy = false,
                    isVegetarian = false
                ),
                Dish(
                    id = "6",
                    name = "兰州拉面",
                    englishName = "Lanzhou Beef Noodles",
                    price = 18.0,
                    description = "传统兰州拉面，汤清味美",
                    category = "风味面档",
                    imageUrl = "https://example.com/lanzhou-noodles.jpg",
                    isSpicy = false,
                    isVegetarian = false
                ),
                Dish(
                    id = "7",
                    name = "重庆小面",
                    englishName = "Chongqing Noodles",
                    price = 16.0,
                    description = "麻辣鲜香，地道重庆风味",
                    category = "风味面档",
                    imageUrl = "https://example.com/chongqing-noodles.jpg",
                    isSpicy = true,
                    isVegetarian = false
                )
            )
        ),
        DailyMenu(
            date = "2026-01-26",
            category = "特色小炒",
            dishes = listOf(
                Dish(
                    id = "8",
                    name = "宫保鸡丁",
                    englishName = "Kung Pao Chicken",
                    price = 25.0,
                    description = "经典川菜，香辣可口",
                    category = "特色小炒",
                    imageUrl = "https://example.com/kungpao.jpg",
                    isSpicy = true,
                    isVegetarian = false
                ),
                Dish(
                    id = "9",
                    name = "鱼香肉丝",
                    englishName = "Yuxiang Shredded Pork",
                    price = 22.0,
                    description = "鱼香味浓，酸甜可口",
                    category = "特色小炒",
                    imageUrl = "https://example.com/yuxiang.jpg",
                    isSpicy = true,
                    isVegetarian = false
                ),
                Dish(
                    id = "10",
                    name = "清炒时蔬",
                    englishName = "Stir-fried Vegetables",
                    price = 15.0,
                    description = "新鲜时蔬，清炒健康",
                    category = "特色小炒",
                    imageUrl = "https://example.com/vegetables.jpg",
                    isSpicy = false,
                    isVegetarian = true
                )
            )
        )
    )
    
    // 模拟配置
    private val mockConfig = MenuConfig(
        displayMode = "image",
        scrollSpeed = 50L,
        autoScroll = true,
        refreshInterval = 30,
        serverUrl = "http://localhost",
        port = 3000
    )
    
    /**
     * 应用初始化
     */
    fun initializeApp() {
        viewModelScope.launch {
            try {
                // 模拟网络请求延迟
                delay(2000)
                
                // 模拟获取菜单数据
                val menuData = mockMenuData
                val configs = mockConfig
                val networkStatus = true
                
                // 检查当前时间是否在供餐时间段内
                if (isMealTime()) {
                    // 更新UI状态为成功
                    _uiState.value = UiState.Success(menuData, configs, networkStatus)
                } else {
                    // 非供餐时间，显示空闲状态
                    _uiState.value = UiState.Idle("午餐准备中，11:30 开始供应")
                }
            } catch (e: Exception) {
                // 模拟网络错误
                _uiState.value = UiState.Error("加载菜单数据失败，请检查网络连接")
            }
        }
    }
    
    /**
     * 检查当前时间是否在供餐时间段内
     * @return 是否在供餐时间内
     */
    private fun isMealTime(): Boolean {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        
        // 模拟供餐时间：早餐 6:00-9:00，午餐 11:00-13:30，晚餐 17:00-20:00
        return when (hour) {
            in 6..9 -> true // 早餐
            in 11..13 -> true // 午餐
            in 17..20 -> true // 晚餐
            else -> false // 非供餐时间
        }
    }
    
    /**
     * 重新初始化
     */
    fun retryInitialization() {
        initializeApp()
    }
    
    /**
     * 刷新数据
     */
    fun refreshData() {
        initializeApp()
    }
    
    /**
     * UI状态密封类
     */
    sealed class UiState {
        object Loading : UiState()
        data class Success(val menuData: List<DailyMenu>, val configs: MenuConfig, val networkStatus: Boolean) : UiState()
        data class Error(val message: String) : UiState()
        data class Idle(val message: String) : UiState()
    }
}
