package com.tecsup.app.micro.user.application.usecase;

import com.tecsup.app.micro.user.domain.model.UserProfile;
import java.util.List;

public interface GetMyProfileUseCase {
    UserProfileResult getMyProfile(MyPrincipal principal);

    record MyPrincipal(String subject, String username, String email, String fullName, List<String> roles) {
    }

    record UserProfileResult(UserProfile profile, List<String> roles) {
    }
}