package fom.wolniakhajri.wa.views.dax;

import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import fom.wolniakhajri.wa.controllers.Controller;
import fom.wolniakhajri.wa.models.ChartTypesModel;
import fom.wolniakhajri.wa.views.main.MainView;

import java.io.IOException;

@Route(value = "dax", layout = MainView.class)
@PageTitle("DAX")
@CssImport("./styles/views/dax/dax-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class DAXView extends HorizontalLayout {

    private ApexCharts daxChart;

    private final ComboBox<ChartTypesModel> chartTypesComboBox;

    private boolean chartInitialized = false;

    private Button clickedButton;

    public DAXView() throws IOException {
        setId("dax-view");



        //Buttons
        Button buttonMax = Controller.createButton("max","3mo", this);
        Button button10years = Controller.createButton("10y","1mo", this);
        Button button5years = Controller.createButton("5y","1wk", this);
        Button button2years = Controller.createButton("2y","5d", this);
        Button button1year = Controller.createButton("1y","1d", this);
        Button button6months = Controller.createButton("6mo","1d", this);
        Button button3months = Controller.createButton("3mo","1h", this);
        Button button1month = Controller.createButton("1mo","15m", this);
        Button button1week = Controller.createButton("7d","5m", this);
        Button button1day = Controller.createButton("1d","1m", this);

        disableButton(button1week);

        chartTypesComboBox = Controller.getComboBoxChartTypes(clickedButton.getText(), getInterval(clickedButton.getText()), this);

        add(buttonMax, button10years, button5years, button2years, button1year, button6months, button3months, button1month, button1week, button1day);
        add(chartTypesComboBox);
        buildChartView("7d", "5m", chartTypesComboBox.getValue(), "%5EGDAXI");

    }

    public void buildChartView(String range, String interval, ChartTypesModel types, String symbol) throws IOException {
        if (chartInitialized) {
            remove(daxChart);
        } else {
            chartInitialized = true;
        }
        daxChart = Controller.buildChart(range, interval, types, symbol, this);
        add(daxChart);
    }

    public void disableButton(Button toDisable) {
        if (clickedButton != null && clickedButton != toDisable) {
            clickedButton.setEnabled(true);
        }
        toDisable.setEnabled(false);
        clickedButton = toDisable;
    }

    private String getInterval(String text) {
        switch (text) {
            case "max":
                return "3mo";
            case "10y":
                return "1mo";
            case "5y":
                return "1wk";
            case "2y":
                return "5d";
            case "1y":
            case "6mo":
                return "1d";
            case "3mo":
                return "1h";
            case "1mo":
                return "15m";
            case "7d":
                return "5m";
            case "1d":
                return "1m";
        }
        return null;
    }

    public ComboBox<ChartTypesModel> getChartTypesComboBox() {
        return chartTypesComboBox;
    }

}
