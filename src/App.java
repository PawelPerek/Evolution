import Config.Config;
import Engine.Engine;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
    private static Engine engine = new Engine();

    private static Config config;

    private int width = 1366;
    private int height = 768;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public static void generateConfig() {
        config = new Config(30, 20, 100, 1, 20, 0.3);
    }

    public static void main(String[] args) throws Exception {
        generateConfig();

        engine.start(config);

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        var root = new Group();
        var scene = new Scene(root, width, height, Color.WHITE);
        scene.widthProperty().addListener((obs, oldVal, newVal) -> setWidth(newVal.intValue()));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> setHeight(newVal.intValue()));
        var canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        var ctx = canvas.getGraphicsContext2D();
        stage.setTitle("Evolution");

        stage.setScene(scene);
        stage.show();

        renderGame(ctx);
    }

    private void renderMenu(GraphicsContext ctx) {
        ctx.scale((double) width / (double) config.width(), (double) height / (double) config.height());
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {

                engine.loop();
                for (int y = 0; y < config.height(); y++) {
                    for (int x = 0; x < config.width(); x++) {
                        ctx.setStroke(switch (engine.getState(x, y)) {
                            case Animal -> Color.RED;
                            case Plant -> Color.YELLOW;
                            case Jungle -> Color.DARKGREEN;
                            case Steppe -> Color.GREEN;
                        });
                        ctx.strokeRect(x, y, 1, 1);
                    }
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void renderGame(GraphicsContext ctx) {
        ctx.scale((double) width / (double) config.width(), (double) height / (double) config.height());
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {

                engine.loop();
                for (int y = 0; y < config.height(); y++) {
                    for (int x = 0; x < config.width(); x++) {
                        ctx.setStroke(switch (engine.getState(x, y)) {
                            case Animal -> Color.RED;
                            case Plant -> Color.YELLOW;
                            case Jungle -> Color.DARKGREEN;
                            case Steppe -> Color.GREEN;
                        });
                        ctx.strokeRect(x, y, 1, 1);
                    }
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
