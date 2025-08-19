package main.java.com.projeto1;

import main.java.com.projeto1.dao.ClienteMapDao;
import main.java.com.projeto1.ui.ClienteFrame;

public class Main {
    public static void main(String[] args) {
        ClienteMapDao dao = new ClienteMapDao();
        ClienteFrame frame = new ClienteFrame(dao);
        frame.setVisible(true);
    }
}