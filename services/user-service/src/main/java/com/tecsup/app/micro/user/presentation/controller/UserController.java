package com.tecsup.app.micro.user.presentation.controller;

import com.tecsup.app.micro.user.application.service.UserApplicationService;
import com.tecsup.app.micro.user.application.usecase.GetMyProfileUseCase.MyPrincipal;
import com.tecsup.app.micro.user.presentation.dto.UpdateUserProfileRequest;
import com.tecsup.app.micro.user.presentation.mapper.UserDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

        private final UserApplicationService service;
        private final UserDtoMapper mapper;

        @GetMapping("/me")
        public Object me(@AuthenticationPrincipal Jwt jwt, Authentication auth) {
                List<String> roles = auth.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .filter(r -> r.startsWith("ROLE_"))
                                .sorted()
                                .toList();

                var principal = new MyPrincipal(
                                jwt.getSubject(),
                                jwt.getClaimAsString("preferred_username"),
                                jwt.getClaimAsString("email"),
                                jwt.getClaimAsString("name"),
                                roles);

                var result = service.getMyProfile(principal);
                return mapper.toUserProfileResponse(result.profile(), result.roles());
        }

        @PutMapping("/me")
        public Object updateMe(@AuthenticationPrincipal Jwt jwt, Authentication auth,
                        @Valid @RequestBody UpdateUserProfileRequest req) {
                var updated = service.updateMyProfile(jwt.getSubject(), req.fullName(), req.phone());

                List<String> roles = auth.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .filter(r -> r.startsWith("ROLE_"))
                                .sorted()
                                .toList();

                return mapper.toUserProfileResponse(updated, roles);
        }
}