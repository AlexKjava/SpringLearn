package org.springframework.beans.factory;

import org.springframework.beans.factory.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.Provider;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
    private Map<String, Object> singletons = new HashMap();

    public Object getBean(String beanName){
        return singletons.get(beanName);
    }
    public void instantiate(String basePackage) {
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            String path = basePackage.replace('.', '/'); //"com.kciray" -> "com/kciray"
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();

                File file = new File(resource.toURI());
                for(File classFile : file.listFiles()){
                    String fileName = classFile.getName();//ProductService.class
                    System.out.println(fileName);
                    if (fileName.endsWith(".class")) {
                        String className = fileName.substring(0, fileName.lastIndexOf("."));

                        Class classObject = Class.forName(basePackage + "." + className);

                        if (classObject.isAnnotationPresent(Component.class) || classObject.isAnnotationPresent(Provider.Service.class)) {
                            System.out.println("Component: " + classObject);

                            Object instance = classObject.newInstance();//=new CustomClass()
                            String beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
                            singletons.put(beanName, instance);
                        }
                    }
                }
            }
        } catch (IOException | URISyntaxException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        }
    }
}