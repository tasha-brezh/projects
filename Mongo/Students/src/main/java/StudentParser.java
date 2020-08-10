import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

public class StudentParser {
    private String studentDatabase;
    private ArrayList<Student> studentList;
    private final int INDEX_NAME = 0;
    private final int INDEX_AGE = 1;
    private final int INDEX_COURSES = 2;

    StudentParser(String path) {
        this.studentDatabase = path;
    }

    ArrayList<Student> loadDataFromFile() {
        studentList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(studentDatabase));
            for (String line : lines) {
                ArrayList<String> lineList = parseLine(line);
                ArrayList<String> columnList = writeValues(lineList);
                Student student = createStudent(columnList);
                studentList.add(student);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return studentList;
    }

    ArrayList<String> parseLine(String line){
        int courseIndex = line.indexOf("\"");
        ArrayList<String> fragmentsList = new ArrayList<>();
        String[] fragments = line.substring(0, courseIndex).split(",");
        fragmentsList.add(fragments[0]);
        fragmentsList.add(fragments[1]);
        fragmentsList.add(line.substring(courseIndex).trim());
        return fragmentsList;
    }

    ArrayList<String> writeValues (ArrayList<String> lineList){
        ArrayList<String> columnList = new ArrayList<String>();
        for (int i = 0; i < lineList.size(); i++) {
            columnList.add(lineList.get(i));
        }
        return columnList;
    }

    Student createStudent(ArrayList<String> columnList){
        Student student = new Student(
                columnList.get(INDEX_NAME),
                Integer.parseInt(columnList.get(INDEX_AGE)),
                columnList.get(INDEX_COURSES).split(","));

        return student;
    }

    ArrayList<Student> getStudentList(){
        return studentList ;
    }
}



