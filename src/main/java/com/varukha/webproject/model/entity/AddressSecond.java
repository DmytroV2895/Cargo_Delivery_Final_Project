package com.varukha.webproject.model.entity;

import java.util.Objects;


/**
 * AddressSecond entity class. Matches table 'second_address' in database.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class AddressSecond  {

    private long  secondAddressId;
    private String secondCity;
    private String secondStreetName;
    private String secondStreetNumber;
    private String secondHouseNumber;

    private AddressSecond() {
    }

    public long getSecondAddressId() {
        return secondAddressId;
    }

    public void setSecondAddressId(long secondAddressId) {
        this.secondAddressId = secondAddressId;
    }

    public String getSecondCity() {
        return secondCity;
    }

    public void setSecondCity(String secondCity) {
        this.secondCity = secondCity;
    }

    public String getSecondStreetName() {
        return secondStreetName;
    }

    public void setSecondStreetName(String secondStreetName) {
        this.secondStreetName = secondStreetName;
    }

    public String getSecondStreetNumber() {
        return secondStreetNumber;
    }

    public void setSecondStreetNumber(String secondStreetNumber) {
        this.secondStreetNumber = secondStreetNumber;
    }

    public String getSecondHouseNumber() {
        return secondHouseNumber;
    }

    public void setSecondHouseNumber(String secondHouseNumber) {
        this.secondHouseNumber = secondHouseNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressSecond that = (AddressSecond) o;
        return secondAddressId == that.secondAddressId &&
                secondCity.equals(that.secondCity) &&
                secondStreetName.equals(that.secondStreetName) &&
                secondStreetNumber.equals(that.secondStreetNumber) &&
                secondHouseNumber.equals(that.secondHouseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secondAddressId, secondCity, secondStreetName,
                secondStreetNumber, secondHouseNumber);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AddressSecond [secondAddressId = ");
        builder.append(secondAddressId);
        builder.append(", secondCity = ");
        builder.append(secondCity);
        builder.append(", secondStreetName = ");
        builder.append(secondStreetName);
        builder.append(", secondStreetNumber = ");
        builder.append(secondStreetNumber);
        builder.append(", secondHouseNumber = ");
        builder.append(secondHouseNumber);
        builder.append("]");
        return builder.toString();
    }

    public static class Builder {
        private final AddressSecond newSecondAddress;

        public Builder() {
            newSecondAddress = new AddressSecond();
        }

        public Builder setSecondAddressId(long secondAddressId) {
            newSecondAddress.secondAddressId = secondAddressId;
            return this;
        }

        public Builder setSecondCity(String secondCity) {
            newSecondAddress.secondCity = secondCity;
            return this;
        }

        public Builder setSecondStreetName(String secondStreetName) {
            newSecondAddress.secondStreetName = secondStreetName;
            return this;
        }

        public Builder setSecondStreetNumber(String secondStreetNumber) {
            newSecondAddress.secondStreetNumber = secondStreetNumber;
            return this;
        }

        public Builder setSecondHouseNumber(String secondHouseNumber) {
            newSecondAddress.secondHouseNumber = secondHouseNumber;
            return this;
        }

        public AddressSecond build() {
            return newSecondAddress;
        }
    }
}
