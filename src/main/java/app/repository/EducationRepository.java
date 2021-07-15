package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.entity.Education;
import app.entity.Member;

public interface EducationRepository extends JpaRepository<Education,Long>
{
    List<Education> findByMember(Member member);
    @Query(value="Select member_id from Education where id=:id",nativeQuery=true)
    Long getMemberId(@Param("id") Long id);
}