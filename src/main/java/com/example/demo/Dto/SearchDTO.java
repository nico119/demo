package com.example.demo.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SearchDTO {
    private String searchType;
    private String keyword;


    public SearchDTO(String searchType,String keyword) {
        this.searchType=searchType;
        this.keyword=keyword;
    }
}

