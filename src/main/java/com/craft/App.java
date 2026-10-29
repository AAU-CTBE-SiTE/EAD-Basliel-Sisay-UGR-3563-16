package com.craft;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        try (var context = new ClassPathXmlApplicationContext("beansConfig.xml")) {
            ApplicationContext beans = context;
            System.out.println(beans.getBean("setterStudent", Student.class).getDetails());
            System.out.println(beans.getBean("constructorStudent", Student.class).getDetails());
            Student singleton1 = beans.getBean("singletonStudent", Student.class);
            Student singleton2 = beans.getBean("singletonStudent", Student.class);
            Student prototype1 = beans.getBean("prototypeStudent", Student.class);
            Student prototype2 = beans.getBean("prototypeStudent", Student.class);
            System.out.println("Singleton same instance: " + (singleton1 == singleton2));
            System.out.println("Prototype different instances: " + (prototype1 != prototype2));
        }
    }
}
