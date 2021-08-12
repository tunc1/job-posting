package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Skill;

public interface SkillRepository extends JpaRepository<Skill,Long>{}