![Android](Android.png)
# NormalToolsProject
描述：自己平时总结和网上找到的的一些工具类项目

## Usage - 用法

## Description - 描述
- [LoggerTools](LoggerTools) - Log日志输出类
- [EmulatorUtils](EmulatorUtils) - 模拟器检测类
- [PermissionUtils](PermissionUtils) - Android6.0（M）系统动态权限申请类
- [OnlySignUtils](OnlySignUtils) - 移动设备唯一标识工具类
- [ThreadLibrary](ThreadLibrary) - 线程池模块
- [CommonUtils](CommonUtils) - 常用Utils

## ChangeLog - [更新日志](ChangeLog.md)
- ### 1.0.0 - 2020-06-20
    - #### Add  - 增加
        - [CommonUtils](CommonUtils)  常用Utils
    - #### Delete - 删除
        - [NetworkUtils]() 网络模块
        - [UserModel]()  用户登录注册模块
        - [CommonUiLibrary]() 常用UI
    - #### Chanage - 修改
        - [EmulatorUtils](EmulatorUtils)
            - [build.gradle](EmulatorUtils/build.gradle)
        - [LoggerTools](LoggerTools)
            - [build.gradle](LoggerTools/build.gradle)
                - 设置Java 版本为Java8
        - [OnlySignUtils](OnlySignUtils)
            - [build.gradle](EmulatorUtils/build.gradle)
            - 移除LoggerUtils Jar
        - [OnlySignUtils](OnlySignUtils)
            - [build.gradle](OnlySignUtils/build.gradle)
            - 移除LoggerUtils Jar
        - [ThreadLibrary](ThreadLibrary)
            - [build.gradle](ThreadLibrary/build.gradle)