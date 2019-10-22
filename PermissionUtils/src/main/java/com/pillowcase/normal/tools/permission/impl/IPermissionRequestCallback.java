package com.pillowcase.normal.tools.permission.impl;

/**
 * Author      : PillowCase
 * Create On   : 2019-10-22 14:34
 * Description : 动态申请权限回调
 */
public interface IPermissionRequestCallback {
    /**
     * 申请的权限用户全部授权
     */
    void allGranted();

    /**
     * 拒绝某个权限的授权申请
     *
     * @param permission 权限
     */
    void refused(String permission);
}
