import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class Timer extends Pane {

    private static Timer instance = null;

    public static Timer getInstance() {
        if (instance == null) {
            instance = new Timer();
        }
        return instance;
    }

    protected Timer() {

        whiteHbox = new HBox();
        blackHbox = new HBox();


        // Bind the timerLabel text property to the timeSeconds property
        timerLabelMinutesWhite.textProperty().bind(timeMinutesWhite.divide(60).asString());
        timerLabelSecondsWhite.textProperty().bind(timeMinutesWhite.subtract(timeMinutesWhite.divide(60).multiply(60)).asString());
        timerLabelMinutesWhite.setStyle("-fx-font-size: 2em;");
        timerLabelSecondsWhite.setStyle("-fx-font-size: 2em;");
        sep1.setStyle("-fx-font-size: 2em;");
        sep2.setStyle("-fx-font-size: 2em;");

        timerLabelMinutesBlack.textProperty().bind(timeMinutesBlack.divide(60).asString());
        timerLabelSecondsBlack.textProperty().bind(timeMinutesBlack.subtract(timeMinutesBlack.divide(60).multiply(60)).asString());
        timerLabelMinutesBlack.setStyle("-fx-font-size: 2em;");
        timerLabelSecondsBlack.setStyle("-fx-font-size: 2em;");

        if (timelineWhite != null) {
            timelineWhite.stop();
        }

        timeMinutesWhite.set(STARTTIME);
        timelineWhite = new Timeline();
        timelineWhite.getKeyFrames().add(new KeyFrame(Duration.seconds(STARTTIME+1), new KeyValue(timeMinutesWhite, 0)));

        if (timelineBlack != null) {
            timelineBlack.stop();
        }

        timeMinutesBlack.set(STARTTIME);
        timelineBlack = new Timeline();
        timelineBlack.getKeyFrames().add(new KeyFrame(Duration.seconds(STARTTIME+1), new KeyValue(timeMinutesBlack, 0)));

        whiteHbox.getChildren().addAll(timerLabelMinutesWhite,sep1,timerLabelSecondsWhite);
        blackHbox.getChildren().addAll(timerLabelMinutesBlack,sep2,timerLabelSecondsBlack);

    }

    public void startWhiteTimer() {
        whiteIsRunning = true;
        timelineWhite.playFromStart();
        timerLabelMinutesWhite.setTextFill(Color.web("BE6D5F"));
        timerLabelSecondsWhite.setTextFill(Color.web("BE6D5F"));
        sep1.setTextFill(Color.web("BE6D5F"));
    }

    public void stopWhiteTimer() {
        timelineWhite.stop();
        whiteIsRunning = false;
        timerLabelMinutesWhite.setTextFill(Color.BLACK);
        timerLabelSecondsWhite.setTextFill(Color.BLACK);
        sep1.setTextFill(Color.BLACK);
    }

    public void startBlackTimer() {
        timelineBlack.playFromStart();
        blackIsRunning = true;
        timerLabelMinutesBlack.setTextFill(Color.web("BE6D5F"));
        timerLabelSecondsBlack.setTextFill(Color.web("BE6D5F"));
        sep2.setTextFill(Color.web("BE6D5F"));

    }

    public void stopBlackTimer() {
        timelineBlack.stop();
        blackIsRunning = false;
        timerLabelMinutesBlack.setTextFill(Color.BLACK);
        timerLabelSecondsBlack.setTextFill(Color.BLACK);
        sep2.setTextFill(Color.BLACK);
    }

    public void resetBothTimer(){
        if(whiteIsRunning){
            timelineWhite.stop();
            timerLabelMinutesWhite.setTextFill(Color.BLACK);
            timerLabelSecondsWhite.setTextFill(Color.BLACK);
            sep1.setTextFill(Color.BLACK);
        }else{
            timelineBlack.stop();
            timerLabelMinutesBlack.setTextFill(Color.BLACK);
            timerLabelSecondsBlack.setTextFill(Color.BLACK);
            sep2.setTextFill(Color.BLACK);
        }

        timeMinutesWhite.set(STARTTIME);
        timeMinutesBlack.set(STARTTIME);
    }

    private HBox hbox;
    public HBox whiteHbox;
    public HBox blackHbox;
    public boolean whiteIsRunning;
    public boolean blackIsRunning;
    private static final int STARTTIME = 900;
    private Timeline timelineWhite;
    private Timeline timelineBlack;
    private Label timerLabelMinutesWhite = new Label();
    private Label timerLabelSecondsWhite = new Label();
    private Label sep1 = new Label(":");
    private Label sep2 = new Label(":");

    private Label timerLabelMinutesBlack = new Label();
    private Label timerLabelSecondsBlack = new Label();

    private Separator separator = new Separator();
    private SimpleIntegerProperty timeMinutesWhite = new SimpleIntegerProperty(STARTTIME);
    private SimpleIntegerProperty timeMinutesBlack = new SimpleIntegerProperty(STARTTIME);
}

