package com.lg;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        User[] users = {
                new User("test_1", "test_1", "Kamil", "Zdun", SEX.MALE),
                new User("test_2", "test_2", "Bogdan", "Boner", SEX.FEMALE),
                new User("test_3", "test_3", "Krzysztof", "Łęcina", SEX.MALE),
                new User("test_4", "test_4", "Jan", "Kowalski", SEX.MALE),
                new User("test_5", "test_5", "Domino", "Jachaś", SEX.FEMALE),
        };
        Role[] roles = {
                new Role("Rola_1"),
                new Role("Rola_2"),
                new Role("Rola_3"),
                new Role("Rola_4"),
                new Role("Rola_5")
        };

        users[0].addRole(roles[3]);
        users[0].addRole(roles[1]);

        System.out.println("JPA project");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Hibernate_JPA");

        EntityManager em = factory.createEntityManager();

//        4.3.1
        try {
            em.getTransaction().begin();
            for(User u: users){
                em.persist(u);
            }
//            ChatGPT twierdzi ze mozna ładowac rekordy do kolejnej tabeli bez clear()
//              - użyc go do czyszczenia pamieci po wyslaniu pewnej ilosci rekordow w przypadku przesylania duzych ilosci danych
//            em.getTransaction().commit();
//            em.clear();
            for(Role r: roles){
                em.persist(r);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if( em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
//        finally {
//            em.close();
//            factory.close();
//        }

//        4.3.2
        try {
            User temp = em.find(User.class, (long)1);
            temp.setPassword("NOWE_HASLO");

            em.getTransaction().begin();
            em.merge(temp);
            em.getTransaction().commit();

        } catch (Exception e) {
            if( em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Nie odnaleziono użytkownika o id = 1, nie zaktualizowano rekordu");
        }
//        finally {
//            em.close();
//            factory.close();
//        }

//        4.3.3
        try {
            Role temp = em.find(Role.class, (long)5);

            em.getTransaction().begin();
            em.remove(temp);
            em.getTransaction().commit();


        } catch (Exception e) {
            if( em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Nie odnaleziono roli o id = 5, nie usunieto rekordu");
        }
//        finally {
//            em.close();
//            factory.close();
//        }

//        4.3.4
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM User u WHERE u.lastName = 'Kowalski'");
            List<User> kowalscy = query.getResultList();
            kowalscy.forEach(u -> System.out.println(u.toString()));
            em.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Nie odnaleziono użytkowników o nazwisku Kowalscy");
        }

//        4.3.5
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM User u WHERE u.Sex = 'FEMALE'");
            List<User> baby = query.getResultList();
//            baby.forEach(u -> System.out.println(u.toString()));
            baby.forEach(System.out::println);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Nie znaleziono żadnej kobiety w bazie danych");
        }
    }
}
