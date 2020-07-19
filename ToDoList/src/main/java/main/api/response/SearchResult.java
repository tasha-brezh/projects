package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import main.model.Point;

import java.util.List;
@Data
@AllArgsConstructor
public class SearchResult {
    int foundNumber;
    List<Point> resultList;
}
