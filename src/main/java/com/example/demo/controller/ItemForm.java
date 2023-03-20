package com.example.demo.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ItemForm {
    private Long itemId;                       //업로드되는 이미지의 고유 식별자를 저장
    private String itemName;                   //업로드되는 이미지의 이름을 저장
    private List<MultipartFile> imageFiles;    //
}
