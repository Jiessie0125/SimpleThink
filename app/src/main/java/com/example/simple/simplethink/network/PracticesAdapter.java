package com.example.simple.simplethink.network;

import com.example.simple.simplethink.model.PracticesResponse;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mobileteam on 2019/8/20.
 */

public class PracticesAdapter /*implements JsonDeserializationContext */{

   /* @Override
    public PracticesResponse deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
        Gson gson = new Gson();
        List<PracticesResponse.Practice> practices = new ArrayList<PracticesResponse.Practice>();
        Set<Map.Entry<String,JsonElement>> entries = json.getAsJsonObject().entrySet();
        for(Map.Entry<String,JsonElement> entry: entries){
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            value.getAsJsonObject().addProperty("dailyPractice", key);
            practices.add(gson.fromJson(value, PracticesResponse.Practice.class));
        }
        return new PracticesResponse(practices);
    }*/
}
