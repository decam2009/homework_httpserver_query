package org.example;

import org.apache.hc.core5.net.URIBuilder;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class Connection implements Runnable {

    private final Socket client;
    private final BufferedReader in;
    private final BufferedOutputStream out;

    public Connection(ServerSocket serverSocket) throws IOException {
        this.client = serverSocket.accept();
        this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.out = new BufferedOutputStream(client.getOutputStream());
    }

    @Override
    public void run() {
        try {
            final var requestLine = in.readLine();
            final var parts = requestLine.split(" ");

            if (parts.length != 3) {
                // just close socket
                client.close();
            }
            Request request = new Request(parts[0], parts[1], parts[0].equals("GET") ? null : requestLine);
            Server.handlers.get(request.getMethod()).get(request.getPathRequest()).handle(request, out);
            out.flush();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

