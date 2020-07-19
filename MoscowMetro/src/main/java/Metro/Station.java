package Metro;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class Station {
    @SerializedName("name")
    private String stationName;
    @SerializedName("number")
    private String lineNumber;

    public Station(String lineNumber, String stationName){
        this.stationName = stationName;
        this.lineNumber = lineNumber;
    }

    public String getLineNumber(){
        return lineNumber;
    }

    public String getStationName(){
        return stationName;
    }

    @Override
    public String toString(){
       return(this.lineNumber + "  " + this.stationName);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(stationName, station.stationName) &&
                Objects.equals(lineNumber, station.lineNumber);
    }
}
