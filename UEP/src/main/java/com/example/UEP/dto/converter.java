package com.example.UEP.dto;

import com.example.UEP.model.User;

public class converter {
    public static User convertToUser(UserProfileDTO dto) {
        User user = new User();
//        user.setId(dto.id() != null ? Long.parseLong(dto.id()) : null);
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPicture(dto.picture());
        user.setCollegeName(dto.collegeName());
        user.setExperience(dto.experience());
        return user;
    }

    public static UserProfileDTO convertToDTO(User user) {
        return new UserProfileDTO(
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                user.getPicture(),
                user.getCollegeName(),
                user.getExperience()
        );
    }
}
