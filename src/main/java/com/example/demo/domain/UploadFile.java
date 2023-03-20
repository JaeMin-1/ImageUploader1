package com.example.demo.domain;

import lombok.Data;

@Data
public class UploadFile {
    private String storeFileName;  //새롭게 저장되는 파일명

    public UploadFile(String storeFileName) {
        this.storeFileName = storeFileName;
    }
}
