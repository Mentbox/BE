package com.example.mentbox.file.repository;

import com.example.mentbox.file.entity.File;
import com.example.mentbox.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByMemberOrderByTargetDateAsc(Member member);

    List<File> findByMember(Member member);
}
