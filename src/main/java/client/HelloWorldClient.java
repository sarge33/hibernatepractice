package client;

import entity.*;
import org.hibernate.Session;

import org.hibernate.Transaction;
import util.HibernateUtil;
//import domain.Message;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class HelloWorldClient {
    static final Logger logger = Logger.getLogger(HelloWorldClient.class);

    public static void main(String[] args) {
//        mappingByEntityOrByXmlExample();
//        transactionExample();
//        aggregationExample();
//        associationExample();
//        cascadePersistExample();
//        cascadeDeleteExample();
//        oneToManyExample();
//        oneToOneMappingExample();
//        manyToManyExample();
        enumMappingExample();
    }


    public static void mappingByEntityOrByXmlExample() {

        //Configure logger
        BasicConfigurator.configure();
        logger.debug("Hello World!");

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Message message1 = new Message("Hello World with Hibernate & JPA Annotations");
        Message message2 = new Message("Hello World with log4j - 2");


        //Updating objects

//        Message message3 = (Message) session.get(Message.class, 2L);
//        message3.setText("Hello Automatic Dirty Checking");
//        session.save(message3);


        session.save(message1);
        session.save(message2);
        session.getTransaction().commit();
        session.close();

    }


    public static void transactionExample() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.getTransaction();
        try {
            txn.begin();

            //Finding objects
//            Message msg = (Message) session.get(Message.class, 2L);


            //Updating objects

//            Message msg = (Message) session.get(Message.class, 2L);
//            msg.setText("Hello Automatic Dirty Checking");


            //Deleting objects
            Message msg = (Message) session.get(Message.class, 2L);
            session.delete(msg);


            txn.commit();
        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public static void aggregationExample() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.getTransaction();
        try {
            txn.begin();

            Address address = new Address("200 E Main St", "Seattle", "85123");
            Person person = new Person("Ruby", address);

            session.save(person);

            txn.commit();
        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }


    public static void associationExample() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.getTransaction();
        try {
            txn.begin();

            Guide guide = new Guide("2000MO10789", "Mike Lawson", 1000);
            Student student = new Student("2014JT50123", "John Smith", guide);

            session.save(guide);
            session.save(student);

            txn.commit();
        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public static void cascadePersistExample() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.getTransaction();
        try {
            txn.begin();

            // persisting Student object

            Guide guide = new Guide("2000IM10901", "Ian Lamb", 2000);
            Student student = new Student("2014AL50456", "Amy Gill", guide);

            session.persist(student);

            txn.commit();
        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public static void cascadeDeleteExample() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.getTransaction();
        try {
            txn.begin();

            // deleting Student object
            Student student = (Student) session.get(Student.class, 2L);
            session.delete(student);

            txn.commit();
        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }


    public static void oneToManyExample() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.getTransaction();
        try {
            txn.begin();

//            Guide guide1 = new Guide("2000MO10789", "Mike Lawson", 1000);
//            Guide guide2 = new Guide("2000IM10901", "Ian Lamb", 2000);
//
//            Student student1 = new Student("2014JT50123", "John Smith", guide1);
//            Student student2 = new Student("2014AL50456", "Amy Gill", guide1);
//
//            guide1.getStudents().add(student1);
//            guide1.getStudents().add(student2);
//
//            session.persist(guide1);
//            session.persist(guide2);

            //Updating inverse end, the followings won't work, since guide doesn't know the relationship
            //        only student knows, b/c guide is a foreign key in student table
            /*
            Guide guide = (Guide) session.get(Guide.class, 2L);
            Student student = (Student) session.get(Student.class, 2L);
            guide.getStudents().add(student);
            */

            //Updating owner
            /*
            Guide guide = (Guide) session.get(Guide.class, 4L);
            Student student = (Student) session.get(Student.class, 2L);
            student.setGuide(guide);
            */

            //Updating inverse end (after adding addStudent(Student) in Guide entity)
            Guide guide = (Guide) session.get(Guide.class, 1L);
            Student student = (Student) session.get(Student.class, 2L);
            guide.addStudent(student);

            txn.commit();
        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public static void oneToOneMappingExample() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.getTransaction();
        try {
            txn.begin();

            Passport passport = new Passport("925076473");
            Customer customer = new Customer("Nicole Scott", passport);

            session.persist(customer);

            txn.commit();
        }	catch(Exception e) {
            if(txn != null) { txn.rollback(); }
            e.printStackTrace();
        }	finally {
            if(session != null) { session.close(); }
        }

    }

    public static void manyToManyExample() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction txn = session.getTransaction();
        try {
            txn.begin();

            //persisting the ManyToMany relationship between the Movie and Actor objects
//            Movie movie1 = new Movie("American Hustle");
//            Movie movie2 = new Movie("The Prestige");
//
//            Actor actor1 = new Actor("Christian Bale");
//            Actor actor2 = new Actor("Hugh Jackman");
//
//            movie1.getActors().add(actor1);
//
//            movie2.getActors().add(actor1);
//            movie2.getActors().add(actor2);
//
//            session.persist(movie1);
//            session.persist(movie2);

            // Updating the inverse end (Actor)
//            Movie movie = (Movie) session.get(Movie.class, 1L);
//            Actor actor = (Actor) session.get(Actor.class, 2L);
//            actor.getMovies().add(movie);


            // Updating the owner (Movie)
            Movie movie = (Movie) session.get(Movie.class, 1L);
            Actor actor = (Actor) session.get(Actor.class, 2L);
            movie.getActors().add(actor);

            txn.commit();
        }	catch(Exception e) {
            if(txn != null) { txn.rollback(); }
            e.printStackTrace();
        }	finally {
            if(session != null) { session.close(); }
        }

    }

    public static void enumMappingExample() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        //persisting
//        Employee2 employee1 = new Employee2("Josh Stockham", "2014JA11001", EmployeeStatus.FULL_TIME);
//        Employee2 employee2 = new Employee2("Ammie Corrio", "2014AI21543", EmployeeStatus.PART_TIME);
//        Employee2 employee3 = new Employee2("Ernie Stenseth", "2014ET25100", EmployeeStatus.CONTRACT);
//
//        session.persist(employee1);
//        session.persist(employee2);
//        session.persist(employee3);

		Employee2 employee = (Employee2) session.get(Employee2.class, 2L);
		System.out.println(employee);

        session.getTransaction().commit();
        session.close();

    }
}

