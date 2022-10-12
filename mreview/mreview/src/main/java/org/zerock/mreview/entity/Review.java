package org.zerock.mreview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"movie", "member"}) // 2개의 fk를 제외한다. (배열 선언)
public class Review extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    
    private int grade ; //리뷰 점수
    
    private String text ; //댓글

    public void changeGrade(int grade){
        this.grade = grade;
    }

    public void changeText(String text){
        this.text = text;
    }

    //Hibernate:
    //
    //    create table m_member (
    //       mid bigint not null auto_increment,
    //        moddate datetime(6),
    //        regdate datetime(6),
    //        email varchar(255),
    //        nickname varchar(255),
    //        pw varchar(255),
    //        primary key (mid)
    //    ) engine=InnoDB
    //Hibernate:
    //
    //    create table review (
    //       reviewnum bigint not null auto_increment,
    //        moddate datetime(6),
    //        regdate datetime(6),
    //        grade integer not null,
    //        text varchar(255),
    //        member_mid bigint,
    //        movie_mno bigint,
    //        primary key (reviewnum)
    //    ) engine=InnoDB
    //Hibernate:
    //
    //    alter table review
    //       add constraint FK9gnx8llky658xey9qg00djyg5
    //       foreign key (member_mid)
    //       references m_member (mid)
    //Hibernate:
    //
    //    alter table review
    //       add constraint FKdg4bkv5wfpxx015elj4h915gw
    //       foreign key (movie_mno)
    //       references movie (mno)
}
