package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.AppliedJob;

public interface AppliedJobRepository extends JpaRepository<AppliedJob,Long>{}