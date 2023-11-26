package com.example.backendgram.profile.service;

import com.example.backendgram.profile.dto.ProfileRequestDto;
import com.example.backendgram.profile.entity.Profile;
import com.example.backendgram.profile.repository.ProfileRepository;
import com.example.backendgram.user.entity.User;
import com.example.backendgram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileRequestDto getProfileByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Profile profile = user.getProfile();
        if (profile == null) {
            return new ProfileRequestDto();
        }

        ProfileRequestDto profileRequestDto = new ProfileRequestDto();
        profileRequestDto.setIntroduction(profile.getIntroduction());

        return profileRequestDto;
    }

    public ProfileRequestDto updateProfile(Long userId, ProfileRequestDto updatedProfile) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Profile profile = user.getProfile();
        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
            user.setProfile(profile);
        }

        profile.setIntroduction(updatedProfile.getIntroduction());

        userRepository.save(user);

        return updatedProfile;
    }

    public void updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    public void updateIntroduction(Long userId, String introduction) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Profile profile = user.getProfile();
        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
            user.setProfile(profile);
        }

        profile.setIntroduction(introduction);

        userRepository.save(user);
    }

    public void updateProfileImage(Long userId, MultipartFile profileImage) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Profile profile = user.getProfile();
        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
            user.setProfile(profile);
        }

        String imageName = saveProfileImage(profileImage);
        profile.setProfileImage(imageName);

        userRepository.save(user);
    }

    private String saveProfileImage(MultipartFile profileImage) {
        String imageName = UUID.randomUUID().toString() + "_" + profileImage.getOriginalFilename();
        try {
            Files.copy(profileImage.getInputStream(), Paths.get("your/profile/image/directory", imageName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save profile image", e);
        }
        return imageName;
    }
}