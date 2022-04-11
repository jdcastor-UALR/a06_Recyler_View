package com.ualr.recyclerviewassignment.Utils;

import android.content.Context;

import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.model.Email;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    private static final Random r = new Random();

    public static int randInt(int max) {
        int min = 0;
        return r.nextInt((max - min) + 1) + min;
    }

    public static List<Email> getInboxData(Context ctx) {
        List<Email> items = new ArrayList<>();
        String[] names;
        names = ctx.getResources().getStringArray(R.array.people_names);
        String[] dates;
        dates = ctx.getResources().getStringArray(R.array.general_date);

        Arrays.stream(names).forEach(s -> {
            Email obj = new Email();
            obj.setFrom(s);
            obj.setEmail(Tools.getEmailFromName(obj.getFrom()));
            obj.setMessage(ctx.getResources().getString(R.string.lorem_ipsum));
            obj.setDate(dates[randInt(dates.length - 1)]);
            items.add(obj);
        });
        Collections.shuffle(items);
        return items;
    }

    public static Email getRandomInboxItem(Context ctx) {
        String[] names = ctx.getResources().getStringArray(R.array.people_names);
        String[] dates = ctx.getResources().getStringArray(R.array.general_date);
        int indexName = randInt(names.length - 1);
        int indexDate = randInt(dates.length - 1);
        Email obj = new Email();
        obj.setFrom(names[indexName]);
        obj.setEmail(Tools.getEmailFromName(obj.getFrom()));
        obj.setMessage(ctx.getResources().getString(R.string.lorem_ipsum));
        obj.setDate(dates[indexDate]);
        return obj;
    }
}
