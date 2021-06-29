NormalToolsProject
==================

描述：自己平时总结和网上找到的的一些工具类项目

## 项目工程目录

- [APP](app)
- [CommonUtils](CommonUtils)---常用Utils
- [LoggerTools](LoggerTools)---Log日志输出类
  - [LoggerUtils.java](LoggerTools/src/main/java/com/pillowcase/logger/LoggerUtils.java)---日志输出、打印
  - [LoggerCrashHandler.java](LoggerTools/src/main/java/com/pillowcase/logger/utils/LoggerCrashHandler.java)---用来处理在程序中未被捕获的异常
- [PermissionUtils](PermissionUtils)---Android6.0（M）系统动态权限申请类
- [PluginUtils](PluginUtils)---检测插件库
  - [GamePlugin.json](PluginUtils/src/main/assets/GamePlugin.json)---游戏外挂检测配置Json文件
  - [SimulatorManager.java](PluginUtils/src/main/java/com/pillowcase/plugin/SimulatorManager.java)---模拟器检测
  - [GamePlugInManager.java](PluginUtils/src/main/java/com/pillowcase/plugin/GamePlugInManager.java)---游戏外挂检测
  - [NotchManager.java](PluginUtils/src/main/java/com/pillowcase/plugin/NotchManager.java)---全面屏配置，刘海屏、点滴屏界面处理
- [resource](resource)---资源
- [ThreadLibrary](ThreadLibrary)---线程池模块
- [UniqueIdentifier](UniqueIdentifier)---移动设备唯一标识工具类
- [README](README.md)---说明文档

## 备注

> 1、[LoggerCrashHandler.java](LoggerTools/src/main/java/com/pillowcase/logger/utils/LoggerCrashHandler.java)的使用  
> 2、[AssetsUtils.java](PluginUtils/src/main/java/com/pillowcase/plugin/utils/AssetsUtils.java)---assets资源读取

## Log

- **Update** _Date : 2021-02-19_

> 1、更新LoggerTools代码，编写[LoggerCrashHandler.java](LoggerTools/src/main/java/com/pillowcase/logger/utils/LoggerCrashHandler.java)逻辑代码  
> 2、[LoggerTools](LoggerTools)版本1.2.0

- **Update** _Date : 2021-02-07_

> 1、更新[PillowLogger.java](LoggerTools/src/main/java/com/pillowcase/logger/LoggerUtils.java)逻辑代码


- **Update** _Date : 2021-02-05_

> 1、新增处理在程序中未被捕获的异常类[LoggerCrashHandler.java](LoggerTools/src/main/java/com/pillowcase/logger/utils/LoggerCrashHandler.java)  
> 2、更新[LoggerUtils.java](LoggerTools/src/main/java/com/pillowcase/logger/LoggerUtils.java)类  
> 3、更新[PluginLog.java](PluginUtils/src/main/java/com/pillowcase/plugin/utils/PluginLog.java)逻辑代码

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

