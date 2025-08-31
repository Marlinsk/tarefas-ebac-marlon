package main.java.com.mir;

import main.java.com.mir.domain.Produto;
import main.java.com.mir.utils.AnnotationReader;

public class Main {
    public static void main(String[] args) {
        Class<Produto> classeProduto = Produto.class;
        AnnotationReader.printAnnotations(classeProduto);
    }
}