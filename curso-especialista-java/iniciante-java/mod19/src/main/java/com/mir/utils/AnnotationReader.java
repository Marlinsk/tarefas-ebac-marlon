package main.java.com.mir.utils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationReader {
    public static void printAnnotations(Class<?> clazz) {
        Annotation[] annotations = clazz.getAnnotations();

        if (annotations.length > 0) {
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                System.out.println("Anotação encontrada: " + annotationType.getSimpleName());

                if (annotationType.getDeclaredMethods().length > 0) {
                    System.out.println("Atributos & Valores:");
                    for (Method method : annotationType.getDeclaredMethods()) {
                        try {
                            Object value = method.invoke(annotation);
                            System.out.println(" - " + method.getName() + " = " + value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("Atributos & Valores: Nenhum encontrado!");
                }
            }
        } else {
            System.out.println("Nenhuma anotação encontrada na classe: " + clazz.getSimpleName());
        }
    }
}
