package com.cmj.app.domain.board.repository;

import com.cmj.app.domain.board.entity.Board;
import com.cmj.app.domain.channel.entity.Channel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void create() {

        Channel channel = Channel.builder()
                .name("스포츠")
                .description("스포츠 관련 채널")
                .build();

        Board board = Board.builder()
                .name("축구 게시판")
                .description("축구 게시판입니다.")
                .channel(channel)
                .build();

        Board saved = boardRepository.save(board);

        assertNotNull(saved.getId());
    }
}