package com.mimi.server;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AndroidPushPeriodicNotifications {

    public static String PeriodicNotificationJson() throws JSONException {
        LocalDate localDate = LocalDate.now();

        String sampleData[] = {"eNjmbFicQhqjCe7CJL62MN:APA91bHO3Zq71K3x4NRMuLaYtihfTmhzZySC8vm4QOrrTZvxoZvIaMqxBCt2-z5l4jqoK9meWBdHkfP4n8jR7n5cnmborWoJUG7MFUliENcvniei5PIgtWtDLMxNs9JMCY595e38v4ax"};

        JSONObject body = new JSONObject();

        List<String> tokenlist = new ArrayList<String>();

        for(int i=0; i<sampleData.length; i++){
            tokenlist.add(sampleData[i]);
        }

        JSONArray array = new JSONArray();

        for(int i=0; i<tokenlist.size(); i++) {
            array.put(tokenlist.get(i));
        }

        body.put("registration_ids", array);

        JSONObject notification = new JSONObject();
        notification.put("title","hello!");
        notification.put("body","Today is "+localDate.getDayOfWeek().name()+"!");

        body.put("notification", notification);
        System.out.println("success!!");
        System.out.println(body.toString());

        return body.toString();
    }
}