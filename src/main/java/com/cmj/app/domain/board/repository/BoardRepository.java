package com.cmj.app.domain.board.repository;

import com.cmj.app.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByChannelId(Long channelId);
    Optional<Board> findByName(String name);
}
