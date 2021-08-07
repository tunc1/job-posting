package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long>
{
    Admin findByUserUsername(String username);
}