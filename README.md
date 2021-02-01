![Android](resource/Android.png)

# NormalToolsProject

描述：自己平时总结和网上找到的的一些工具类项目

## 项目工程目录

- [APP](app)
- [CommonUtils](CommonUtils)---常用Utils
- [LoggerTools](LoggerTools)---Log日志输出类
- [PermissionUtils](PermissionUtils)---Android6.0（M）系统动态权限申请类
- [PluginUtils](PluginUtils)---检测插件库
  - [GamePlugin.json](PluginUtils/src/main/assets/agentres/GamePlugin.json)---游戏外挂检测配置Json文件
  - [SimulatorManager.java](PluginUtils/src/main/java/com/pillowcase/plugin/SimulatorManager.java)---模拟器检测
  - [GamePlugInManager.java](PluginUtils/src/main/java/com/pillowcase/plugin/GamePlugInManager.java)---游戏外挂检测
  - [NotchManager.java](PluginUtils/src/main/java/com/pillowcase/plugin/NotchManager.java)---全面屏配置，刘海屏、点滴屏界面处理
- [resource](resource)---资源
- [ThreadLibrary](ThreadLibrary)---线程池模块
- [UniqueIdentifier](UniqueIdentifier)---移动设备唯一标识工具类
- [README](README.md)---说明文档


## Log

- **Update** _Date : 2021-02-01_

> 1、更新ReadMe.md  
> 2、更新模拟器判断检测相关代码  
> 3、添加[NotchManager.java](PluginUtils/src/main/java/com/pillowcase/plugin/NotchManager.java)---全面屏配置，刘海屏、点滴屏界面处理

- **Update** _Date : 2020-12-14_

> 1、增加[PluginUtils](PluginUtils) 检测插件库 V1.0.0  
> 2、修改混淆配置文件  
> 3、**Deprecated**[EmulatorUtils](EmulatorUtils)<!-- @IGNORE PREVIOUS: link -->和[GamePluginDetection](GamePluginDetection)<!-- @IGNORE PREVIOUS: link -->  
> 4、重新整理[README](README.md)说明文档  
> 5、更新代码

