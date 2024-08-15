package org.example.memoaserver.global.controller;

import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.school.dto.SchoolDTO;
import org.example.memoaserver.domain.school.entity.SchoolEntity;
import org.example.memoaserver.domain.school.service.SchoolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;

    @PostMapping("/add-school")
    public void addSchool(@RequestBody SchoolDTO school) {
        schoolService.addSchool(school);
    }

    @GetMapping("/search")
    public List<SchoolEntity> search(
            @RequestParam(name = "search") String schoolName
    ) {
        return schoolService.searchSchool(schoolName);
    }
}
