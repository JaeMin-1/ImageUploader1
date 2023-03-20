package com.example.demo.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepository {
    private final Map<Long, Item> store = new HashMap<>();  //DB와 같이 데이터를 보관하는 공간 역할인 map
    //ID 만들기
    private long sequence = 0L;     //외부 데이터베이스라면 그 데베 시퀀스나 값을 올려주는 autoincrement 사용

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }
    public Item findById(Long id) {
        return store.get(id);
    }
}

