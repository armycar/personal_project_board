package com.example.mini_project_cm.file.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.file.Path;

@Data
@AllArgsConstructor
public class FileDownloadVO {
    Path folderLocation;
    String filename;
}
