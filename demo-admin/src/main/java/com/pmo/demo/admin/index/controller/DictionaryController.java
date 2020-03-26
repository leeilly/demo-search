package com.pmo.demo.admin.index.controller;

import com.pmo.demo.admin.index.domain.Synonym;
import com.pmo.demo.admin.index.domain.SynonymRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/dic")
public class DictionaryController {

    @Autowired
    SynonymRepository synonymRepository;

    @GetMapping("/synonym")
    public String index(Model model) {

        List<Synonym> synonymList = synonymRepository.findAll();
        model.addAttribute("synonymList", synonymList);

        return "/dictionary/synonym";
    }




}
