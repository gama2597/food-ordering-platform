package com.tecsup.app.micro.user.application.service;

import com.tecsup.app.micro.user.application.usecase.*;

public interface UserApplicationService extends
        GetMyProfileUseCase,
        UpdateMyProfileUseCase,
        ListMyAddressesUseCase,
        CreateMyAddressUseCase,
        UpdateMyAddressUseCase,
        DeleteMyAddressUseCase {
}