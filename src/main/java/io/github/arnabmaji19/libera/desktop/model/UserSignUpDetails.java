package io.github.arnabmaji19.libera.desktop.model;

import com.google.gson.annotations.SerializedName;

public class UserSignUpDetails {

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;

    public UserSignUpDetails() {
    }

    public UserSignUpDetails(String firstName, String lastName, String email, String password, String phone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }


}
