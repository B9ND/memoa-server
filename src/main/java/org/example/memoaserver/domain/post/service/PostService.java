package org.example.memoaserver.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.post.dto.PostDTO;
import org.example.memoaserver.domain.post.entity.ImageEntity;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.entity.TagEntity;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private UserAuthHolder userAuthHolder;

    public void save(PostDTO postDTO) {
        Set<TagEntity> tagEntities = postDTO.getTags().stream()
                .map(tagName -> TagEntity.builder().tagName(tagName).build())
                .collect(Collectors.toSet());
        List<ImageEntity> imageEntities = postDTO.getImages().stream()
                .map(imageUrl -> ImageEntity.builder().url(imageUrl).build())
                .collect(Collectors.toList());
        PostEntity postEntity = PostDTO.of(postDTO, userAuthHolder.current(), tagEntities, imageEntities);
    }
}
