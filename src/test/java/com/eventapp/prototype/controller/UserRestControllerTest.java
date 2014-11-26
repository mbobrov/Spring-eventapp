package com.eventapp.prototype.controller;

import com.eventapp.prototype.domain.UserCreateForm;
import com.eventapp.prototype.service.UserService;
import com.eventapp.prototype.util.UserUtil;
import com.eventapp.prototype.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerTest {

    @Mock
    private UserService userService;

    private UserRestController userRestController;

    @Before
    public void setUp() throws Exception {
        userRestController = new UserRestController(userService);
    }

    @Test
    public void shouldCreateUser() {
        final User savedUser = stubServiceToReturnStoredUser();
        final UserCreateForm userForm = UserUtil.createUserForm();
        final User user = new User(userForm.getName(), userForm.getEmail(), userForm.getPassword2());
        User returnedUser = userRestController.createUser(userForm);
        // verify user was passed to UserService
        //verify(userService, times(1)).save(user);
        assertEquals("Returned user should come from the service", savedUser, returnedUser);
    }

    private User stubServiceToReturnStoredUser() {
        final User user = UserUtil.createUser();
        when(userService.save(any(User.class))).thenReturn(user);
        return user;
    }

    @Test
    public void shouldListAllUsers() {
        stubServiceToReturnExistingUsers(10);
        Collection<User> users = userRestController.listUsers();
        assertNotNull(users);
        assertEquals(10, users.size());
        // verify user was passed to UserService
        verify(userService, times(1)).getList();
    }

    private void stubServiceToReturnExistingUsers(int howMany) {
        when(userService.getList()).thenReturn(UserUtil.createUserList(howMany));
    }

}
