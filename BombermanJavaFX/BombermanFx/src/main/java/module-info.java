/*
 On définit un 'module' pour notre application
 */
module app {

    /*
     Liste des modules requis
     */
    requires javafx.controls;
    requires javafx.fxml;       // pour plus tard, quand on utilisera FXML
    requires java.desktop;

    /*
     Autorise javafx.fxml à examiner les classes de 'demo' par introspection (reflection)
     */
    opens bomberman.app to javafx.fxml;  // pour plus tard, quand on utilisera FXML

    /*
     Expose toutes les éléments publics des packages suivants (requis pour pouvoir exécuter)
     */
    exports bomberman.app;
}