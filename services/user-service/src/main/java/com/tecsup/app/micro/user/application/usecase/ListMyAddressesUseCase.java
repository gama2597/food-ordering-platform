package com.tecsup.app.micro.user.application.usecase;

import com.tecsup.app.micro.user.domain.model.Address;
import java.util.List;

public interface ListMyAddressesUseCase {
    List<Address> listMyAddresses(String subject);
}