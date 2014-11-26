package com.eventapp.prototype.util;

import com.eventapp.prototype.domain.User;
import com.eventapp.prototype.domain.UserCreateForm;

import java.util.ArrayList;
import java.util.List;

public class UserUtil {

    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    private UserUtil() {
    }

    public static User createUser() {
        return new User(NAME, EMAIL, PASSWORD);
    }
    public static UserCreateForm createUserForm() {
        UserCreateForm form = new UserCreateForm();
        form.setName(NAME);
        form.setEmail(EMAIL);
        form.setPassword1(PASSWORD);
        form.setPassword2(PASSWORD);
        return form;
    }

    public static List<User> createUserList(int howMany) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            userList.add(new User(NAME + "#" + i, EMAIL+i, PASSWORD));
        }
        return userList;
    }

}
