package ex5;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class HttpServerTest {
        public static void main(String[] args) {
            try{
                HttpServer server = HttpServer.create(new InetSocketAddress("localhost",9000), 0);
                server.createContext("/hello", new TestHandler());

                server.start();
            }
            catch(Exception e){

            }



        }

    static class TestHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String msg = "hello world";
            byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "text/html; UTF -8");
            exchange.sendResponseHeaders(
                    HttpURLConnection.HTTP_OK, msg.getBytes("UTF-8").length);
            OutputStream output = exchange.getResponseBody();
            output.write(msg.getBytes("UTF-8"));
            output.flush(); exchange.close();

        }
    }

}

