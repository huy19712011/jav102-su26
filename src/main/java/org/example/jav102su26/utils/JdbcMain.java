package org.example.jav102su26.utils;


public class JdbcMain {

    public static void main(String[] args) {

        //DatabaseConnectionManager dcm = new DatabaseConnectionManager("jav102_su26", "sa", "123456");

        try (var em = EntityManagerUtils.getEntityManager()) {

            System.out.println("Created tables...");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}
