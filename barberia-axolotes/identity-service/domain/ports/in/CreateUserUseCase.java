package com.skimobarber.identity.domain.ports.in;

import com.skimobarber.identity.domain.model.User;

public interface CreateUserUseCase {
    User create(User user);
}