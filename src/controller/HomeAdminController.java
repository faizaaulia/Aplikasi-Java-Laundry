/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.HomeAdmin;
import model.HomeSuperAdmin;
import view.HomeAdminView;
import view.HomeSuperAdminView;

/**
 *
 * @author faizaaulia
 */
public class HomeAdminController {
    HomeAdminView view;
    HomeAdmin model;
    
    public HomeAdminController(String nama) {
        view = new HomeAdminView();
        model = new HomeAdmin();
        view.setVisible(true);
        view.setLabelHi(nama);
    }
}
