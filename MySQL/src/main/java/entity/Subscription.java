package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Subscriptions")
public class Subscription implements Serializable{
    @EmbeddedId
    private SubscriptionId subscriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    private Course course;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    private Student student;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    private  Subscription () {}

    public Subscription(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.subscriptionId = new SubscriptionId(student.getId(), course.getId());

    }
    @Embeddable
    public static class SubscriptionId implements Serializable {
        @Column(name = "student_id")
        private Integer studentId;

        @Column(name = "course_id")
        private Integer courseId;

        private SubscriptionId() {
        }

        public SubscriptionId(Integer studentId, Integer courseId) {
            this.studentId = studentId;
            this.courseId = courseId;
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

            SubscriptionId that = (SubscriptionId) o;
            return Objects.equals(studentId, that.studentId) &&
                    Objects.equals(courseId, that.courseId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(studentId, courseId);
        }
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

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}
