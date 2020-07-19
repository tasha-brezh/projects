package main.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.service.PointService;
import main.model.Point;
import main.api.response.SearchResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/points")
public class PointController {
    private final PointService pointService;

    @GetMapping
    public ResponseEntity<List<Point>> findAll(){
        return ResponseEntity.ok(pointService.findAll());
    }

    @PostMapping
    public ResponseEntity<Point> create(@Valid @RequestBody Point point){
        return ResponseEntity.ok(pointService.save(point));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Point> findById(@PathVariable Long id) {
        Optional<Point> point = pointService.findById(id);
        return pointService.findById(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Point> update(@PathVariable Long id, @Valid @RequestBody Point point) {
        if (!pointService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(pointService.save(point));
    }

    @PatchMapping("/todolist/{id}")
    public ResponseEntity<Point> edit(@PathVariable long id, String name) {
        Optional<Point> point = pointService.findById(id);
        if (!point.isPresent()) {
           return ResponseEntity.notFound().build();
        }
        pointService.edit(id, name);
        return new ResponseEntity<>(point.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Point> delete(@PathVariable Long id) {
        Optional<Point> point = pointService.findById(id);
        if (!point.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        pointService.deleteById(id);
        return new ResponseEntity<>(point.get(), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Point> deletePoints(){
       if(pointService.pointsCount() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        pointService.deletePoints();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/api/v1/points/search")
    @ResponseBody
    public ResponseEntity<SearchResult> search (@PathParam(value="query") String query) {
        return new ResponseEntity<>(pointService.search(query), HttpStatus.OK);
    }
}


