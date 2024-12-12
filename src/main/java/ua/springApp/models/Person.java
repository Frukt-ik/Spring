package ua.springApp.models;

import jakarta.validation.constraints.*;

public class Person {

    private int id;

    @NotEmpty(message = "Name is required!")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters!")
    private String name;


    @NotEmpty(message = "Email is required!")
    @Email(message = "Email should be valid!")
    private String email;

//    @Pattern(regexp = "\\d{1,3}", message = "should be number")
    @Min(value = 0, message = "Age should be bigger then 0")
    private Integer age;

    public Person() {}

    public Person(int id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
