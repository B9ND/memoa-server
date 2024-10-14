package org.example.memoaserver.domain.post.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.post.dto.req.PostReq;
import org.example.memoaserver.domain.post.dto.res.PostRes;
import org.example.memoaserver.domain.post.entity.ImageEntity;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.entity.TagEntity;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.post.repository.TagRepository;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final UserAuthHolder userAuthHolder;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

//    @Transactional
    public void save(PostReq postReq) {
        UserEntity user = userRepository.findByEmail(userAuthHolder.current().getEmail());
        Set<TagEntity> tags = postReq.getTags().stream().map(this::findOrCreateTag).collect(Collectors.toSet());
        PostEntity post = postReq.toPostEntity(user, tags);
        List<ImageEntity> images = postReq.getImages().stream().map(imageUrl -> ImageEntity.builder().url(imageUrl).post(post).build()).collect(Collectors.toList());

        post.setImages(images);

        postRepository.save(post);
    }

    public List<PostRes> getPostsByTitleOrContent(String name) {
        return postRepository.findByTitleContainingOrContentContaining(name, name)
                .stream()
                .map(PostRes::fromPostEntity)
                .toList();
    }

    public PostRes getPostById(Long id) {
        return PostRes.fromPostEntity(postRepository.findById(id)
                .orElseThrow(RuntimeException::new));
    }

    private TagEntity findOrCreateTag(String tagName) {
        return tagRepository.findByTagName(tagName)
                .orElseGet(() -> TagEntity.builder().tagName(tagName).build());
    }

}
