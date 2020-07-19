package Metro;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class Line {
    @SerializedName("number")
    private String lineId;
    @SerializedName("name")
    private String lineName;

    public Line(String lineId, String lineName){
        this.lineId = lineId;
        this.lineName = lineName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(lineId, line.lineId) &&
                Objects.equals(lineName, line.lineName);
    }
}
