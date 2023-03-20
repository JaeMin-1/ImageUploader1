package com.example.demo.controller;

import com.example.demo.domain.Item;
import com.example.demo.domain.ItemRepository;
import com.example.demo.domain.UploadFile;
import com.example.demo.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Controller                        //웹요청을 처리하는 컨트롤러라는 뜻
@RequiredArgsConstructor           //해당 클래스의 생성자를 자동으로 생성해주는 롬복 어노테이션
public class ItemController {
    private final ItemRepository itemRepository;  //데이터베이스와 연동하기 위해 ItemRepositoty 클래스 사용
    private final FileStore fileStore;  //업로드한 이미지 파일을 저장하기 위해 FileStore 클래스 사용
    //HTTP GET 요청을 처리하는 메소드로, item-from 폼을 연결하여 보여준다
    @GetMapping("/")
    public String newItem(@ModelAttribute ItemForm form) {
        return "item-form";
    }
    //HTTP POST 요청을 처리하는 메소드로, 업로드한 이미지 파일과 정보를 데이터베이스에 저장한다
    @PostMapping("/")
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes
            redirectAttributes) throws IOException {
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());
        Item item = new Item(); //데이터베이스에 저장될 정보를 Item 클래스에 저장한다
        item.setItemName(form.getItemName());
        item.setImageFiles(storeImageFiles);
        itemRepository.save(item);
        redirectAttributes.addAttribute("Id", item.getId());
        return "redirect:/items/{Id}";
    }
    //HTTP GET 요청을 처리하는 메소드, 업로드한 이미지 파일을 item-view 폼을 통해 보여준다
    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model) {
        Item item = itemRepository.findById(id);
        model.addAttribute("item", item);
        return "item-view";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws
            MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

}
