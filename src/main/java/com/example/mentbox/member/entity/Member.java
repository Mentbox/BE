package com.example.mentbox.member.entity;

import com.example.mentbox.interest.entity.Interest;
import com.example.mentbox.member.SocialType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    @Column(nullable = false, unique = true)
    private String socialId;

    @Column(length = 15)
    private String name;

    @Column(name = "profile_image_key")
    private String profileImageKey;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interest> interests = new ArrayList<>();

    /** 관심사 전체 교체 */
    public void setInterests(List<Interest> newInterests) {
        this.interests.clear();
        this.interests.addAll(newInterests);
    }

    /** 프로필 수정 (이름 + 이미지 + 관심사) */
    public void updateProfile(String name, String profileImageKey, List<Interest> newInterests) {
        this.name = name;
        this.profileImageKey = profileImageKey;
        setInterests(newInterests);
    }

}
