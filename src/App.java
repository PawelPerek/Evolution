public class App {
    
    public static void main(String[] args) throws Exception {
        var engine = new Engine();

        engine.generateConfig();

        engine.start();
    }
}
