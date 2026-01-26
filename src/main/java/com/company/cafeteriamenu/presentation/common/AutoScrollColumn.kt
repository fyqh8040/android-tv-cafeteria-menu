package com.company.cafeteriamenu.presentation.common

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 自动滚动列组件
 * @param modifier 修饰符
 * @param items 要显示的项目列表
 * @param scrollSpeed 滚动速度（毫秒/像素）
 * @param autoScroll 是否自动滚动
 * @param content 项目内容
 */
@Composable
fun <T> AutoScrollColumn(
    modifier: Modifier = Modifier,
    items: List<T>,
    scrollSpeed: Long = 50L,
    autoScroll: Boolean = true,
    content: @Composable (T) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    
    // 自动滚动逻辑
    LaunchedEffect(items.size, autoScroll) {
        if (autoScroll && items.isNotEmpty()) {
            while (true) {
                // 平滑滚动到下一个位置
                coroutineScope.launch {
                    val currentIndex = listState.firstVisibleItemIndex
                    val nextIndex = (currentIndex + 1) % items.size
                    
                    // 计算滚动距离
                    val distance = if (nextIndex == 0) {
                        // 如果到了最后一项，滚动到顶部
                        listState.layoutInfo.totalItemsCount - currentIndex
                    } else {
                        1
                    }
                    
                    // 平滑滚动
                    listState.animateScrollToItem(nextIndex)
                }
                
                // 等待一段时间后继续滚动
                delay(3000) // 每3秒滚动一次
            }
        }
    }
    
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 150.dp) // 底部留空，配合渐隐效果
        ) {
            items(items) {
                content(it)
            }
        }
        
        // 底部渐隐效果
        BottomFadeEffect()
    }
}

/**
 * 底部渐隐效果组件
 */
@Composable
fun BottomFadeEffect() {
    Surface(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .height(100.dp),
        color = Color.Transparent,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White
                            ),
                            startY = 0f,
                            endY = 100f
                        )
                    )
            ) {}
        }
    )
}

/**
 * 带自动滚动的菜品列表组件
 * @param modifier 修饰符
 * @param items 菜品列表
 * @param scrollSpeed 滚动速度
 * @param autoScroll 是否自动滚动
 * @param content 菜品内容
 */
@Composable
fun <T> AutoScrollingDishList(
    modifier: Modifier = Modifier,
    items: List<T>,
    scrollSpeed: Long = 50L,
    autoScroll: Boolean = true,
    content: @Composable (T) -> Unit
) {
    Box(modifier = modifier) {
        AutoScrollColumn(
            modifier = Modifier.fillMaxSize(),
            items = items,
            scrollSpeed = scrollSpeed,
            autoScroll = autoScroll
        ) {
            content(it)
        }
    }
}