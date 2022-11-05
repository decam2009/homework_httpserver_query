package org.example;

import java.io.BufferedOutputStream;
import java.io.IOException;

public class Main {

    private static final int PORT = 9090;

    public static void main(String[] args) {
        Server server = new Server();
        server.addHandler("GET", "/message", (request, responseStream) -> {
            try {
                responseStream.write(("Processing GET request \r\n" +
                        "Параметры запроса: " + request.getQueryParams() + "\r\n").getBytes());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

        server.addHandler("POST", "/message", new Handler() {
            @Override
            public void handle(Request request, BufferedOutputStream responseStream) {
                try {
                    responseStream.write((request.getBody() + "\r\n" +
                            "Значение параметра name : " + request.getQueryParam("name").getValue() + "\r\n").getBytes());
                    System.out.println("Processing POST request");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        server.listen(PORT);
    }
}