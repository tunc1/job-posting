package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long>
{
    Member findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}