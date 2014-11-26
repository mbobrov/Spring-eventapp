package com.eventapp.prototype.service;

import com.eventapp.prototype.repository.UserRepository;
import com.eventapp.prototype.service.exception.UserAlreadyExistsException;
import com.eventapp.prototype.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Validated
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository repository;

    @Inject
    public UserServiceImpl(final UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public User create(@NotNull @Valid final User user) {
        LOGGER.debug("Creating {}", user);
        List<User> existing = repository.findByName(user.getName());//.findOne(user.getId());
        if (existing.size() > 0) {
            throw new UserAlreadyExistsException("There already exists a user with username: "+user.getName());
        }
        return repository.save(user);
    }

    @Override
    @Transactional
    public User save(@NotNull @Valid final User user) {
        LOGGER.debug("Saving {}", user);
        return repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getList() {
        LOGGER.debug("Retrieving the list of all users");
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        LOGGER.debug("Retrieving user with ID "+userId);
        return repository.findOne(userId);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        LOGGER.debug("Retrieving user with ID "+userId);
        repository.delete(userId);
    }

}
