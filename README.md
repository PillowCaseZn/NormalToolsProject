![Android](Android.png)
# NormalToolsProject
描述：自己平时总结和网上找到的的一些工具类项目

## Usage - 用法
- [EmulatorUtils](EmulatorUtils) - 模拟器检测类
   ```
   new EmulatorUtils(this, new IEmulatorCheckListener() {

        @Override
        public void result(boolean isEmulator, String info) {
            log("result", "是否是模拟器 : " + isEmulator + "\n" + info);
        }
    });
    ```
- [CommonUtils](CommonUtils) 
    - 常用Utils
- [AssetsUtils.java](CommonUtils/src/main/java/com/pillowcase/utils/AssetsUtils.java)
    ```
    ```
- [CountTimeUtils.java](CommonUtils/src/main/java/com/pillowcase/utils/CountTimeUtils.java)
    ```
    ```


## Description - 描述
- [LoggerTools](LoggerTools) - Log日志输出类 版本号：V1.1.0
- [LoggerUtils.java](LoggerTools/src/main/java/com/pillowcase/logger/LoggerUtils.java) - 日志格式化输出
- [EmulatorUtils](EmulatorUtils) - 模拟器检测类
- [EmulatorUtils.java](EmulatorUtils/src/main/java/com/pillowcase/emulator/EmulatorUtils.java) - 模拟器识别
- [PermissionUtils](PermissionUtils) - Android6.0（M）系统动态权限申请类
- [UniqueIdentifier](UniqueIdentifier) - 移动设备唯一标识工具类
- [ThreadLibrary](ThreadLibrary) - 线程池模块
- [CommonUtils](CommonUtils) - 常用Utils
    - [AssetsUtils.java](CommonUtils/src/main/java/com/pillowcase/utils/AssetsUtils.java)
        - 读取assets文件夹下的文件内容
    - [CountTimeUtils.java](CommonUtils/src/main/java/com/pillowcase/utils/CountTimeUtils.java)
        - 倒计时
- [GamePluginDetection](GamePluginDetection) - 游戏外挂检查类

## ChangeLog - [更新日志](ChangeLog.md)
#### 1.0.0 - 2020-07-28
- Add  - 新增
  - [GamePluginDetection](GamePluginDetection) - 游戏外挂检查类

