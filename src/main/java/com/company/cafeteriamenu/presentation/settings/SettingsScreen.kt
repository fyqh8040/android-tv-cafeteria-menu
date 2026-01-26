package com.company.cafeteriamenu.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.Text

/**
 * 设置页面组件
 * @param modifier 修饰符
 * @param onBack 返回按钮回调
 */
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    // 服务器配置
    var serverAddress by remember { mutableStateOf(TextFieldValue("http://localhost")) }
    var port by remember { mutableStateOf(TextFieldValue("3000")) }
    
    // 显示设置
    var displayMode by remember { mutableStateOf("image") } // image 或 no_image
    var scrollSpeed by remember { mutableStateOf(50L) }
    var autoScroll by remember { mutableStateOf(true) }
    var refreshInterval by remember { mutableStateOf(30) }
    
    // 测试连接状态
    var testResult by remember { mutableStateOf("未测试") }
    var isTesting by remember { mutableStateOf(false) }
    
    // 保存设置状态
    var saveResult by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }
    
    // 导航状态
    var currentTab by remember { mutableStateOf("server_config") }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        // 左侧导航栏
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(240.dp)
                .fillMaxHeight()
                .background(Color(0xFF1E1E1E)),
            verticalArrangement = Arrangement.Top
        ) {
            // 标题
            Text(
                text = "Admin Settings",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            
            // 导航项
            NavigationItem(
                title = "服务器配置",
                isSelected = currentTab == "server_config",
                onClick = { currentTab = "server_config" }
            )
            
            NavigationItem(
                title = "显示设置",
                isSelected = currentTab == "display_settings",
                onClick = { currentTab = "display_settings" }
            )
            
            NavigationItem(
                title = "Wi-Fi设置",
                isSelected = currentTab == "wifi_settings",
                onClick = { currentTab = "wifi_settings" }
            )
            
            NavigationItem(
                title = "关于信息",
                isSelected = currentTab == "about",
                onClick = { currentTab = "about" }
            )
        }
        
        // 右侧配置区
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxWidth()
                .padding(start = 240.dp)
                .fillMaxHeight()
                .background(Color(0xFF121212))
                .padding(32.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // 顶部栏
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (currentTab) {
                        "server_config" -> "服务器配置"
                        "display_settings" -> "显示设置"
                        "wifi_settings" -> "Wi-Fi设置"
                        "about" -> "关于信息"
                        else -> "设置"
                    },
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Button(
                    onClick = onBack,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "返回")
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // 配置内容
            when (currentTab) {
                "server_config" -> {
                    ServerConfigSection(
                        serverAddress = serverAddress,
                        onServerAddressChange = { serverAddress = it },
                        port = port,
                        onPortChange = { port = it },
                        testResult = testResult,
                        isTesting = isTesting,
                        onTestConnection = {
                            // 模拟测试连接
                            isTesting = true
                            testResult = "测试中..."
                            // 这里应该调用实际的测试连接逻辑
                            // 模拟延迟
                            kotlinx.coroutines.GlobalScope.launch {
                                kotlinx.coroutines.delay(2000)
                                testResult = "连接成功"
                                isTesting = false
                            }
                        },
                        saveResult = saveResult,
                        isSaving = isSaving,
                        onSave = {
                            // 模拟保存设置
                            isSaving = true
                            saveResult = "保存中..."
                            // 这里应该调用实际的保存逻辑
                            // 模拟延迟
                            kotlinx.coroutines.GlobalScope.launch {
                                kotlinx.coroutines.delay(1500)
                                saveResult = "保存成功"
                                isSaving = false
                            }
                        }
                    )
                }
                "display_settings" -> {
                    DisplaySettingsSection(
                        displayMode = displayMode,
                        onDisplayModeChange = { displayMode = it },
                        scrollSpeed = scrollSpeed,
                        onScrollSpeedChange = { scrollSpeed = it },
                        autoScroll = autoScroll,
                        onAutoScrollChange = { autoScroll = it },
                        refreshInterval = refreshInterval,
                        onRefreshIntervalChange = { refreshInterval = it }
                    )
                }
                "wifi_settings" -> {
                    // Wi-Fi设置内容
                    Text(
                        text = "Wi-Fi设置页面",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
                "about" -> {
                    // 关于信息内容
                    AboutSection()
                }
            }
        }
    }
}

/**
 * 导航项组件
 * @param title 标题
 * @param isSelected 是否选中
 * @param onClick 点击回调
 */
@Composable
fun NavigationItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF3498DB) else Color.Transparent
        )
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = if (isSelected) Color.White else Color(0xFFC1C1C1)
        )
    }
}

/**
 * 服务器配置部分
 */
@Composable
fun ServerConfigSection(
    serverAddress: TextFieldValue,
    onServerAddressChange: (TextFieldValue) -> Unit,
    port: TextFieldValue,
    onPortChange: (TextFieldValue) -> Unit,
    testResult: String,
    isTesting: Boolean,
    onTestConnection: () -> Unit,
    saveResult: String,
    isSaving: Boolean,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 服务器地址
        SettingItem(
            label = "服务器地址",
            description = "输入Web后台服务器的IP地址或域名"
        ) {
            TextField(
                value = serverAddress,
                onValueChange = onServerAddressChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Uri
                ),
                textStyle = TextStyle(fontSize = 18.sp, color = Color.White),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2C2C2C),
                    unfocusedContainerColor = Color(0xFF2C2C2C),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color(0xFF3498DB),
                    unfocusedIndicatorColor = Color(0xFF79747E)
                )
            )
        }
        
        // 端口
        SettingItem(
            label = "端口",
            description = "输入Web后台服务器的端口号"
        ) {
            TextField(
                value = port,
                onValueChange = onPortChange,
                modifier = Modifier
                    .width(120.dp)
                    .height(60.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                textStyle = TextStyle(fontSize = 18.sp, color = Color.White),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2C2C2C),
                    unfocusedContainerColor = Color(0xFF2C2C2C),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color(0xFF3498DB),
                    unfocusedIndicatorColor = Color(0xFF79747E)
                )
            )
        }
        
        // 测试连接和保存按钮
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 测试连接按钮
            Button(
                onClick = onTestConnection,
                enabled = !isTesting,
                modifier = Modifier.height(60.dp)
            ) {
                Text(
                    text = if (isTesting) "测试中..." else "测试连接"
                )
            }
            
            // 测试结果
            Text(
                text = testResult,
                fontSize = 18.sp,
                color = when (testResult) {
                    "连接成功" -> Color(0xFF2ECC71)
                    "测试中..." -> Color(0xFFF39C12)
                    else -> Color(0xFFC1C1C1)
                },
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
            )
            
            // 保存按钮
            Button(
                onClick = onSave,
                enabled = !isSaving,
                modifier = Modifier.height(60.dp)
            ) {
                Text(
                    text = if (isSaving) "保存中..." else "保存"
                )
            }
        }
        
        // 保存结果
        if (saveResult.isNotEmpty()) {
            Text(
                text = saveResult,
                fontSize = 18.sp,
                color = when (saveResult) {
                    "保存成功" -> Color(0xFF2ECC71)
                    "保存中..." -> Color(0xFFF39C12)
                    else -> Color(0xFFE74C3C)
                }
            )
        }
    }
}

/**
 * 显示设置部分
 */
@Composable
fun DisplaySettingsSection(
    displayMode: String,
    onDisplayModeChange: (String) -> Unit,
    scrollSpeed: Long,
    onScrollSpeedChange: (Long) -> Unit,
    autoScroll: Boolean,
    onAutoScrollChange: (Boolean) -> Unit,
    refreshInterval: Int,
    onRefreshIntervalChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 显示模式
        SettingItem(
            label = "显示模式",
            description = "选择菜单的显示模式：图片模式或无图模式"
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { onDisplayModeChange("image") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (displayMode == "image") Color(0xFF3498DB) else Color(0xFF2C2C2C)
                    )
                ) {
                    Text(text = "图片模式")
                }
                
                Button(
                    onClick = { onDisplayModeChange("no_image") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (displayMode == "no_image") Color(0xFF3498DB) else Color(0xFF2C2C2C)
                    )
                ) {
                    Text(text = "无图模式")
                }
            }
        }
        
        // 滚动速度
        SettingItem(
            label = "滚动速度",
            description = "调整菜品自动滚动的速度，数值越大滚动越慢"
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${scrollSpeed}ms/像素",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(end = 16.dp)
                )
                
                Slider(
                    value = scrollSpeed.toFloat(),
                    onValueChange = { onScrollSpeedChange(it.toLong()) },
                    modifier = Modifier.weight(1f),
                    valueRange = 10f..200f,
                    steps = 19,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF3498DB),
                        activeTrackColor = Color(0xFF3498DB),
                        inactiveTrackColor = Color(0xFF2C2C2C)
                    )
                )
            }
        }
        
        // 自动滚动
        SettingItem(
            label = "自动滚动",
            description = "开启或关闭菜品自动滚动功能"
        ) {
            Switch(
                checked = autoScroll,
                onCheckedChange = onAutoScrollChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF3498DB),
                    checkedTrackColor = Color(0xFF3498DB),
                    uncheckedThumbColor = Color(0xFF79747E),
                    uncheckedTrackColor = Color(0xFF2C2C2C)
                )
            )
        }
        
        // 刷新间隔
        SettingItem(
            label = "刷新间隔",
            description = "设置菜单数据的自动刷新间隔（分钟）"
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${refreshInterval}分钟",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(end = 16.dp)
                )
                
                Slider(
                    value = refreshInterval.toFloat(),
                    onValueChange = { onRefreshIntervalChange(it.toInt()) },
                    modifier = Modifier.weight(1f),
                    valueRange = 5f..60f,
                    steps = 11,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF3498DB),
                        activeTrackColor = Color(0xFF3498DB),
                        inactiveTrackColor = Color(0xFF2C2C2C)
                    )
                )
            }
        }
    }
}

/**
 * 关于信息部分
 */
@Composable
fun AboutSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "员工食堂菜单TV客户端",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        
        Text(
            text = "版本：1.0.0",
            fontSize = 18.sp,
            color = Color(0xFFC1C1C1)
        )
        
        Text(
            text = "专为Android TV设备设计，用于显示员工食堂菜单信息",
            fontSize = 16.sp,
            color = Color(0xFFC1C1C1),
            modifier = Modifier.padding(top = 16.dp)
        )
        
        Text(
            text = "支持Android 7.0及以上版本",
            fontSize = 16.sp,
            color = Color(0xFFC1C1C1)
        )
        
        Text(
            text = "© 2026 公司名称",
            fontSize = 16.sp,
            color = Color(0xFFC1C1C1),
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}

/**
 * 设置项组件
 * @param label 标签
 * @param description 描述
 * @param content 内容
 */
@Composable
fun SettingItem(
    label: String,
    description: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        
        Text(
            text = description,
            fontSize = 14.sp,
            color = Color(0xFF79747E),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        content()
    }
}