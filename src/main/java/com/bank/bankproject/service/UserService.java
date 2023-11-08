package com.bank.bankproject.service;

import com.bank.bankproject.domain.User;
import com.bank.bankproject.repository.UserRepository;
import com.bank.bankproject.service.dto.UserDto;
import com.bank.bankproject.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service Implementation for managing {@link User}.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;


    @Transactional
    public UserDto save(UserDto userDto) {
        log.debug("Request to save User : {}", userDto);
        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Transactional
    public User save(User user) {
        log.debug("Request to save User : {}", user);
        user = userRepository.save(user);
        return user;
    }

    @Transactional
    public Optional<UserDto> partialUpdate(UserDto userDto) {
        log.debug("Request to partially update User : {}", userDto);

        return userRepository
                .findById(userDto.getId())
                .map(existingUser -> {
                    userMapper.partialUpdate(existingUser, userDto);

                    return existingUser;
                })
                .map(userRepository::save)
                .map(userMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findAll(Pageable pageable) {
        log.debug("Request to get all Users");
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<UserDto> findOne(Long id) {
        log.debug("Request to get User : {}", id);
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete User : {}", id);
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        log.debug("Request to get a User by its id : {}", id);
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        log.debug("Request to get a User by its email : {}", email);
        return userRepository.findByEmail(email);
    }

}
