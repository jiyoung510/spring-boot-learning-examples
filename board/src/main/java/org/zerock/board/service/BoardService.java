package org.zerock.board.service;

import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

public interface BoardService {
    Long register(BoardDTO dto);                                              // 등록 처리

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO); // 목록 처리

    BoardDTO get(Long bno);                                                   // 게시물 조회 처리

    void removeWithReplies(Long bno);                                         // 삭제 기능

    void modify(BoardDTO boardDTO);                                           // 수정 기능
    default Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        // Board에 있는 데이터를 기준으로 Member에 있는 이메일을 가져올 수 있도록 builder() 사용

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)                 // 11행에서 작성한 Builder()의 데이터를 사용하여 writer(작성자)를 알아낸다.
                .build();
        return board;
    }

    default BoardDTO entityToDto(Board board, Member member, Long replyCount) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())          // Long으로 나오므로 int로 처리하도록
                .build();

        return boardDTO;
    }
}

