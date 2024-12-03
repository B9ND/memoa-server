package org.example.memoaserver.domain.report.service;

import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.exception.PostNotFoundException;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.report.dto.request.ReportRequest;
import org.example.memoaserver.domain.report.entity.ReportEntity;
import org.example.memoaserver.domain.report.exception.ReportNotFoundException;
import org.example.memoaserver.domain.report.repository.ReportRepository;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.example.memoaserver.global.security.jwt.support.UserAuthHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final PostRepository postRepository;
    private final ReportRepository reportRepository;
    private final UserAuthHolder userAuthHolder;

    @Transactional
    public void save(Long reportRequest) {
        UserEntity user = userAuthHolder.current();
        PostEntity post = postRepository.findById(reportRequest).orElseThrow(PostNotFoundException::new);

        reportRepository.save(ReportEntity.builder()
                .post(post)
                .user(user)
                .build());
    }

    @Transactional(readOnly = true)
    public List<ReportEntity> getReportsByPostId(ReportRequest reportRequest) {
        PostEntity post = postRepository.findById(reportRequest.getPostId())
            .orElseThrow(PostNotFoundException::new);

        return reportRepository.findByPost(post)
            .orElseThrow(ReportNotFoundException::new);
    }

    
}