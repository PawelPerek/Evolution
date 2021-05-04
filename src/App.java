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

    private final int WIDTH = 800;
    private final int HEIGHT = 600;

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
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        Canvas canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);
        GraphicsContext ctx = canvas.getGraphicsContext2D();
        stage.setTitle("Evolution");

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        ctx.scale((double) WIDTH / (double) config.width(), (double) HEIGHT / (double) config.height());
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
