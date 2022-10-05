package org.zerock.mreview.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.repotitory.MemberRepository;
import org.zerock.mreview.repotitory.MovieRepository;
import org.zerock.mreview.repotitory.ReviewRepository;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMembers() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("r" + i + "@zerock.org")
                    .pw("1111")
                    .nickname("reviewer" + i).build();
            memberRepository.save(member);
        });
    }

    @Test
    @Transactional
    @Commit
    public void testDeleteMember(){
        Long mid = 8L ;

        Member member = Member.builder().mid(mid).build();

        reviewRepository.deleteByMember(member); //리뷰를 삭제한다.
        memberRepository.deleteById(mid); // 맴버를 삭제한다.
    }

}