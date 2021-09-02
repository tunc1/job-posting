package app.repository;

import app.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience,Long>
{
    List<Experience> findAllByMemberId(Long memberId);
}