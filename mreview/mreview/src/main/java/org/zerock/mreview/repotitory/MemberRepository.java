package org.zerock.mreview.repotitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {


}
