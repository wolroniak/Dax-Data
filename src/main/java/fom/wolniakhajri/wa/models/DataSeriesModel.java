package fom.wolniakhajri.wa.models;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class DataSeriesModel {

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

    public static Series<Coordinate> getStockDataset(String symbol, String range, String interval) throws IOException {

        ArrayList<double[]> stockData = getStockData(symbol, range, interval);
        Series<Coordinate> dataset = new Series<>();
        Coordinate[] data = new Coordinate[stockData.get(0).length];

        double[] timestamp = stockData.get(0);
        double[] open = stockData.get(1);
        double[] high = stockData.get(2);
        double[] low = stockData.get(3);
        double[] close = stockData.get(4);

        for (int i = 0; i < timestamp.length; i++) {
            data[i] = new Coordinate(getDateString((long) timestamp[i], interval, range), open[i], high[i], low[i], close[i]);
        }

        dataset.setData(data);
        dataset.setName("EUR");
        return dataset;

    }

    public static ArrayList<double[]> getStockData(String symbol, String range, String interval) throws IOException {

        JSONObject json = readJsonFromUrl("https://query1.finance.yahoo.com/v7/finance/chart/" + symbol + "?range=" + range + "&interval=" + interval + "&indicators=quote&includeTimestamps=true");

        System.out.println(json.toString());
        JSONObject output = (JSONObject) json.get("chart");
        JSONArray arrayResult = output.getJSONArray("result");
        JSONObject output0 = arrayResult.getJSONObject(0);

        //Array: Timestamp
        JSONArray arrayTimestamp = output0.getJSONArray("timestamp");

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

        ArrayList<double[]> data = new ArrayList<>();
        double[] timestampOutput = new double[opens.length];
        double[] openOutput = new double[opens.length];
        double[] highOutput = new double[highs.length];
        double[] lowOutput = new double[lows.length];
        double[] closeOutput = new double[closes.length];

        //Befüllen der AusgabeArrays und Ausgabe
        for (int i = 0; i < timestampOutput.length; i++) {
            timestampOutput[i] = arrayTimestamp.getInt(i);
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

            //Coordinate[] data = new Coordinate[arrayTimestamp.length()];

            BigDecimal bd = BigDecimal.valueOf(opens[i]).setScale(4, RoundingMode.HALF_UP);
            openOutput[i] = bd.doubleValue();
            bd = BigDecimal.valueOf(highs[i]).setScale(4, RoundingMode.HALF_UP);
            highOutput[i] = bd.doubleValue();
            bd = BigDecimal.valueOf(lows[i]).setScale(4, RoundingMode.HALF_UP);
            lowOutput[i] = bd.doubleValue();
            bd = BigDecimal.valueOf(closes[i]).setScale(4, RoundingMode.HALF_UP);
            closeOutput[i] = bd.doubleValue();

        }

        data.add(timestampOutput);
        data.add(openOutput);
        data.add(highOutput);
        data.add(lowOutput);
        data.add(closeOutput);

        return data;

    }

    public static String getDateString(long unix_seconds, String interval, String range) {
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
