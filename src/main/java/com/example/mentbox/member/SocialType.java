package com.example.mentbox.member;

import com.example.mentbox.common.exception.ErrorCode;
import com.example.mentbox.common.exception.NotSupportingSocialTypeException;

public enum SocialType {
    KAKAO,
    GOOGLE;

    public static SocialType from(String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "kakao" -> KAKAO;
            case "google" -> GOOGLE;
            default -> throw new NotSupportingSocialTypeException(ErrorCode.NotSupportingSocialType);
        };
        }
    }
