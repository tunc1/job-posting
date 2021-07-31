package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager,Long>
{
    Manager findByUserUsername(String username);
}