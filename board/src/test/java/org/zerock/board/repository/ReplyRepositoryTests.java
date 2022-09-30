package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            long bno = (long)(Math.random() * 100) + 1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("reply....." + i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });
    }

    @Test
    public void readReply1() {
        Optional<Reply> result = replyRepository.findById(1L);

        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBoard());
    }

    @Test
    public void testListBoard() {
        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(97L).build());

        replyList.forEach(reply -> System.out.println(reply));
    }
//    Hibernate:
//    select
//    reply0_.rno as rno1_2_,
//    reply0_.moddate as moddate2_2_,
//    reply0_.regdate as regdate3_2_,
//    reply0_.board_bno as board_bn6_2_,
//    reply0_.replyer as replyer4_2_,
//    reply0_.text as text5_2_
//            from
//    reply reply0_
//    where
//    reply0_.board_bno=?
//    order by
//    reply0_.rno asc
//    Reply(rno=200, text=reply.....200, replyer=guest)
}
