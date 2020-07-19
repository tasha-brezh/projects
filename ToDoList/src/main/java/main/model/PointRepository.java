package main.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(String name, String description);
}
