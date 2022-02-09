package com.kciray;
import org.springframework.beans.factory.BeanFactory;
//import org.springframework.context.ApplicationContext;

public class Main {
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.instantiate("com.kciray");
        ProductService productService = (ProductService) beanFactory.getBean("productService");
        System.out.println(productService);
    }

}
