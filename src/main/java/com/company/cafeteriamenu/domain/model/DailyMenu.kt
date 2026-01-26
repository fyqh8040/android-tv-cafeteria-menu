package com.company.cafeteriamenu.domain.model

/**
 * 每日菜单模型
 * @param date 日期
 * @param category 分类
 * @param dishes 菜品列表
 */
data class DailyMenu(
    val date: String,
    val category: String,
    val dishes: List<Dish>
)

/**
 * 菜品模型
 * @param id 菜品ID
 * @param name 菜品名称
 * @param englishName 英文名称
 * @param price 价格
 * @param unit 单位
 * @param description 描述
 * @param category 分类
 * @param imageUrl 图片URL
 * @param isSpicy 是否辣
 * @param isVegetarian 是否素食
 * @param sortOrder 排序顺序
 */
data class Dish(
    val id: String,
    val name: String,
    val englishName: String? = null,
    val price: Double,
    val unit: String = "份",
    val description: String? = null,
    val category: String? = null,
    val imageUrl: String? = null,
    val isSpicy: Boolean = false,
    val isVegetarian: Boolean = false,
    val sortOrder: Int = 0
)

/**
 * 菜单配置模型
 * @param displayMode 显示模式：image（图片模式）或no_image（无图模式）
 * @param scrollSpeed 滚动速度（毫秒/像素）
 * @param autoScroll 是否自动滚动
 * @param refreshInterval 刷新间隔（分钟）
 * @param serverUrl 服务器URL
 * @param port 端口
 */
data class MenuConfig(
    val displayMode: String = "image",
    val scrollSpeed: Long = 50L,
    val autoScroll: Boolean = true,
    val refreshInterval: Int = 30,
    val serverUrl: String = "http://localhost",
    val port: Int = 3000
)