package org.zerock.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Board;
import org.zerock.board.repository.search.SearchBoardRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {
    @Query("SELECT b, w FROM Board b LEFT JOIN b.writer w WHERE b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);
    // WHERE b.bno =:bno -> bno를 param으로 사용
    // 한개의 Object에 배열 값으로 나옴
    // Board를 사용하고 있지만 Member를 함께 조회해야 하는 상황
    // Board 클래스는 Member 클래스와 연관 관계를 맺고 있음 -> b.writer
    // Board 입장에서 보면 writer와 연관관계가 있어서 ON을 생략

    @Query("SELECT b, r FROM Board b LEFT JOIN Reply r ON r.board = b WHERE b.bno= :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);
    // board 입장에서 보면 Reply에 연관관계가 없어서 ON을 붙인다.

    @Query(value = "SELECT b, w, count(r) FROM Board b LEFT JOIN b.writer w LEFT JOIN Reply r ON r.board = b GROUP BY b",
            countQuery = "SELECT count(b) FROM Board b")
    Page<Object[]> getBoarWithReplyCount(Pageable pageable); // 목록 화면에 필요한 데이터 -> 페이징 처리
    // 목록 화면에 필요한 데이터
    // Board : 게시물의 번호(bno), 제목(title), 작성 시간(regdate) / Member : 회원 이름(name), 이메일(email) / Reply : 해당 게시물의 댓글 수
    // 위 세개의 entity중 Board가 가장 많은 데이터를 가져오기 때문에 Board를 중심으로 조인 관계를 작성
    // Member - Board는 writer라는 필드로 연관관계 O / Reply - Board는 연관관계 X(ON 사용)
    // 조인 후 Board를 중심으로 GROUP BY 처리 -> 하나의 게시물 당 하나의 라인이 될 수 있도록 처리
    // getBoarWithReplyCount()은 Pageable을 파라미터로 전달받고 Page<Object[]> 리턴 타입으로 한다.

    @Query("SELECT b, w, count(r) FROM Board b LEFT JOIN b.writer w LEFT OUTER JOIN Reply r ON r.board = b WHERE b.bno= :bno")
    Object getBoardByBno(@Param("bno") Long bno);
}
