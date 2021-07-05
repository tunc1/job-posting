package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.AppliedJob;
import app.entity.Member;

public interface AppliedJobRepository extends JpaRepository<AppliedJob,Long>
{
    List<AppliedJob> findByMember(Member member);
}