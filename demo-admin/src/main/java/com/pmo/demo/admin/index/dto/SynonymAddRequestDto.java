package com.pmo.demo.admin.index.dto;

import com.pmo.demo.admin.index.domain.Synonym;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SynonymAddRequestDto {

    private String synonym;

    public Synonym toEntity(){
        return Synonym.builder()
                .synonym(synonym)
                .build();
    }


}
