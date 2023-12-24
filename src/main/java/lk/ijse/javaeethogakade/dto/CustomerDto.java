package lk.ijse.javaeethogakade.dto;

import java.io.Serializable;

public class CustomerDto implements Serializable {
        private String id;
        private String name;
        private String address;
        private double salary;

    public CustomerDto() {
    }

    public CustomerDto(String id, String name, String address, double salary) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }
}

