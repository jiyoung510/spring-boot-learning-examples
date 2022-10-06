package org.zerock.mreview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long mno;
    private String title;

    @Builder.Default        // 화면에 이미지들도 같이 수집하여 전달해야 하므로 내부적으로 리스트 이용해서 수집
    // Builder.Default :  Builder로 인스턴스 생성시 초기화할 값을 정할 수 있다.
    private List<MovieImageDTO> imageDTOList = new ArrayList<>();

    // 영화의 평균 평점
    private double avg;

    // 리뷰 수 jpa의 count()
    private int reviewCnt;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

}
