package com.example.mini_project_cm.file.service;

import com.example.mini_project_cm.file.exception.InvalidInputException;
import com.example.mini_project_cm.file.vo.FileDownloadVO;
import com.example.mini_project_cm.repository.ArticlePhotoRepository;
import com.example.mini_project_cm.repository.MemberPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileService {
    private final ArticlePhotoRepository apRepo;
    private final MemberPhotoRepository mpRepo;

    @Value("/cm_files/article") String article_photo_path;
    @Value("/cm_files/member") String member_photo_path;

    public FileDownloadVO downloadImageFile(String type, String url) {
        Path folderLocation = null;

        if (type.equals("article")) {
            folderLocation = Paths.get(article_photo_path);
        }
        else if(type.equals("member")) {
            folderLocation = Paths.get(member_photo_path);
        }
        else {
            throw  new InvalidInputException("유효하지 않은 경로입니다");
        }

        String filename = "";
        if(type.equals("article")) {
            if(apRepo.findByApFileUrl(url)==null) {
                throw new InvalidInputException("파일이 존재하지 않습니다");
            }
            else {
                filename = apRepo.findByApFileUrl(url).getApFileName();
            }
        }
        else if(type.equals("member")) {
            if(mpRepo.findByMpFileUrl(url)==null) {
                throw new InvalidInputException("파일이 존재하지 않습니다");
            }
            else {
                filename = mpRepo.findByMpFileUrl(url).getMpFileName();
            }
        }
            return new FileDownloadVO(folderLocation, filename);
    }
}
