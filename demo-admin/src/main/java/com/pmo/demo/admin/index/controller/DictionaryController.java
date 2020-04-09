package com.pmo.demo.admin.index.controller;

import com.pmo.demo.admin.index.domain.Synonym;
import com.pmo.demo.admin.index.domain.SynonymRepository;
import com.pmo.demo.admin.index.dto.SynonymAddRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dic")
public class DictionaryController {

    @Autowired
    SynonymRepository synonymRepository;

    @GetMapping("/synonym")
    public List<Synonym> getSynonymList() {
        return synonymRepository.findAll();
    }

    @PostMapping("/synonym")
    public Synonym add(@RequestBody final SynonymAddRequestDto dto) {
        return synonymRepository.save(dto.toEntity());
    }




}
