package org.example.memoaserver.domain.report.service;

import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.exception.PostNotFoundException;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.report.dto.request.ReportRequest;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.global.security.jwt.support.UserAuthHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final PostRepository postRepository;
    private final UserAuthHolder userAuthHolder;

    @Transactional
    public void reportPost(ReportRequest reportRequest) {
        UserEntity user = userAuthHolder.current();
        PostEntity post = postRepository.findById(reportRequest.getPostId()).orElseThrow(PostNotFoundException::new);


    }
}
