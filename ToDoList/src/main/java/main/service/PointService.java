package main.service;

import lombok.RequiredArgsConstructor;
import main.model.Point;
import main.model.PointRepository;
import main.api.response.SearchResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    public List<Point> findAll(){
        return pointRepository.findAll();
    }

    public Optional<Point> findById(Long id) {
        return pointRepository.findById(id);
    }

    public Point save (Point point) {
       return pointRepository.save(point);
    }

    public void deleteById(Long id) {
        synchronized (this) {
            pointRepository.deleteById(id);
        }
    }

    public void deletePoints(){
       pointRepository.deleteAll();
    }

    public int pointsCount(){
        return pointRepository.findAll().size();
    }

    public Point edit (Long id, String name) {
        Point point = pointRepository.findById(id).get();
        point.setName(name);
        return pointRepository.save(point);
    }

    public SearchResult search (String query) {
        List<Point> pointList = new ArrayList<>();
        if (query != null) {
            pointList = pointRepository.findByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(query, query);
        }
        SearchResult searchResult = new SearchResult(pointList.size(), pointList);
        return searchResult;
    }
}
