package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "LinkedPurchaseList")
@IdClass(LinkedPurchaseList.LinkedPurchaseListId.class)
public class LinkedPurchaseList implements Serializable {
    @Id
    @Column(name = "student_id", columnDefinition = "int(10) unsigned", nullable = false)
    Integer studentId;

    @Id
    @Column(name = "course_id", columnDefinition = "int(10) unsigned", nullable = false)
    Integer courseId;

    @ManyToOne
    @MapsId("courseId")
    private Course course;

    @ManyToOne
    @MapsId("studentId")
    private Student student;

    private LinkedPurchaseList() {
    }

    public LinkedPurchaseList(Student student, Course course) {
        this.student = student;
        this.course = course;

    }

    @Embeddable
    public static class LinkedPurchaseListId implements Serializable {
        Integer studentId;

        Integer courseId;

        public LinkedPurchaseListId(Integer studentId, Integer courseId) {
            this.studentId = studentId;
            this.courseId = courseId;
        }

        private LinkedPurchaseListId() {
        }

        public Integer getStudentId() {
            return studentId;
        }

        public void setStudentId(Integer studentId) {
            this.studentId = studentId;
        }

        public Integer getCourseId() {
            return courseId;
        }

        public void setCourseId(Integer courseId) {
            this.courseId = courseId;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (o == null || getClass()!= o.getClass())
                return false;

            LinkedPurchaseList.LinkedPurchaseListId that = (LinkedPurchaseList.LinkedPurchaseListId) o;
            return Objects.equals(studentId, that.studentId) &&
                    Objects.equals(courseId, that.courseId);
        }
        @Override
        public int hashCode() {
            return Objects.hash(studentId, courseId);
        }
    }



    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
