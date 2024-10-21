package org.example.memoaserver.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.school.dto.res.DepartmentResponse;
import org.example.memoaserver.domain.school.entity.SchoolEntity;
import org.example.memoaserver.domain.school.service.SchoolService;
import org.springframework.http.ResponseEntity;
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
    @PostMapping
    public void addSchool(@RequestBody SchoolRequest school) {

        schoolService.addSchool(school);
    }

    @Operation(
            summary = "학교를 검색합니다",
            description = "연관학교를 모두 보여줍니다."
    )
    @GetMapping("/search")
    public List<SchoolEntity> search(
            @RequestParam(name = "search") String schoolName
    ) {

        return schoolService.searchSchool(schoolName);
    }

    @Operation(
            summary = "내 학교를 찾습니다.",
            description = "자신의 헉교를 찾습니다."
    )
    @GetMapping("/me")
    public ResponseEntity<DepartmentResponse> me() {
        return ResponseEntity.ok().body(schoolService.getMySchool());
    }
}
