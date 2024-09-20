package org.example.memoaserver.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.post.service.PostService;
import org.example.memoaserver.domain.user.repository.BookmarkRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final PostService postService;
    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
}
