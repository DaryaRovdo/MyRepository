import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.TreeMap;
import java.util.Map;

public class MessageExchange {

    private JSONParser jsonParser = new JSONParser();

    public String getToken(int index) {
        Integer number = index * 8 + 11;
        return "TN" + number + "EN";
    }

    public int getIndex(String token) {
        return (Integer.valueOf(token.substring(2, token.length() - 2)) - 11) / 8;
    }

    public JSONArray mapToJSONArray(TreeMap <Integer, Message> messages)
    {
        JSONArray array = new JSONArray();
        for(Map.Entry entry: messages.entrySet()) {
            JSONObject jo = new JSONObject();
            jo.put("id", ((Message) entry.getValue()).getId());
            jo.put("user", ((Message) entry.getValue()).getUser());
            jo.put("text", ((Message) entry.getValue()).getText());
            array.add(jo);
        }
        return array;
    }

    public String getServerResponse(TreeMap <Integer, Message> messages) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messages", mapToJSONArray(messages));
        jsonObject.put("token", getToken(messages.size()));
        return jsonObject.toJSONString();
    }

    public String getClientSendMessageRequest(String message, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("message", message);
        return jsonObject.toJSONString();
    }

    public Message getClientMessage(InputStream inputStream) throws ParseException {
        JSONObject o = getJSONObject(inputStreamToString(inputStream));
        Message m = new Message();
        if (o.containsKey("id"))
            m.setId(Integer.parseInt(o.get("id").toString()));
        if (o.containsKey("name"))
            m.setUser((String) o.get("name"));
        if (o.containsKey("message"))
            m.setText((String) o.get("message"));
        return m;
    }

    public JSONObject getJSONObject(String json) throws ParseException {
        return (JSONObject) jsonParser.parse(json.trim());
    }

    public String inputStreamToString(InputStream in) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int length = 0;
        try {
            while ((length = in.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(baos.toByteArray());
    }
}
