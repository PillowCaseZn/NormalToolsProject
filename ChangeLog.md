# 更新日志
- ### 1.0.0 - 2020-06-20
    - #### Add  - 增加
        - [CommonUtils](CommonUtils)  常用Utils
    - #### Delete - 删除
        - [NetworkUtils]() 网络模块
        - [UserModel]()  用户登录注册模块
        - [CommonUiLibrary]() 常用UI
    - #### Change - 修改
        - [EmulatorUtils](EmulatorUtils) - 版本号：V1.0.1
          - 包名改为 'com.pillowcase.emulator'
          - [build.gradle](EmulatorUtils/build.gradle)
          - [EmulatorUtils.java](EmulatorUtils/src/main/java/com/pillowcase/emulator/EmulatorUtils.java)
            - 模拟器数据测试
          - 移除LoggerUtils Jar
        - [LoggerTools](LoggerTools)
          - 包名改为 'com.pillowcase.logger'
          - [build.gradle](LoggerTools/build.gradle)
          - 设置Java 版本为Java8
          - 优化代码
        - [UniqueIdentifier](UniqueIdentifier)
          - Library 名改为 UniqueIdentifier(旧->OnlySignUtils)
          - 包名改为 'com.pillowcase.identifier'
          - [build.gradle](UniqueIdentifier/build.gradle)
          - 移除LoggerUtils Jar
          - 'OnlySignUtils.java'改名为'UniqueIdentifierUtils.java'
          - 'OnlySignApplication.java'改名为'UniqueIdentifierApplication.java'
        - [ThreadLibrary](ThreadLibrary)
          - [build.gradle](ThreadLibrary/build.gradle)
        - [PermissionUtils](PermissionUtils)
          - 包名改为 'com.pillowcase.permission'
***
- ### 1.0.0 - 2020-05-08
    - #### Changed - 修改
        - 更新README.md 增加ChangeLog.md
***
### 1.0.0 - 2020-04-09
#### Changed - 修改
- [CommonUiLibrary]()
  - 常用UI
***
### 1.0.0 - 2020-03-31
#### Changed - 修改
- [ThreadLibrary](ThreadLibrary)
  - 线程池模块
- [OnlySignUtils]()
  - 更新
- [NetworkUtils]()
  - 网络模块
- [PermissionUtils](PermissionUtils)
  - 更新Logger库依赖
***
### 1.0.0 - 2020-02-25
#### Changed - 修改
- [UserModel]()
  - 用户登录注册模块
***
### 1.0.0 - 2019-11-26
#### Changed - 修改
- [LoggerTools `Version : 1.0.2`](LoggerTools)
  - BaseLogger.class ==> LoggerBase.class 添加LoggerUtils.class 使用Logger输出日志
***
### 1.0.0 - 2019-11-18
#### Changed - 修改
- [OnlySignUtils `Version : 1.0.1`]()
  - 添加新的返回参数 MAC、AndroidId、DeviceId  更改IMEI的返回数据
- [LoggerTools `Version : 1.0.1`](LoggerTools)
  - 更新BaseLogger.class error方法输出，Throwable的日志打印
***
### 1.0.0 - 2019-11-13
#### Changed - 修改
- [OnlySignUtils `Version : 1.0.0`]()
***
### 1.0.0 - 2019-10-22
#### Changed - 修改
- [EmulatorUtils `Version : 1.0.0`](EmulatorUtils)
- [PermissionUtils `Version : 1.0.0`](PermissionUtils)
  - 添加时，需要注意AndroidManifest.xml、styles.xml的配置
***
### 1.0.0 - 2019-10-16
#### Changed - 修改
- [LoggerUtils `Version : 1.0.0`](LoggerTools)
  - 添加BaseLogger类，实现多个Tag的Log输出
***
