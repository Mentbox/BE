package com.example.mentbox.file.service;

import com.example.mentbox.file.dto.FileRequest;
import com.example.mentbox.file.dto.FileResponse;
import com.example.mentbox.file.service.crud.FileCreate;
import com.example.mentbox.file.service.crud.FileDelete;
import com.example.mentbox.file.service.crud.FileRead;
import com.example.mentbox.file.service.crud.FileUpdate;
import com.example.mentbox.member.entity.Member;
import lombok.RequiredArgsConstructor;
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

    public FileResponse readMemberFile(Long fileId) {
        return fileRead.readFile(fileId);
    }

    public FileResponse update(Long fileId, FileRequest request) {
        return fileUpdate.updateFile(fileId, request);
    }

    public void delete(Long fileId) {
        fileDelete.deleteFile(fileId);
    }



}
