package com.varukha.webproject.entity;


import java.io.Serializable;
import java.util.Objects;

public class AddressFirst  {

    private long  firstAddressId;
    private String firstCity;
    private String firstStreetName;
    private String firstStreetNumber;
    private String firstHouseNumber;


    private AddressFirst() {

    }



    public long getFirstAddressId() {
        return firstAddressId;
    }

    public void setFirstAddressId(long firstAddressId) {
        this.firstAddressId = firstAddressId;
    }


    public String getFirstCity() {
        return firstCity;
    }

    public void setFirstCity(String firstCity) {
        this.firstCity = firstCity;
    }


    public String getFirstStreetName() {
        return firstStreetName;
    }

    public void setFirstStreetName(String firstStreetName) {
        this.firstStreetName = firstStreetName;
    }


    public String getFirstStreetNumber() {
        return firstStreetNumber;
    }

    public void setFirstStreetNumber(String firstStreetNumber) {
        this.firstStreetNumber = firstStreetNumber;
    }

    public String getFirstHouseNumber() {
        return firstHouseNumber;
    }

    public void setFirstHouseNumber(String firstHouseNumber) {
        this.firstHouseNumber = firstHouseNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressFirst that = (AddressFirst) o;
        return firstAddressId == that.firstAddressId && firstCity == that.firstCity && Objects.equals(firstStreetName, that.firstStreetName) && Objects.equals(firstStreetNumber, that.firstStreetNumber) && Objects.equals(firstHouseNumber, that.firstHouseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstAddressId, firstCity, firstStreetName, firstStreetNumber, firstHouseNumber);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AddressFirst [firstAddressId = ");
        builder.append(firstAddressId);
        builder.append(", firstCity = ");
        builder.append(firstCity);
        builder.append(", firstStreetName = ");
        builder.append(firstStreetName);
        builder.append(", firstStreetNumber = ");
        builder.append(firstStreetNumber);
        builder.append(", firstHouseNumber = ");
        builder.append(firstHouseNumber);
        builder.append("]");
        return builder.toString();
    }



    public static class Builder {

        private AddressFirst newAddressFirst;


        public Builder() {
            newAddressFirst = new AddressFirst();
        }

        public Builder setFirstAddressId(long firstAddressId) {
            newAddressFirst.firstAddressId = firstAddressId;
            return this;
        }

        public Builder setFirstCity(String firstCity) {
            newAddressFirst.firstCity = firstCity;
            return this;
        }

        public Builder setFirstStreetName(String firstStreetName) {
            newAddressFirst.firstStreetName = firstStreetName;
            return this;
        }

        public Builder setFirstStreetNumber(String firstStreetNumber) {
            newAddressFirst.firstStreetNumber = firstStreetNumber;
            return this;
        }

        public Builder setFirstHouseNumber(String firstHouseNumber) {
            newAddressFirst.firstHouseNumber = firstHouseNumber;
            return this;
        }

        public AddressFirst build() {
            return newAddressFirst;
        }
    }


}

