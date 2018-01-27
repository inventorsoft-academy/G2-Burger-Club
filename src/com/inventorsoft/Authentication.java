package com.inventorsoft;

import com.inventorsoft.Model.User;

public interface Authentication {

    boolean isLoginSuccessful(User user);

    boolean isRegistrationSuccessful(User user);
}
