package com.example.mentbox.recording.repository;

import com.example.mentbox.recording.entity.Recording;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordingRepository extends JpaRepository<Recording, Long> {

}
