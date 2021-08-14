package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Language;

public interface LanguageRepository extends JpaRepository<Language,Long>{}