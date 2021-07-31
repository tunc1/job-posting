package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.User;

public interface UserRepository extends JpaRepository<User,Long>
{
    User findByUsername(String username);
    boolean existsByUsername(String username);
}