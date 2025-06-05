package com.example.mentbox.interest.entity;

import com.example.mentbox.common.entity.BaseTimeEntity;
import com.example.mentbox.interest.KeywordType;
import com.example.mentbox.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor
@Where(clause = "deleted = false")
public class Interest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private KeywordType keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static Interest of(KeywordType keyword, Member member) {
        Interest interest = new Interest();
        interest.keyword = keyword;
        interest.member = member;
        return interest;
    }
}
