package com.example.backendgram.profile.controller;

import com.example.backendgram.profile.service.ProfileService;
import com.example.backendgram.profile.dto.ProfileRequestDto;
import com.example.backendgram.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileRequestDto> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            Long userId = userDetails.getUser().getId();
            ProfileRequestDto profileRequestDto = profileService.getProfileByUserId(userId);
            return ResponseEntity.ok(profileRequestDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<ProfileRequestDto> updateProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ProfileRequestDto updatedProfile
    ) {
        try {
            Long userId = userDetails.getUser().getId();
            ProfileRequestDto updatedProfileRequestDto = profileService.updateProfile(userId, updatedProfile);
            return ResponseEntity.ok(updatedProfileRequestDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/password")
    public ResponseEntity<String> updatePassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam String newPassword
    ) {
        try {
            Long userId = userDetails.getUser().getId();
            profileService.updatePassword(userId, newPassword);
            return ResponseEntity.ok("Updated password successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/update/introduction")
    public ResponseEntity<String> updateIntroduction(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam String introduction
    ) {
        try {
            Long userId = userDetails.getUser().getId();
            profileService.updateIntroduction(userId, introduction);
            return ResponseEntity.ok("Updated introduction successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/update/profile/image")
    public ResponseEntity<String> updateProfileImage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam MultipartFile profileImage
    ) {
        try {
            Long userId = userDetails.getUser().getId();
            profileService.updateProfileImage(userId, profileImage);
            return ResponseEntity.ok("Updated profile image successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
