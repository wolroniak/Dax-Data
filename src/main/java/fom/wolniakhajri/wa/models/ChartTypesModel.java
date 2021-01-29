package fom.wolniakhajri.wa.models;

import java.util.ArrayList;

public enum ChartTypesModel {
    AREA,
    CANDLESTICK,
    LINE;

    public static ArrayList<ChartTypesModel> createChartTypeList(){
        ArrayList<ChartTypesModel> list = new ArrayList<>();
        list.add(AREA);
        list.add(CANDLESTICK);
        list.add(LINE);
        return list;
    }

    public static String getStringValue(ChartTypesModel type) {
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
