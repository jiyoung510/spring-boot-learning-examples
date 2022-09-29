package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder().email("user" + i + "@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

    @Transactional
    @Test
    public void testRead1() {
        Optional<Board> result = boardRepository.findById(100L);
        // board 테이블에 100번 값을 갖는 자료를 찾는다.
        Board board = result.get();
        // Board 객체에 있는 모든 자료를 찾는다.

        System.out.println(board);
        System.out.println(board.getWriter()); // 작성자를 찾는다.
    }

    @Test
    public void testReadWithWriter() {
        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[])result;

        System.out.println("------------------------------------------");
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testGetBoardWithReply() {
        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for(Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoarWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = (Object[])row;

            System.out.println(Arrays.toString(arr));
        });
        // 리스트 화면에 처리 결과 확인
        //[Board(bno=100, title=Title...100, content=Content...100), Member(email=user100@aaa.com, password=1111, name=USER100), 1]
        //[Board(bno=99, title=Title...99, content=Content...99), Member(email=user99@aaa.com, password=1111, name=USER99), 2]
        //[Board(bno=98, title=Title...98, content=Content...98), Member(email=user98@aaa.com, password=1111, name=USER98), 1]
        //[Board(bno=97, title=Title...97, content=Content...97), Member(email=user97@aaa.com, password=1111, name=USER97), 1]
        //[Board(bno=96, title=Title...96, content=Content...96), Member(email=user96@aaa.com, password=1111, name=USER96), 3]
        //[Board(bno=95, title=Title...95, content=Content...95), Member(email=user95@aaa.com, password=1111, name=USER95), 3]
        //[Board(bno=94, title=Title...94, content=Content...94), Member(email=user94@aaa.com, password=1111, name=USER94), 4]
        //[Board(bno=93, title=Title...93, content=Content...93), Member(email=user93@aaa.com, password=1111, name=USER93), 6]
        //[Board(bno=92, title=Title...92, content=Content...92), Member(email=user92@aaa.com, password=1111, name=USER92), 5]
        //[Board(bno=91, title=Title...91, content=Content...91), Member(email=user91@aaa.com, password=1111, name=USER91), 6]
    }

    @Test
    public void testRead3() {
        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }
    // [Board(bno=100, title=Title...100, content=Content...100),           - 보드테이블 정보
    // Member(email=user100@aaa.com, password=1111, name=USER100),          - 멤버테이블 정보
    // 1]                                                                   - 댓글 수 카운트 정보

    @Test
    public void testSearch1() {
        boardRepository.search1();
    }
//    2022-09-29 09:49:33.487  INFO 1780 --- [    Test worker] o.z.b.r.s.SearchBoardRepositoryImpl      : search1..............
//            2022-09-29 10:04:45.539  INFO 8856 --- [    Test worker] o.z.b.r.s.SearchBoardRepositoryImpl      : -------------------------------------
//            2022-09-29 10:04:45.544  INFO 8856 --- [    Test worker] o.z.b.r.s.SearchBoardRepositoryImpl      : select board
//    from Board board
//    where board.bno = ?1
//            2022-09-29 10:04:45.544  INFO 8856 --- [    Test worker] o.z.b.r.s.SearchBoardRepositoryImpl      : -------------------------------------
//    Tuple 처리(이하)
//    from Board board
//    left join Member member1 with board.writer = member1
//    left join Reply reply with reply.board = board
//    group by board
//2022-09-29 10:17:21.695  INFO 5656 --- [    Test worker] o.z.b.r.s.SearchBoardRepositoryImpl      : -------------------------------------
//    Hibernate:
//    select
//    board0_.bno as col_0_0_,
//    member1_.email as col_1_0_,
//    count(reply2_.rno) as col_2_0_,
//    board0_.bno as bno1_0_,
//    board0_.moddate as moddate2_0_,
//    board0_.regdate as regdate3_0_,
//    board0_.content as content4_0_,
//    board0_.title as title5_0_,
//    board0_.writer_email as writer_e6_0_
//            from
//    board board0_
//    left outer join
//    member member1_
//    on (
//            board0_.writer_email=member1_.email
//    )
//    left outer join
//    reply reply2_
//    on (
//            reply2_.board_bno=board0_.bno
//    )
//    group by
//    board0_.bno

    @Test
    public void testSearchPage() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending().and(Sort.by("title").ascending()));

        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);
    }
//    Hibernate:
//    select
//    board0_.bno as col_0_0_,
//    member1_.email as col_1_0_,
//    count(reply2_.rno) as col_2_0_,
//    board0_.bno as bno1_0_0_,
//    member1_.email as email1_1_1_,
//    board0_.moddate as moddate2_0_0_,
//    board0_.regdate as regdate3_0_0_,
//    board0_.content as content4_0_0_,
//    board0_.title as title5_0_0_,
//    board0_.writer_email as writer_e6_0_0_,
//    member1_.moddate as moddate2_1_1_,
//    member1_.regdate as regdate3_1_1_,
//    member1_.name as name4_1_1_,
//    member1_.password as password5_1_1_
//            from
//    board board0_
//    left outer join
//    member member1_
//    on (
//            board0_.writer_email=member1_.email
//    )
//    left outer join
//    reply reply2_
//    on (
//            reply2_.board_bno=board0_.bno
//    )
//    where
//    board0_.bno>?
//    and (
//            board0_.title like ? escape '!'   // 검색 조건 처리
//    )
//    group by
//    board0_.bno
//    order by
//    board0_.bno desc,                         // order by절 추가된 것을 확인
//    board0_.title asc limit ?
//            2022-09-29 12:33:58.958  INFO 2756 --- [    Test worker] o.z.b.r.s.SearchBoardRepositoryImpl      : [[Board(bno=100, title=Title...100, content=Content...100), Member(email=user100@aaa.com, password=1111, name=USER100), 1], [Board(bno=91, title=Title...91, content=Content...91), Member(email=user91@aaa.com, password=1111, name=USER91), 6], [Board(bno=81, title=Title...81, content=Content...81), Member(email=user81@aaa.com, password=1111, name=USER81), 3], [Board(bno=71, title=Title...71, content=Content...71), Member(email=user71@aaa.com, password=1111, name=USER71), 2], [Board(bno=61, title=Title...61, content=Content...61), Member(email=user61@aaa.com, password=1111, name=USER61), 4], [Board(bno=51, title=Title...51, content=Content...51), Member(email=user51@aaa.com, password=1111, name=USER51), 6], [Board(bno=41, title=Title...41, content=Content...41), Member(email=user41@aaa.com, password=1111, name=USER41), 1], [Board(bno=31, title=Title...31, content=Content...31), Member(email=user31@aaa.com, password=1111, name=USER31), 3], [Board(bno=21, title=Title...21, content=Content...21), Member(email=user21@aaa.com, password=1111, name=USER21), 4], [Board(bno=19, title=Title...19, content=Content...19), Member(email=user19@aaa.com, password=1111, name=USER19), 6]]
//    (윗줄) 쿼리 실행 결과로 Board, Member, 댓글 수를 Object[] 리스트로 처리
//    Hibernate:                                // count를 처리하는 쿼리
//    select
//    count(distinct board0_.bno) as col_0_0_
//    from
//    board board0_
//    left outer join
//    member member1_
//    on (
//            board0_.writer_email=member1_.email
//    )
//    left outer join
//    reply reply2_
//    on (
//            reply2_.board_bno=board0_.bno
//    )
//    where
//    board0_.bno>?
//    and (
//            board0_.title like ? escape '!'
//    )
//2022-09-29 12:33:58.986  INFO 2756 --- [    Test worker] o.z.b.r.s.SearchBoardRepositoryImpl      : COUNT : 19
}
