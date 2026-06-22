package org.yearup.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.yearup.dtos.UpdateProfileRequest;
import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@CrossOrigin
public class ProfileController {

    ProfileService profileService;
    UserService userService;
    public ProfileController(ProfileService profileService, UserService userService){
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<Profile> getUserInfo(Principal principal){
        String username = principal.getName();
        User user = userService.getByUserName(username);
        int userId = user.getId();
        Profile profile = this.profileService.getProfile(userId);

        return ResponseEntity.ok(profile);

    }

    @PutMapping("")
    public ResponseEntity<Void> updateUserInfo(Principal principal, @RequestBody UpdateProfileRequest updateProfileRequest){
        String username = principal.getName();
        User user = userService.getByUserName(username);
        int userId = user.getId();

         this.profileService.updateProfile(userId, updateProfileRequest);

        return ResponseEntity.ok().build();
    }



}
