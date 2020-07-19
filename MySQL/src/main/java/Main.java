import entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String query = "insert into LinkedPurchaseList (studentId, courseId) " +
                "select " + "student.id, course.id " +
                "from Course course inner join Subscription subscription on subscription.subscriptionId.courseId = course.id " +
                "inner join Student student on student.id = subscription.subscriptionId.studentId ";
        int newTable = session.createQuery(query).executeUpdate();

        System.out.println(newTable);

        transaction.commit();


        Course course = session.get(Course.class, 3);
        Student student = session.get(Student.class, 1);
        System.out.println(course.getName());
        System.out.println(student.getName() + ", возраст:  " + student.getAge());
        List<Subscription> subscriptionList = student.getSubscriptions();
        for(Subscription subscription: subscriptionList){
            System.out.println(subscription.getStudent().getName() + subscription.getCourse().getName() + subscription.getSubscriptionDate());
       }

        course.getSubscriptions().forEach(c -> System.out.println(c.getStudent().getName() + c.getCourse().getName() + c.getSubscriptionDate()));


        PurchaseList.PurchaseListId purchaseListId = new PurchaseList.PurchaseListId("Грузинский Парфен", "Java-разработчик");
        PurchaseList purchaseList = session.get(PurchaseList.class, purchaseListId);

        System.out.println(purchaseListId.getCourseName() + " " +  purchaseList.getPrice()  + " " +  purchaseList.getSubscriptionDate());
        sessionFactory.close();
    }
}
