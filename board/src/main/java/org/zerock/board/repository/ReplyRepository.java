package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // JpaRepository<엔티티 객체, 엔티티 객체의 pk 타입>

    //Board 삭제시에 댓글들 삭제
    @Modifying
    @Query("DELETE FROM Reply r WHERE r.board.bno =:bno")
    void deleteByBno(Long bno); // 게시글 삭제시 댓글도 삭제되도록 진행

    // 게시물로 댓글 목록 가져오기
    List<Reply> getRepliesByBoardOrderByRno(Board board);
    // 게시물로 댓글 목록 가져오기(쿼리 메서드)
}
