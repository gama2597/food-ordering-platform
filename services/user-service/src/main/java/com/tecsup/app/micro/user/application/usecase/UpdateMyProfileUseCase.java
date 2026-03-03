package com.tecsup.app.micro.user.application.usecase;

import com.tecsup.app.micro.user.domain.model.UserProfile;

public interface UpdateMyProfileUseCase {
    UserProfile updateMyProfile(String subject, String fullName, String phone);
}