package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;
import org.zerock.mreview.repotitory.MovieImageRepository;
import org.zerock.mreview.repotitory.MovieRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository imageRepository;

    @Commit
    @Transactional
    @Test
    public void insertMovies() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            Movie movie = Movie.builder().title("Movie...." + i).build();

            System.out.println("------------------------------------------");

            movieRepository.save(movie);  // 100개의 Movie 게시물 등록

            int count = (int) (Math.random() * 5) + 1; //게시물에 대한 이미지 개수 랜덤(0,1,2,3,4)


            for (int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("test" + j + ".jpg").build();  // 파일명을 UUID를 사용하여 생성

                imageRepository.save(movieImage);  // 영화이미지를 0~4개까지 램덤 생성
            }


            System.out.println("===========================================");

        });
    }

    @Test
    public void testListPage(){

        PageRequest pageRequest = PageRequest.of(4,10, Sort.by(Sort.Direction.DESC, "mno"));

        Page<Object[]> result = movieRepository.getListPage(pageRequest);

        for (Object[] objects : result.getContent()) {
            System.out.println(Arrays.toString(objects));
        }
    }
        //Hibernate:
    //    select
    //        movie0_.mno as col_0_0_,
    //        movieimage1_.inum as col_1_0_,
    //        avg(coalesce(review2_.grade,
    //        0)) as col_2_0_,
    //        count(distinct review2_.reviewnum) as col_3_0_,
    //        movie0_.mno as mno1_1_0_,
    //        movieimage1_.inum as inum1_2_1_,
    //        movie0_.moddate as moddate2_1_0_,
    //        movie0_.regdate as regdate3_1_0_,
    //        movie0_.title as title4_1_0_,
    //        movieimage1_.img_name as img_name2_2_1_,
    //        movieimage1_.movie_mno as movie_mn5_2_1_,
    //        movieimage1_.path as path3_2_1_,
    //        movieimage1_.uuid as uuid4_2_1_
    //    from
    //        movie movie0_
    //    left outer join
    //        movie_image movieimage1_
    //            on (
    //                movieimage1_.movie_mno=movie0_.mno
    //            )
    //    left outer join
    //        review review2_
    //            on (
    //                review2_.movie_mno=movie0_.mno
    //            )
    //    group by
    //        movie0_.mno
    //    order by
    //        movie0_.mno desc limit ?,
    //        ?
    //Hibernate:
    //    select
    //        count(movie0_.mno) as col_0_0_
    //    from
    //        movie movie0_
    //    left outer join
    //        movie_image movieimage1_
    //            on (
    //                movieimage1_.movie_mno=movie0_.mno
    //            )
    //    left outer join
    //        review review2_
    //            on (
    //                review2_.movie_mno=movie0_.mno
    //            )
    //    group by
    //        movie0_.mno
    //[Movie(mno=60, title=Movie....60), MovieImage(inum=181, uuid=bca620bc-9a31-4bf3-b483-fe5b4145c4e6, imgName=test0.jpg, path=null), 3.3333, 3]
    //[Movie(mno=59, title=Movie....59), MovieImage(inum=176, uuid=0635851b-15e8-4b3b-9804-18c7a7b4be19, imgName=test0.jpg, path=null), 5.0, 1]
    //[Movie(mno=58, title=Movie....58), MovieImage(inum=172, uuid=45778dec-0529-4af9-aaa3-5e8ccca0674e, imgName=test0.jpg, path=null), 0.0, 0]
    //[Movie(mno=57, title=Movie....57), MovieImage(inum=171, uuid=1c91fee8-dd9c-4278-a015-64840417de61, imgName=test0.jpg, path=null), 3.25, 4]
    //[Movie(mno=56, title=Movie....56), MovieImage(inum=169, uuid=b2adc438-b831-4115-ba07-e868ffa923fa, imgName=test0.jpg, path=null), 2.3333, 6]
    //[Movie(mno=55, title=Movie....55), MovieImage(inum=164, uuid=0d493daa-ab73-4fbc-b09c-91c68e791646, imgName=test0.jpg, path=null), 3.0, 3]
    //[Movie(mno=54, title=Movie....54), MovieImage(inum=162, uuid=218df259-a4d8-4780-93a4-30ea44403890, imgName=test0.jpg, path=null), 2.0, 2]
    //[Movie(mno=53, title=Movie....53), MovieImage(inum=160, uuid=15bcd91e-96dd-4a41-807c-7a51e3e353d7, imgName=test0.jpg, path=null), 3.0, 1]
    //[Movie(mno=52, title=Movie....52), MovieImage(inum=159, uuid=d159eeda-0575-423c-88ce-d6050efef530, imgName=test0.jpg, path=null), 0.0, 0]
    //[Movie(mno=51, title=Movie....51), MovieImage(inum=156, uuid=ee85ce9d-dee1-49d4-bd06-1332ca1875aa, imgName=test0.jpg, path=null), 3.0, 3]

    @Test
    public void testGetMovieWithAll() {

        List<Object[]> result = movieRepository.getMovieWithAll(8L);

        System.out.println(result);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    //Hibernate:
        //    select
        //        movie0_.mno as col_0_0_,
        //        movieimage1_.inum as col_1_0_,
        //        avg(coalesce(review2_.grade,
        //        0)) as col_2_0_,
        //        count(review2_.reviewnum) as col_3_0_,
        //        movie0_.mno as mno1_1_0_,
        //        movieimage1_.inum as inum1_2_1_,
        //        movie0_.moddate as moddate2_1_0_,
        //        movie0_.regdate as regdate3_1_0_,
        //        movie0_.title as title4_1_0_,
        //        movieimage1_.img_name as img_name2_2_1_,
        //        movieimage1_.movie_mno as movie_mn5_2_1_,
        //        movieimage1_.path as path3_2_1_,
        //        movieimage1_.uuid as uuid4_2_1_
        //    from
        //        movie movie0_
        //    left outer join
        //        movie_image movieimage1_
        //            on (
        //                movieimage1_.movie_mno=movie0_.mno
        //            )
        //    left outer join
        //        review review2_
        //            on (
        //                review2_.movie_mno=movie0_.mno
        //            )
        //    where
        //        movie0_.mno=?
        //    group by
        //        movieimage1_.inum
        //[[Ljava.lang.Object;@72851503, [Ljava.lang.Object;@690c3b1f]
        //[Movie(mno=8, title=Movie....8), MovieImage(inum=21, uuid=70dd448f-2264-4613-adb1-2dbe591ae010, imgName=test0.jpg, path=null), 2.3333, 6]
        //[Movie(mno=8, title=Movie....8), MovieImage(inum=22, uuid=49efab7c-d00f-42f6-be8a-578a456ce1e4, imgName=test1.jpg, path=null), 2.3333, 6]
    }
}
