package org.example.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void writePosts(){
        postsRepository.save(Posts.builder()
            .title("테스트 게시글")
            .content("테스트 본문")
            .author("testUser@gmail.com").build());

        var postsList = postsRepository.findAll();

        var posts = postsList.get(0);
        assertEquals(posts.getTitle(), "테스트 게시글");
        assertEquals(posts.getContent(), "테스트 본문");
    }

    @Test
    public void writeBaseTimeEntity() {
        var now = LocalDateTime.of(2024, 6, 28, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("테스트 게시글")
                .content("테스트 본문")
                .author("author").build());

        var postsList = postsRepository.findAll();

        var posts = postsList.get(0);

        System.out.println(">>>>>>>>> createDate=" + posts.getCreatedDate()
                + ", modifiedDate=" + posts.getModifiedDate());
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}