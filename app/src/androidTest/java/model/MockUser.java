package model;

import com.example.boket.model.user.User;

public class MockUser implements User {
    @Override
    public String getUid() {
        return "DNdBWJjEjFa1yMaYncB6xsKptAl2";
    }

    @Override
    public String getEmail() {
        return "test@test.com";
    }

    @Override
    public String getName() {
        return "TEST";
    }

    @Override
    public String getLocation() {
        return "Göteborg";
    }
}
