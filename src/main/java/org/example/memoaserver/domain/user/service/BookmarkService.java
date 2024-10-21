package org.example.memoaserver.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.post.service.PostService;
import org.example.memoaserver.domain.user.dto.req.BookmarkRequest;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.BookmarkRepository;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final PostService postService;
    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final UserAuthHolder userAuthHolder;

    @Transactional
    public void insert(BookmarkRequest bookmarkRequest) throws Exception {

//        UserEntity user = userRepository.findByEmail(userAuthHolder.current().getEmail())
    }
}
