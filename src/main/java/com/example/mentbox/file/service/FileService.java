package com.example.mentbox.file.service;

import com.example.mentbox.file.dto.FileRequest;
import com.example.mentbox.file.dto.FileResponse;
import com.example.mentbox.file.service.crud.FileCreate;
import com.example.mentbox.file.service.crud.FileDelete;
import com.example.mentbox.file.service.crud.FileRead;
import com.example.mentbox.file.service.crud.FileUpdate;
import com.example.mentbox.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileCreate fileCreate;
    private final FileRead fileRead;
    private final FileUpdate fileUpdate;
    private final FileDelete fileDelete;


    public FileResponse create(FileRequest request, Member member) {
        return fileCreate.createFile(request, member);


    }

    public List<FileResponse> readMemberFiles(Member member) {
        return fileRead.readAllFiles(member);


    }

    public ResponseEntity<FileResponse> readOne(Long fileId) {
        FileResponse fileResponse = fileRead.readFile(fileId);

        return ResponseEntity.status(HttpStatus.OK).body(fileResponse);

    }

    public FileResponse update(Long fileId, FileRequest request) {
        return fileUpdate.updateFile(fileId, request);
    }

    public ResponseEntity<Void> delete(Long fileId) {
        fileDelete.deleteFile(fileId);
        return ResponseEntity.status(HttpStatus.OK).build();

    }



}
