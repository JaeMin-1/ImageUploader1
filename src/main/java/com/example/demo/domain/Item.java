package com.example.demo.domain;

import lombok.Data;

import java.util.List;

@Data
public class Item {
    private Long id; //데이터베이스에 저장할때 생기는 값
    private String itemName;
    private List<UploadFile> imageFiles;
}
