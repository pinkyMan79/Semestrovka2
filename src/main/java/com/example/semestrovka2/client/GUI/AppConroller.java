package com.example.semestrovka2.client.GUI;

import com.example.semestrovka2.client.logic.OnUpdateController;
import com.example.semestrovka2.client.models.Character;
import com.example.semestrovka2.packetsss.ClientRequestPacket;
import com.example.semestrovka2.packetsss.ServerResponsePacket;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AppConroller extends Application {

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    public static ArrayList<Rectangle> bonuses = new ArrayList<>();

    private Socket socket;

    FileInputStream inputstream;
    {
        try {
            inputstream = new FileInputStream("/home/lino/Documents/Semestrovka2/src/main/java/com/example/semestrovka2/graphics/img.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    Image image = new Image(inputstream);
    ImageView imageView = new ImageView(image);
    Character player = new Character(imageView);
    public static Pane root = new Pane();


    public void bonus(){
        int random = (int)Math.floor(Math.random()*100);
        int x = (int)Math.floor(Math.random()*600);
        int y = (int)Math.floor(Math.random()*600);
        if(random == 5){
            Rectangle rect = new Rectangle(20,20, Color.RED);
            rect.setX(x);
            rect.setY(y);
            bonuses.add(rect);
            root.getChildren().addAll(rect);
        }
    }
    public void update() {
        OnUpdateController updateController = () ->{
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ServerResponsePacket responsePacket = (ServerResponsePacket) objectInputStream.readObject();
            String content = responsePacket.getContent();

            if (Objects.equals(content, "UP")){
                player.animation.play();
                player.animation.setOffsetY(96);
                player.moveY(-2);
            } else if (Objects.equals(content, "DOWN")) {
                player.animation.play();
                player.animation.setOffsetY(0);
                player.moveY(2);
            }else if (Objects.equals(content, "RIGHT")){
                player.animation.play();
                player.animation.setOffsetY(64);
                player.moveX(2);
            } else if (Objects.equals(content, "LEFT")) {
                player.animation.play();
                player.animation.setOffsetY(64);
                player.moveX(2);
            }else{
                player.animation.stop();
            }

            if (isPressed(KeyCode.UP)) {
                player.animation.play();
                player.animation.setOffsetY(96);
                player.moveY(-2);
                objectOutputStream.writeObject(ClientRequestPacket.builder().code("UP").build());
            } else if (isPressed(KeyCode.DOWN)) {
                objectOutputStream.writeObject(ClientRequestPacket.builder().code("DOWN").build());
            } else if (isPressed(KeyCode.RIGHT)) {
                objectOutputStream.writeObject(ClientRequestPacket.builder().code("RIGHT").build());
            } else if (isPressed(KeyCode.LEFT)) {
                objectOutputStream.writeObject(ClientRequestPacket.builder().code("LEFT").build());
            }
            else{
                player.animation.stop();
            }
        };
    }
    public boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        root.setPrefSize(600,600);
        root.getChildren().addAll(player);
        socket = new Socket("localhost", 5555);
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(),true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                bonus();
            }
        };
        timer.start();
        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }


}
