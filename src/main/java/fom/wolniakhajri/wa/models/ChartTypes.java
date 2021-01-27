package fom.wolniakhajri.wa.models;

import java.util.ArrayList;

public enum ChartTypes {
    AREA,
    CANDLESTICK,
    LINE;

    public static ArrayList<ChartTypes> createChartTypeList(){
        ArrayList<ChartTypes> list = new ArrayList<>();
        list.add(AREA);
        list.add(CANDLESTICK);
        list.add(LINE);
        return list;
    }

    public static String getStringValue(ChartTypes type) {
        String tmp = "";
        switch (type) {
            case AREA:
                tmp = "Area";
                break;
            case CANDLESTICK:
                tmp = "Candlestick";
                break;
            case LINE:
                tmp = "Line";
                break;
            default:
                break;
        }
    return tmp;
    }

}
