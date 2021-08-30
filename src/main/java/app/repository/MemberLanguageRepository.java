package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.MemberLanguage;

import java.util.List;

public interface MemberLanguageRepository extends JpaRepository<MemberLanguage,Long>
{
    List<MemberLanguage> findAllByMemberId(long id);
}