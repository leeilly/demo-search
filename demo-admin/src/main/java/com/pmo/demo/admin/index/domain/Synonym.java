package com.pmo.demo.admin.index.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Synonym {

    @Id
    @GeneratedValue
    private Long synonymSeq;

    private String synonym;

    @Builder
    public Synonym(String synonym){
        this.synonym = synonym;
    }
}
