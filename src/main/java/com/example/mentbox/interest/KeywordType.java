package com.example.mentbox.interest;

import com.example.mentbox.common.exception.ErrorCode;
import com.example.mentbox.common.exception.ThereIsNotThatKeywordException;
import lombok.Getter;

@Getter
public enum KeywordType {
    JOB_HUNTING("취준"),
    TEAM_PROJECT("팀플"),
    PERSONAL_PROJECT("프로젝트"),
    EMPLOYEE("직장인"),
    UNIVERSITY_STUDENT("대학생"),
    CLUB("동아리"),
    INTERVIEW("면접"),
    COMPETITION("공모전"),
    CAREER_CHANGE("이직러");

    private final String displayName;

    KeywordType(String displayName) {
        this.displayName = displayName;
    }

    public static KeywordType stringToEnum(String string) {
        for (KeywordType value : KeywordType.values()) {
            if (value.displayName.equals(string)) {
                return value;
            }
        }
        throw new ThereIsNotThatKeywordException(ErrorCode.ThereIsNotThatKeywordException);
    }
}
