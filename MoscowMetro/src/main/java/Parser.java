import Metro.Metro;
import Metro.Station;
import Metro.Line;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

public class Parser {
    static String jsonFilePath = "src\\main\\resources\\map.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    static ArrayList<String> stations = new ArrayList<>();
    static ArrayList<String> lines = new ArrayList<>();
    static ArrayList<List<String>> connectionNumberList = new ArrayList<>();
    static ArrayList<List<String>> connectionNameList = new ArrayList<>();

    public static ArrayList<Station> createStations() {
        ArrayList<Station> stationArrayList = new ArrayList<>();
        for (String stationData: stations) {
            String[] stationAttributes = stationData.split(",");
            Station station = new Station(stationAttributes[0], stationAttributes[1]);
            if(!(stationArrayList.contains(station))) {
                stationArrayList.add(station);
            }
        }
        return stationArrayList;
        }

        public static ArrayList<Line> createLines() {
            ArrayList<Line> linesArrayList = new ArrayList<>();
            for (String lineData : lines) {
                String[] lineAttributes = lineData.split(",");
                Line line = new Line(lineAttributes[0], lineAttributes[1]);
                if (!linesArrayList.contains(line)) {
                    linesArrayList.add(line);
                }
            }
                return linesArrayList;
            }

        public static ArrayList<List<Station>> createConnections() {
            ArrayList<List<Station>> connectionList = new ArrayList<>();
            for(int i =0; i < connectionNameList.size(); i++) {
                if (connectionNameList.get(i).size() != 0 && !(connectionNumberList.get(i).contains("14"))) {
                    List<Station> connections = new ArrayList<>();
                    connections.add(createStations().get(i));
                    for (Station station : createStations()) {
                        if (connectionNameList.get(i).toString().contains(station.getStationName()) && !connections.contains(station)) {
                            connections.add(station);
                        }
                    }
                    connectionList.add(connections);
                }
             }
             return connectionList;
        }

    public static Map<String, List<String>> createStationList(){
        Map<String, List<String>> stationResultList = createStations().stream()
                .collect(Collectors.groupingBy(Station::getLineNumber, mapping(Station::getStationName, toList())));
        return stationResultList;
    }



    public static void createFile(){
        Metro metro = new Metro(createStationList(), createLines(), createConnections());
        String json =
                new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().
                        create().toJson(metro);
        try(
            FileWriter file = new FileWriter(jsonFilePath))

            {
                //file.write(GSON.toJson(metro));
                file.write(json);

         } catch(
            IOException e)
            {
                e.printStackTrace();
            }
        }

       public static  Map<String, Object> readFromFile()
       throws IOException {
       Gson gson = new Gson();
           try (final InputStream inputStream = new FileInputStream(jsonFilePath);
                final Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
               Map<String, Object> map = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {
               }.getType());
               Map<String, List<String>> st = (Map<String, List<String>>) map.get("stations");
               for (Map.Entry entry : st.entrySet()) {
                   System.out.println("Линия " + entry.getKey() + ", количество станций " + ((List)(entry.getValue())).size());
               }
                   return map;
               }
           }
       }









