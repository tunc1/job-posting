package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Education;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education,Long>
{
    List<Education> findAllByMemberId(Long memberId);
}