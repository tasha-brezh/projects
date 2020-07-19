package Metro;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Metro {
    public Map<String, List<String>> stations;
    public ArrayList<Line> lines;
    public ArrayList<List<Station>> connections;
    public Metro(Map<String, List<String>> stations, ArrayList<Line> lines, ArrayList<List<Station>> connections){
        this.stations = stations;
        this.lines = lines;
        this.connections = connections;
    }
    @Override
    public String toString(){
        return(stations.toString());
    }
}
