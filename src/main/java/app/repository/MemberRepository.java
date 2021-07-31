package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long>
{
    boolean existsByUserUsername(String username);
    boolean existsByEmail(String email);
    Member findByUserUsername(String username);
}