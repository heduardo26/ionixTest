package com.example.service;

import com.example.entity.UserEntity;
import com.example.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo mockUserRepo;

    @InjectMocks
    private UserService userService;

    private UserService userServiceUnderTest;

    @BeforeEach
    void setUp() {
        userServiceUnderTest = new UserService(mockUserRepo);
        //MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByEmail(){
        UserEntity userMock = new UserEntity(1L, "userName", "name", "email@mail.com", "phone");
        when(mockUserRepo.findByEmail(anyString())).thenReturn(userMock);
        UserEntity user = userService.getByEmail("email@mail.com");
        assertThat(user).isNotNull();
        assertThat("userName").isEqualTo(user.getUserName());
    }

    @Test
    void testGetAllUsers() {
        // Setup
        final List<UserEntity> expectedResult = List.of(new UserEntity(0L, "userName", "name", "email", "phone"));

        // Configure UserRepo.findAll(...).
        final List<UserEntity> userEntities = List.of(new UserEntity(0L, "userName", "name", "email", "phone"));
        when(mockUserRepo.findAll()).thenReturn(userEntities);

        // Run the test
        final List<UserEntity> result = userServiceUnderTest.getAllUsers();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllUsers_UserRepoReturnsNoItems() {
        // Setup
        when(mockUserRepo.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<UserEntity> result = userServiceUnderTest.getAllUsers();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetByEmail() {
        // Setup
        final UserEntity expectedResult = new UserEntity(0L, "userName", "name", "email", "phone");

        // Configure UserRepo.findByEmail(...).
        final UserEntity user = new UserEntity(0L, "userName", "name", "email", "phone");
        when(mockUserRepo.findByEmail("email")).thenReturn(user);

        // Run the test
        final UserEntity result = userServiceUnderTest.getByEmail("email");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testDeleteUser() {
        // Setup
        // Configure UserRepo.findByEmail(...).
        final UserEntity user = new UserEntity(0L, "userName", "name", "email", "phone");
        when(mockUserRepo.findByEmail("email")).thenReturn(user);

        when(mockUserRepo.deleteByEmail("email")).thenReturn(0L);

        // Run the test
        userServiceUnderTest.deleteUser("email");

        // Verify the results
        verify(mockUserRepo).deleteByEmail("email");
    }

    @Test
    void testAddUser() {
        // Setup
        final UserEntity user = new UserEntity(0L, "userName", "name", "email", "phone");

        // Configure UserRepo.findByEmail(...).
        final UserEntity user1 = new UserEntity(0L, "userName", "name", "email", "phone");
        when(mockUserRepo.findByEmail("email")).thenReturn(user1);

        // Configure UserRepo.save(...).
        final UserEntity user2 = new UserEntity(0L, "userName", "name", "email", "phone");
        when(mockUserRepo.save(new UserEntity(0L, "userName", "name", "email", "phone"))).thenReturn(user2);

        // Run the test
        userServiceUnderTest.addUser(user);

        // Verify the results
        verify(mockUserRepo).save(new UserEntity(0L, "userName", "name", "email", "phone"));
    }
}
