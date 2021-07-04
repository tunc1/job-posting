package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Experience;
import app.entity.Member;

public interface ExperienceRepository extends JpaRepository<Experience,Long>
{
    List<Experience> findByMember(Member member);
}