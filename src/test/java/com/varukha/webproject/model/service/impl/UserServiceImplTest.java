package com.varukha.webproject.model.service.impl;

import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.model.entity.User;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.UserDAO;
import com.varukha.webproject.model.dao.impl.UserDAOImpl;
import com.varukha.webproject.model.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import org.mockito.Mockito;

import java.math.BigDecimal;

import java.util.*;

import static com.varukha.webproject.command.ParameterAndAttribute.USER_EMAIL;
import static com.varukha.webproject.command.ParameterAndAttribute.USER_PASSWORD;
import static com.varukha.webproject.util.Encoder.encodePassword;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


class UserServiceImplTest extends Assertions {

    private static UserDAO userDAO;
    private static User user;

    private static UserService userService;
    private static Map<String, String> userData;

    private static final List<User> users = new ArrayList<>();


    @BeforeAll
    public static void setUp() {
        userDAO = mock(UserDAOImpl.class);
        userService = new UserServiceImpl(userDAO);
        userData = new HashMap<>();

        userData.put(ParameterAndAttribute.USER_NAME, "Victor");
        userData.put(ParameterAndAttribute.USER_SURNAME, "Bondarenko");
        userData.put(USER_EMAIL, "victor@gmail.com");
        userData.put(ParameterAndAttribute.USER_PHONE, "+380668996655");
        userData.put(USER_PASSWORD, "Qwer1234");
        userData.put(ParameterAndAttribute.USER_ROLE, "USER");
        userData.put(ParameterAndAttribute.USER_ACCOUNT, "0");


      user = new User.Builder()
                .setName("Victor")
                .setSurname("Bondarenko")
                .setEmail("victor@gmail.com")
                .setPhone("+380668996655")
                .setUserPassword(encodePassword("Qaz123Zaq"))
                .setRole(User.Role.USER)
                .setPaymentAccount(BigDecimal.valueOf(0))
                .build();

        users.add(user);
    }

    @Test
    void testAddUser() throws ServiceException, DAOException {
        boolean expectedResult = true;
        when(userDAO.addUser(isA(User.class), isA(String.class))).thenReturn(true);
        boolean actualResult = userService.addUser(userData);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    void testGetAllUsers() throws ServiceException, DAOException {
        when(userDAO.findAllUsers()).thenReturn(users);
        assertEquals(users, userService.getAllUsers());
    }

//    @Test
//    void testGetUserEmailPassword() throws ServiceException, DAOException {
//        Optional<User> optionalUser;
//        optionalUser = Optional.of(user);
//        String userEmail = userData.get(USER_EMAIL);
//        String userPassword = userData.get(USER_PASSWORD);
//        String encodedPassword = encodePassword(String.valueOf(userPassword));
//
//
//        when(userDAO.findUserEmailPassword(isA(String.class))).thenReturn(optionalUser);
//        assertEquals(encodedPassword, userService.getUserEmailPassword(userEmail, userPassword));
//
//
//        User userByEmailPassword = getTestUser();
//        user.setPassword(encodePassword(USER_PASSWORD));
//        when(userDAO.findUserEmailPassword(USER_EMAIL)).thenReturn(Optional.of(userByEmailPassword));
//        assertEquals(userByEmailPassword, userService.getUserEmailPassword(USER_EMAIL, USER_PASSWORD));
//    }

    @Test
    void testGetUserByEmail() throws DAOException, ServiceException {
       Optional<User> optionalUser;
        optionalUser = Optional.of(user);
        String  userEmail = "victor@gmail.com";
        Mockito.when(userDAO.findUserByEmail(userEmail)).thenReturn(optionalUser);
        assertEquals(optionalUser, userService.getUserByEmail(userEmail));
    }

//    @Test
//    void testGetUserById() throws DAOException, ServiceException {
//        long userId = 1;
//        when(userDAO.findUserById(userId)).thenReturn(users);
//        assertEquals(users, userService.getUserById(userId));
//
//    }

    @Test
    void addMoneyToUserAccount() throws ServiceException, DAOException {
        String userAccount = "1500";
        long userId = 1;
        userService.addMoneyToUserAccount(userId, userAccount);
        Mockito.verify(userDAO, Mockito.times(1)).topUpUserAccount(userId, userAccount);

    }

    @Test
    void payForDeliveryService() throws ServiceException, DAOException {
        String deliveryPrice = "150";
        long userId = 1;
        userService.payForDeliveryService(deliveryPrice, userId);
        Mockito.verify(userDAO, Mockito.times(1)).payForDeliveryService(deliveryPrice, userId);
    }
}