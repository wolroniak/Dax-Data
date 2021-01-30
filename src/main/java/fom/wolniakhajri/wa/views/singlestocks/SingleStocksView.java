package fom.wolniakhajri.wa.views.singlestocks;

import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fom.wolniakhajri.wa.controllers.Controller;
import fom.wolniakhajri.wa.models.ChartTypesModel;
import fom.wolniakhajri.wa.models.Company;
import fom.wolniakhajri.wa.views.main.MainView;

import java.io.IOException;

@Route(value = "singlestocks", layout = MainView.class)
@PageTitle("Single Stocks")
@CssImport("./styles/views/singlestocks/singlestocks.css")
public class SingleStocksView extends HorizontalLayout {

    private ApexCharts daxChart;

    private final ComboBox<ChartTypesModel> chartTypesComboBox;

    private final ComboBox<Company> comboBoxCompanies;

    private boolean chartInitialized = false;

    private Button clickedButton;

    public SingleStocksView() throws IOException {
        setId("singlestocks");

        //Buttons
        Button buttonMax = Controller.createButton("max",this);
        Button button10years = Controller.createButton("10y",this);
        Button button5years = Controller.createButton("5y",this);
        Button button2years = Controller.createButton("2y",this);
        Button button1year = Controller.createButton("1y",this);
        Button button6months = Controller.createButton("6mo",this);
        Button button3months = Controller.createButton("3mo",this);
        Button button1month = Controller.createButton("1mo",this);
        Button button1week = Controller.createButton("7d",this);
        Button button1day = Controller.createButton("1d",this);

        disableButton(button1week);

        chartTypesComboBox = Controller.getComboBoxChartTypes(clickedButton.getText(), this);

        comboBoxCompanies = Controller.getComboBoxCompany("7d", this);

        add(comboBoxCompanies);
        add(buttonMax, button10years, button5years, button2years, button1year, button6months, button3months, button1month, button1week, button1day);
        add(chartTypesComboBox);

        buildChartView("7d", chartTypesComboBox.getValue(), comboBoxCompanies.getValue().getSymbol());

    }

    public void buildChartView(String range, ChartTypesModel types, String symbol) throws IOException {
        if (chartInitialized) {
            remove(daxChart);
        } else {
            chartInitialized = true;
        }
        daxChart = Controller.buildChart(range, types, symbol, this);
        add(daxChart);
    }

    public void disableButton(Button toDisable) {
        if (clickedButton != null && clickedButton != toDisable) {
            clickedButton.setEnabled(true);
        }
        toDisable.setEnabled(false);
        clickedButton = toDisable;
    }

    public ComboBox<ChartTypesModel> getChartTypesComboBox() {
        return chartTypesComboBox;
    }

    public ComboBox<Company> getComboBoxCompanies() {
        return comboBoxCompanies;
    }

}
