package org.zerock.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)  // 명시적으로 Lazy 로딩 지정
    // Eager 로딩 : 즉시 로딩 - 기본값 /  Lazy 로딩 : 지연 로딩
    private Member writer;

    public void changeTitle(String title) {
        this.title = title;
    }   // title 필드 수정에 사용되는 메서드

    public void changeContent(String content) {
        this.content = content;
    }   // content 필드 수정에 사용되는 메서드
}
