package fom.wolniakhajri.wa.views.dax;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.animations.builder.DynamicAnimationBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.AnimationsBuilder;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import fom.wolniakhajri.wa.controllers.DataSeriesController;
import fom.wolniakhajri.wa.controllers.MVCController;
import fom.wolniakhajri.wa.models.ChartTypes;
import fom.wolniakhajri.wa.models.Company;
import fom.wolniakhajri.wa.views.MVCView;
import fom.wolniakhajri.wa.views.main.MainView;

import java.io.IOException;
import java.util.List;

@Route(value = "dax", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("DAX")
@CssImport("styles/views/dax/d-ax-view.css")
public class DAXView extends MVCView {

    private ApexCharts barChart;
    private ApexCharts daxChart;
    private final ComboBox<ChartTypes> chartTypesComboBox;

    private boolean chartInitialized = false;

    public DAXView(MVCController controller) {
        super(controller);
        setId("d-ax-view");
        this.daxChart = new ApexCharts();
        //Initialize ComboBox
        this.chartTypesComboBox = new ComboBox<>();
        List<ChartTypes> chartTypelist = ChartTypes.createChartTypeList();
        chartTypesComboBox.setItemLabelGenerator(ChartTypes::getStringValue);
        chartTypesComboBox.setItems(chartTypelist);
        chartTypesComboBox.setValue(chartTypelist.get(0));

        chartTypesComboBox.addValueChangeListener(event->{
            try {
                controller.buildChart(clickedButton.getText(),getInterval(clickedButton.getText()), chartTypesComboBox.getValue(),this);
            } catch(IOException e){
                e.printStackTrace();
            }
        });

        //Initialize Button: MAX
        Button buttonMax = controller.createButton("max","3mo",this);

        Button button10years = controller.createButton("10y","1mo",this);

        Button button5years = controller.createButton("5y","1wk",this);

        Button button2years = controller.createButton("2y","5d",this);

        Button button1year = controller.createButton("1y","1d",this);

        Button button6months = controller.createButton("6mo","1d",this);

        Button button3months = controller.createButton("3mo", "1h", this);

        Button button1month = controller.createButton("1mo","15m",this);

        Button button1week = controller.createButton("7d", "5m",this);

        Button button1day = controller.createButton("1d", "1m",this);




        //Buttons
        add(buttonMax);
        add(button10years);
        add(button5years);
        add(button2years);
        add(button1year);
        add(button6months);
        add(button3months);
        add(button1month);
        add(button1week);
        add(button1day);

        add(chartTypesComboBox);
        try {
            controller.buildChart("7d", "5m", chartTypesComboBox.getValue(),this);
            disableButton(button1week);
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(daxChart);
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


}
