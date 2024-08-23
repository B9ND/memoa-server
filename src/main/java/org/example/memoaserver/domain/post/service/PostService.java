package org.example.memoaserver.domain.post.service;

import org.example.memoaserver.domain.post.dto.PostDTO;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    public List<PostDTO> writePost(PostDTO postDTO) {
        PostEntity postEntity = new PostEntity();

        postEntity.setTitle(postDTO.getTitle());
        postEntity.setContent(postDTO.getContent());
        postEntity.setIsReleased(postDTO.getIsReleased());
//        postEntity.setUser();
        return null;
    }
}
