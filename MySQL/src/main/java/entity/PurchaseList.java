package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "PurchaseList")
public class PurchaseList {
    @EmbeddedId
    private PurchaseListId purchaseListId;

    @Column(name = "student_name", insertable=false, updatable=false)
    private String studentName;

    @Column(name = "course_name", insertable=false, updatable=false)
    private String courseName;

    private int price;



    @Column(name = "subscription_date")
    private LocalDateTime subscriptionDate;

    public PurchaseList(String studentName, String courseName) {
        this.studentName = studentName;
        this.courseName = courseName;
        this.purchaseListId = new PurchaseListId(studentName, courseName);

    }

    public PurchaseList() { }
    @Embeddable
    public static class PurchaseListId implements Serializable{

        @Column(name = "student_name")
        private String studentName;

        @Column(name = "course_name")
        private String courseName;



        private PurchaseListId() { }

        public PurchaseListId(String studentName, String courseName) {
            this.studentName = studentName;
            this.courseName = courseName;
        }
        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (o == null || getClass()!= o.getClass())
                return false;

            PurchaseListId that = (PurchaseListId) o;
            return Objects.equals(studentName, that.studentName) &&
                    Objects.equals(courseName, that.courseName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(studentName, courseName);
        }
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

        public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
