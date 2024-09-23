package org.example.memoaserver.global.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.school.dto.SchoolDTO;
import org.example.memoaserver.domain.school.entity.SchoolEntity;
import org.example.memoaserver.domain.school.service.SchoolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "school", description = "학교 정보 확인 API")
@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;

    @Operation(
            summary = "학교를 추가합니다",
            description = "db에 학교를 추가함"
    )
    @PostMapping("/add-school")
    public void addSchool(@RequestBody SchoolDTO school) {

        schoolService.addSchool(school);
    }

    @Operation(
            summary = "학교를 검색합니다",
            description = "연관학교를 모두 보여줍니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "search", value = "검색 이름", required = true, dataType = "string", paramType = "parameter")
    })
    @GetMapping("/search")
    public List<SchoolEntity> search(
            @RequestParam(name = "search") String schoolName
    ) {

        return schoolService.searchSchool(schoolName);
    }
}
