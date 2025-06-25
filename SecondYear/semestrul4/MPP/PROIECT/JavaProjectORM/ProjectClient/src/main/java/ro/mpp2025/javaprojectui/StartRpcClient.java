package ro.mpp2025.javaprojectui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mpp2025.javaprojectui.gui.Home;
import ro.mpp2025.javaprojectui.gui.Login;
import ro.mpp2025.javaprojectui.rpcprotocol.ProjectServicesRpcProxy;

import java.io.IOException;
import java.util.Properties;


public class StartRpcClient extends Application {
    private static int defaultChatPort=55555;
    private static String defaultServer="localhost";

    public void start(Stage primaryStage) throws IOException, ProjectException {
        Properties clientProps=new Properties();
        try {
            clientProps.load(StartRpcClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties "+e);
            return;
        }

        String serverIP=clientProps.getProperty("server.host",defaultServer);

        int serverPort=defaultChatPort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultChatPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);

        ProjectServices server = new ProjectServicesRpcProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(
                StartRpcClient.class.getClassLoader().getResource("login.fxml")
        );

        Parent root=loader.load();

        Login loginCtrl = loader.<Login>getController();
        loginCtrl.setServer(server);


        FXMLLoader mainLoader = new FXMLLoader(
                StartRpcClient.class.getClassLoader().getResource("home.fxml")
        );

        Parent mainRoot = mainLoader.load();

        Home mainCtrl = mainLoader.<Home>getController();
        mainCtrl.setServer(server);

        loginCtrl.setMainController(mainCtrl);
        loginCtrl.setParent(mainRoot);

        primaryStage.setTitle("Concurs");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


}
