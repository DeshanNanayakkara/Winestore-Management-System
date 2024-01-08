package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DashboardFormController {

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    public void initialize() {
        Timeline clockTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm:ss");
            String currentDateTime = dateTimeFormat.format(new Date());
            lblTime.setText(currentDateTime);
        }));


        clockTimeline.setCycleCount(Timeline.INDEFINITE);

        clockTimeline.play();
        setDate();
    }
}
