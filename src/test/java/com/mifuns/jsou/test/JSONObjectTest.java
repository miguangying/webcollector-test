package com.mifuns.jsou.test;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.junit.Test;

/**
 * Created by miguangying on 2017/1/10.
 */
public class JSONObjectTest {


    @Test
    public void test() {
        System.out.println(prepareJSONObject());
        //System.out.println(prepareJSONObject2());
    }


    private static String prepareJSONObject(){
        JSONObject studentJSONObject = new JSONObject();
        try {
            studentJSONObject.put("name", "Jason");
            studentJSONObject.put("id", 20130001);
            studentJSONObject.put("phone", "13579246810");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return studentJSONObject.toString();
    }

    private static String prepareJSONObject2(){
        JSONStringer jsonStringer = new JSONStringer();
        try {
            jsonStringer.object();
            jsonStringer.key("name");
            jsonStringer.value("Jason");
            jsonStringer.key("id");
            jsonStringer.value(20130001);
            jsonStringer.key("phone");
            jsonStringer.value("13579246810");
            jsonStringer.endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonStringer.toString();
    }

}
