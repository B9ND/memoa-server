package org.example.memoaserver.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.report.dto.request.ReportRequest;
import org.example.memoaserver.domain.report.dto.response.ReportResponse;
import org.example.memoaserver.domain.report.entity.ReportEntity;
import org.example.memoaserver.domain.report.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
@Tag(name = "report", description = "게시물 신고 관련 API")
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    @Operation(
            summary = "게시물을 신고합니다.",
            description = "게시물 아이디를 파라미터로 전달합니다."
    )
    public ResponseEntity<List<ReportResponse>> addReport(@RequestParam(name = "post") Long postId) {
        reportService.save(postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(
            summary = "어드민 전용. 신고 목록을 조회합니다.",
            description = "인자는 없습니다."
    )
    public ResponseEntity<List<ReportResponse>> getReport(ReportRequest reportRequest) {
    List<ReportEntity> reports = reportService.getReportsByPostId(reportRequest);
    List<ReportResponse> reportResponses = reports.stream()
            .map(ReportResponse::fromReportEntity)
            .toList();
    return ResponseEntity.ok(reportResponses);

    }

}