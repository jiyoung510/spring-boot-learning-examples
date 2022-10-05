package org.zerock.mreview.repotitory;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
        //member테이블을 호출할 때 EAGER로 처리해라
    List<Review> findByMovie(Movie movie); //리뷰를 Movie 객체로 연결
    // Review에서 필요한 데이터를 추출

    @Modifying
    @Query("DELETE FROM Review mr WHERE mr.member =:member ")
    void deleteByMember(Member member); // 쿼리 메서드를 사용하여 삭제 처리
}
