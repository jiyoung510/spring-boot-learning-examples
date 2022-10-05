package org.zerock.mreview.repotitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    //@Query("SELECT m, avg(coalesce(r.grade,0)), count(distinct r) " +
    //        "FROM Movie m LEFT OUTER JOIN Review r ON r.movie = m GROUP BY m")
    @Query("SELECT m, mi, avg(coalesce(r.grade,0)), count(distinct r) " +
            "FROM Movie m " +
            "LEFT OUTER JOIN MovieImage mi ON mi.movie = m " + //영화이미지에 대한 조건 추가
            "LEFT OUTER JOIN Review r ON r.movie = m GROUP BY m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("SELECT m, mi, avg(coalesce(r.grade,0)), count(r) " +
            "FROM Movie m " +
            "LEFT OUTER JOIN MovieImage mi on mi.movie = m " +
            "LEFT OUTER JOIN Review r ON r.movie = m " +
            "WHERE m.mno =:mno GROUP BY mi")
    List<Object[]> getMovieWithAll(Long mno); //특정 영화를 클릭 시 처리 (영화보기)
}
