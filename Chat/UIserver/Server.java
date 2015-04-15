import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Server implements HttpHandler {

    private TreeMap<Integer, Message> history = new TreeMap <Integer, Message>();//for cool id
    private ArrayList<Message> toEdit = new ArrayList <Message>();
    private MessageExchange messageExchange = new MessageExchange();
    private int id = 0;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    private static PrintWriter log;

    public static void main(String[] args) {
        if (args.length != 1)
            System.out.println("Usage: java Server port");
        else {
            try {
                System.out.println("Server is starting...");
                Integer port = Integer.parseInt(args[0]);
                HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
                System.out.println("Server started.");
                String serverHost = InetAddress.getLocalHost().getHostAddress();
                server.createContext("/chat", new Server());
                server.setExecutor(null);
                server.start();
                log  = new PrintWriter(new FileWriter("log.txt"));
            } catch (IOException e) {
                System.out.println("Error: " + e);
            }
        }
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";

        if ("GET".equals(httpExchange.getRequestMethod())) {
            response = doGet(httpExchange);
        } else if ("POST".equals(httpExchange.getRequestMethod())) {
            doPost(httpExchange);
        } else if ("DELETE".equals(httpExchange.getRequestMethod())) {
            doDelete(httpExchange);
        } else if ("PUT".equals(httpExchange.getRequestMethod())) {
            doPut(httpExchange);
        } else if ("OPTIONS".equals(httpExchange.getRequestMethod())) {
            response = "";
        } else {
            response = "Unsupported http method: " + httpExchange.getRequestMethod();
        }

        sendResponse(httpExchange, response);
    }

    private String doGet(HttpExchange httpExchange) {
        String query = httpExchange.getRequestURI().getQuery();
        if (query != null) {
            Map<String, String> map = queryToMap(query);
            String token = map.get("token");
            if (token != null && !"".equals(token)) {
                int index;
                if (token.substring(0, 2).compareTo("TN") == 0) {
                    index = messageExchange.getIndex(token);
                    return messageExchange.getServerResponse(history, index);
                }
                if (token.substring(0, 2).compareTo("TK") == 0) {
                    index = messageExchange.getIndex(token);
                    return messageExchange.getServerResponseToEdit(toEdit, index);
                }
            } else {
                Date time = new Date();
                System.out.println(timeFormat.format(time) + " Can't send history: token query parameter is absent in url");
                log.println(timeFormat.format(time) + " Can't send history: token query parameter is absent in url");
                log.flush();
                return "Token query parameter is absent in url: " + query;
            }
        }
        Date time = new Date();
        System.out.println(timeFormat.format(time) + " Can't send history: absent query in url");
        log.println(timeFormat.format(time) + " Can't send history: absent query in url");
        log.flush();
        return  "Absent query in url";
    }

    private void doPost(HttpExchange httpExchange) {
        try {
            Message message = messageExchange.getClientMessage(httpExchange.getRequestBody());
            message.setDeleted(false);
            message.setId(id);
            Date time = new Date();
            System.out.println(timeFormat.format(time) + " Get message from " + message.getUser() + " : " + message.getText());
            log.println(timeFormat.format(time) + " Get message from " + message.getUser() + " : " + message.getText());
            log.flush();
            history.put(id, message);
            id++;
        } catch (ParseException e) {
            Date time = new Date();
            System.err.println(timeFormat.format(time) + " Invalid user request: " + httpExchange.getRequestBody() + " " + e.getMessage());
            log.println(timeFormat.format(time) + " Invalid user request: " + httpExchange.getRequestBody() + " " + e.getMessage());
            log.flush();
        }
    }

    private void doDelete(HttpExchange httpExchange) {
        try {
            Message message = messageExchange.getClientMessage(httpExchange.getRequestBody());
            if (history.containsKey(message.getId())) {
                Message newMessage = history.get(message.getId());
                newMessage.setText(newMessage.getText() + " [DELETED]");
                newMessage.setDeleted(true);
                history.put(newMessage.getId(), newMessage);
                toEdit.add(newMessage);
                Date time = new Date();
                System.out.println(timeFormat.format(time) + " Delete message with id " + message.getId());
                log.println(timeFormat.format(time) + " Delete message with id " + message.getId());
                log.flush();
            } else {
                Date time = new Date();
                System.out.println(timeFormat.format(time) + " Can't delete message: No message with such id in history!");
                log.println(timeFormat.format(time) + " Can't delete message: No message with such id in history!");
                log.flush();
            }
        } catch (ParseException e) {
            Date time = new Date();
            System.err.println(timeFormat.format(time) + " Invalid user request: " + httpExchange.getRequestBody() + " " + e.getMessage());
            log.println(timeFormat.format(time) + " Invalid user request: " + httpExchange.getRequestBody() + " " + e.getMessage());
            log.flush();
        }
    }

    private void doPut(HttpExchange httpExchange) {
        try {
            Message message = messageExchange.getClientMessage(httpExchange.getRequestBody());
            if (history.containsKey(message.getId()) && !history.get(message.getId()).isDeleted() ) {
                Message newMessage = history.get(message.getId());
                newMessage.setText(message.getText());
                history.put(newMessage.getId(), newMessage);
                toEdit.add(newMessage);
                Date time = new Date();
                System.out.println(timeFormat.format(time) + " Edit message with id " + newMessage.getId() + " : " + newMessage.getText());
                log.println(timeFormat.format(time) + " Edit message with id " + newMessage.getId() + " : " + newMessage.getText());
                log.flush();
            } else {
                Date time = new Date();
                System.out.println(timeFormat.format(time) + " Can't edit message: No message with such id in history!");
                log.println(timeFormat.format(time) + " Can't edit message: No message with such id in history!");
                log.flush();
            }
        } catch (ParseException e) {
            Date time = new Date();
            System.err.println(timeFormat.format(time) + " Invalid user request: " + httpExchange.getRequestBody() + " " + e.getMessage());
            log.println(timeFormat.format(time) + " Invalid user request: " + httpExchange.getRequestBody() + " " + e.getMessage());
            log.flush();
        }
    }

    private void sendResponse(HttpExchange httpExchange, String response) {
        try {
            byte[] bytes = response.getBytes();
            Headers headers = httpExchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Origin","*");
            if("OPTIONS".equals(httpExchange.getRequestMethod())) {
                headers.add("Access-Control-Allow-Methods","PUT, DELETE, POST, GET, OPTIONS");
            }
            httpExchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = httpExchange.getResponseBody();
            os.write( bytes);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }
}
