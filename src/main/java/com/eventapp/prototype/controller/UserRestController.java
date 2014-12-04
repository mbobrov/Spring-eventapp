package com.eventapp.prototype.controller;

import com.eventapp.prototype.domain.UserCreateForm;
import com.eventapp.prototype.service.UserService;
import com.eventapp.prototype.service.exception.UserAlreadyExistsException;
import com.eventapp.prototype.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);
    private final UserService userService;

    @Inject
    public UserRestController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User createUser(@RequestBody @Valid final UserCreateForm form) {//}, BindingResult result) {
        LOGGER.debug("Received request to create User with data={}", form);
        User usr = new User(form.getName(), form.getEmail(), form.getPassword2());
        usr.setFirstname(form.getFirstname());
        usr.setLastname(form.getLastname());
        User u = userService.create(usr);
        LOGGER.debug("Result of SAVE: "+u.toString());
        return u;
    }

    @RequestMapping(value = "/username/{name}", method = RequestMethod.GET)
    public Boolean checkUserName(@PathVariable String name) {
        return new Boolean(userService.checkName(name));
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public User saveUser(@PathVariable Long id, @RequestBody @Valid final User form) {//}, BindingResult result) {
        LOGGER.debug("Received request to create User with id {} and data={}", id, form);
        userService.getUser(id);
        User u = userService.save(form);
        LOGGER.debug("Result of SAVE: "+u.toString());
        return u;
    }

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long id) {//}, BindingResult result) {
        LOGGER.debug("Received delete of User with id={}", id);
        userService.deleteUser(id);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> listUsers() {
        LOGGER.debug("Received request to list all users");
        return userService.getList();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        LOGGER.debug("Received request to retrieve User with id={}", id);
        User usr = userService.getUser(id);
        return usr;
    }

    //@ExceptionHandler
    //@ResponseStatus(HttpStatus.CONFLICT)
    /*public String handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return e.getMessage();
    }*/

}
