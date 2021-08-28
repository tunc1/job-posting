package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.City;

public interface CityRepository extends JpaRepository<City,Long>{}