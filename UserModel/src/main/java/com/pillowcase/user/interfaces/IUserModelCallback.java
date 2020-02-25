package com.pillowcase.user.interfaces;

import com.pillowcase.user.model.User;

/**
 * Author      : PillowCase
 * Create On   : 2020-02-25 13:15
 * Description :
 */
public interface IUserModelCallback<T> {
    void onSuccess(User user);

    void onFailed(T error);
}
