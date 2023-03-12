package com.varukha.webproject.model.entity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * User entity class. Matches table 'user' in database.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class User {

    private long userId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private Role role;
    private BigDecimal paymentAccount;


    public enum Role {
        MANAGER, GUEST, USER
    }

    private User() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BigDecimal getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(BigDecimal paymentAccount) {
        this.paymentAccount = paymentAccount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(phone, user.phone) &&
                role == user.role &&
                Objects.equals(paymentAccount, user.paymentAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, surname,
                email, password, phone, role, paymentAccount);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [userId = ");
        builder.append(userId);
        builder.append(", name = ");
        builder.append(name);
        builder.append(", surname = ");
        builder.append(surname);
        builder.append(", email = ");
        builder.append(email);
        builder.append(", phone = ");
        builder.append(phone);
        builder.append(", role = ");
        builder.append(role);
        builder.append("]");
        return builder.toString();
    }


    public static class Builder {
        private final User newUser;

        public Builder() {
            newUser = new User();
        }

        public Builder setUserId(long userId) {
            newUser.userId = userId;
            return this;
        }

        public Builder setUserPassword(String userPassword) {
            newUser.password = userPassword;
            return this;
        }

        public Builder setName(String name) {
            newUser.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            newUser.surname = surname;
            return this;
        }

        public Builder setEmail(String email) {
            newUser.email = email;
            return this;
        }

        public Builder setPhone(String phone) {
            newUser.phone = phone;
            return this;
        }

        public Builder setRole(Role role) {
            newUser.role = role;
            return this;
        }

        public Builder setPaymentAccount(BigDecimal money) {
            newUser.paymentAccount = money;
            return this;
        }

        public User build() {
            return newUser;
        }
    }

}
