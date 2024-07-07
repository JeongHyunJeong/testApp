package org.example.service.posts;

import lombok.RequiredArgsConstructor;
import org.example.domain.posts.Posts;
import org.example.web.dto.PostsListResponseDto;
import org.example.web.dto.PostsResponseDto;
import org.example.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.example.domain.posts.PostsRepository;
import org.example.web.dto.PostsSaveRequestDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long SavePosts(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long Update(Long id, PostsUpdateRequestDto requestDto) {
        Posts post = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        post.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    public PostsResponseDto FindById(Long id) {
        Posts post = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(post);
    }

    @Transactional
    public void DeleteById(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts);
    }

}
