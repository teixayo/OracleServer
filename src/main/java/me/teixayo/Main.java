package me.teixayo;


import lombok.SneakyThrows;
import me.teixayo.server.Server;


public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {

                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();

//        AnsiConsole.systemInstall();

        Server server = new Server();
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.stop();
        }));

    }

}
