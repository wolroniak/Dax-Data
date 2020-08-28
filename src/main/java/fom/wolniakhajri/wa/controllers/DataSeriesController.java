package fom.wolniakhajri.wa.controllers;

import com.github.appreciated.apexcharts.helper.Coordinate;
import com.github.appreciated.apexcharts.helper.Series;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

// "https://query1.finance.yahoo.com/v7/finance/chart/DTE.DE?range=1d&interval=1m&indicators=quote&includeTimestamps=true"

@SuppressWarnings("rawtypes")
public class DataSeriesController {


    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    public static Series<Coordinate> getStockData(String symbol, String range, String interval) throws IOException {

        JSONObject json = readJsonFromUrl("https://query1.finance.yahoo.com/v7/finance/chart/" + symbol + "?range=" + range + "&interval=" + interval + "&indicators=quote&includeTimestamps=true");

        System.out.println(json.toString());
        JSONObject output = (JSONObject) json.get("chart");
        JSONArray arrayResult = output.getJSONArray("result");
        JSONObject output0 = arrayResult.getJSONObject(0);

        //Array: Timestamp
        JSONArray arrayTimestamp = output0.getJSONArray("timestamp");
        int[] timestamps = new int[arrayTimestamp.length()];

        //Weiter bei Indicators
        JSONObject outputIndicators = output0.getJSONObject("indicators");
        JSONArray arrayQuote = outputIndicators.getJSONArray("quote");
        JSONObject output0_2 = arrayQuote.getJSONObject(0);

        //Array: Volume
        //JSONArray arrayVolume = output0_2.getJSONArray("volume");
        //int[] volumes = new int[arrayVolume.length()];
        //Array: High
        JSONArray arrayHigh = output0_2.getJSONArray("high");
        double[] highs = new double[arrayHigh.length()];
        //Array: Low
        JSONArray arrayLow = output0_2.getJSONArray("low");
        double[] lows = new double[arrayLow.length()];
        //Array: Close
        JSONArray arrayClose = output0_2.getJSONArray("close");
        double[] closes = new double[arrayClose.length()];
        //Array: Open
        JSONArray arrayOpen = output0_2.getJSONArray("open");
        double[] opens = new double[arrayOpen.length()];

        Series<Coordinate> dataset = new Series<>();
        Coordinate[] data = new Coordinate[arrayTimestamp.length()];

        //Befüllen der AusgabeArrays und Ausgabe
        for (int i = 0; i < timestamps.length; i++) {
            timestamps[i] = arrayTimestamp.getInt(i);
            /*if (arrayVolume.get(i) instanceof Integer) {
                volumes[i] = arrayVolume.getInt(i);
            } else {
                volumes[i] = volumes[i - 1];
            }*/
            if (arrayHigh.get(i) instanceof Double) {
                highs[i] = arrayHigh.getDouble(i);
            } else {
                highs[i] = highs[i - 1];
            }
            if (arrayLow.get(i) instanceof Double) {
                lows[i] = arrayLow.getDouble(i);
            } else {
                lows[i] = lows[i - 1];
            }
            if (arrayClose.get(i) instanceof Double) {
                closes[i] = arrayClose.getDouble(i);
            } else {
                closes[i] = closes[i - 1];
            }
            if (arrayOpen.get(i) instanceof Double) {
                opens[i] = arrayOpen.getDouble(i);
            } else {
                opens[i] = opens[i - 1];
            }

            BigDecimal bd = BigDecimal.valueOf(opens[i]).setScale(4, RoundingMode.HALF_UP);
            double open = bd.doubleValue();
            bd = BigDecimal.valueOf(highs[i]).setScale(4, RoundingMode.HALF_UP);
            double high = bd.doubleValue();
            bd = BigDecimal.valueOf(lows[i]).setScale(4, RoundingMode.HALF_UP);
            double low = bd.doubleValue();
            bd = BigDecimal.valueOf(closes[i]).setScale(4, RoundingMode.HALF_UP);
            double close = bd.doubleValue();
            //noinspection unchecked
            data[i] = new Coordinate(getDateString(timestamps[i], interval, range), open, high, low, close);

        }

        dataset.setData(data);
        return dataset;
    }

    private static String getDateString(long unix_seconds, String interval, String range) {
        Date date = new Date(unix_seconds*1000L);

        SimpleDateFormat jdf;
        if (interval.equals("1mo") || interval.equals("3mo")) {
            jdf = new SimpleDateFormat("yyyy-MM");
        } else if (interval.equals("1d") || interval.equals("5d") || interval.equals("1wk")) {
            jdf = new SimpleDateFormat("yyyy-MM-dd");
        } else if(range.equals("1d")) {
            jdf = new SimpleDateFormat("HH:mm");
        } else {
            jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }

        jdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));

        return jdf.format(date);
    }

}
