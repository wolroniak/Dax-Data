package fom.wolniakhajri.wa.views;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import fom.wolniakhajri.wa.controllers.MVCController;

public class MVCView extends Div {

    private MVCController controller;
    protected Button clickedButton;
    private Button buttonMax;
    private Button button10years;
    private Button button5years;
    private Button button2years;
    private Button button1year;
    private Button button6months;
    private Button button3months;
    private Button button1month;
    private Button button1week;
    private Button button1day;

    public ComboBox chartTypeBox;
    public ApexCharts barChart;
    public boolean chartInitialized = false;

    public MVCView(MVCController ctrl){
        this.controller = ctrl;
    }

    public Type getChartType(String type){
        Type tmp = Type.area;
        switch (type){
            case "AREA":
                tmp = Type.area;
                break;
            case "CANDLESTICK":
                tmp = Type.candlestick;
                break;
            case "LINE":
                tmp = Type.line;
                break;
            default:
                break;
        }
        return tmp;
    }

    public void disableButton(Button toDisable) {
        if (clickedButton != null && clickedButton != toDisable) {
            clickedButton.setEnabled(true);
        }
        toDisable.setEnabled(false);
        clickedButton = toDisable;
    }
}
