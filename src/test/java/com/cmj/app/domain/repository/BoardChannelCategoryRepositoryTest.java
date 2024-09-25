package com.cmj.app.domain.repository;

import com.cmj.app.domain.board.entity.Board;
import com.cmj.app.domain.board.repository.BoardRepository;
import com.cmj.app.domain.category.entity.Category;
import com.cmj.app.domain.category.repository.CategoryRepository;
import com.cmj.app.domain.channel.entity.Channel;
import com.cmj.app.domain.channel.repository.ChannelRepository;
import com.cmj.app.domain.post.entity.Post;
import org.hibernate.proxy.HibernateProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class BoardChannelCategoryRepositoryTest {

    private final BoardRepository boardRepository;
    private final ChannelRepository channelRepository;
    private final CategoryRepository categoryRepository;

    public BoardChannelCategoryRepositoryTest(BoardRepository boardRepository, ChannelRepository channelRepository, CategoryRepository categoryRepository) {
        this.boardRepository = boardRepository;
        this.channelRepository = channelRepository;
        this.categoryRepository = categoryRepository;
    }

    @Test
    public void testFindBoardAndCheckChannelAndCategory() {
        // Board 조회
        Board board = boardRepository.findByName("게시판").orElseThrow();
        assertThat(board.getChannel().getName()).isEqualTo("채널");
        assertThat(board.getChannel().getCategory().getName()).isEqualTo("카테고리");
    }

    @Test
    public void testFindNonExistingBoard() {
        Optional<Board> board = boardRepository.findByName("없는 게시판");
        assertThat(board).isEmpty();
    }

}