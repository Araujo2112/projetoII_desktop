package com.example.frontendjavafx.utils;

public class DashboardRouter {
    public static void openDashboard(String tipoUtilizador) {
        switch (tipoUtilizador.toLowerCase()) {
            case "gestor":
                SceneManager.switchScene("gestor/dashboard_gestor.fxml");
                break;
            case "rececionista":
                SceneManager.switchScene("rececionista/dashboard_rececionista.fxml");
                break;
            case "manutencao":
                SceneManager.switchScene("manutencao/dashboard_manutencao.fxml");
                break;
            default:
                System.out.println("Tipo de utilizador inválido.");
        }
    }
}
