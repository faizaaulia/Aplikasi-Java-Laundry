package controller;

import model.Home;
import view.HomeView;

/** @author faizaaulia */

public class HomeController {
    HomeView view;
    Home model;
    
    public HomeController(String nama) {
        view = new HomeView();
        model = new Home();
        view.setVisible(true);
        view.setLabelHi(nama);
    }
}
