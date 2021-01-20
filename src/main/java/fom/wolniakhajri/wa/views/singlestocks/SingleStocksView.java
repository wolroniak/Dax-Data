package fom.wolniakhajri.wa.views.singlestocks;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.animations.builder.DynamicAnimationBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.AnimationsBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fom.wolniakhajri.wa.controllers.DataSeriesController;
import fom.wolniakhajri.wa.models.CompanyList;
import fom.wolniakhajri.wa.models.Company;
import fom.wolniakhajri.wa.views.main.MainView;

import java.io.IOException;
import java.util.List;

@Route(value = "stocks", layout = MainView.class)
@PageTitle("Single Stocks")
@CssImport("styles/views/singlestocks/single-stocks-view.css")
public class SingleStocksView extends Div {

    private ApexCharts barChart;
    private final ComboBox<Company> comboBoxCompanies;
    private Button clickedButton;
    private final Button buttonMax;
    private final Button button10years;
    private final Button button5years;
    private final Button button2years;
    private final Button button1year;
    private final Button button6months;
    private final Button button3months;
    private final Button button1month;
    private final Button button1week;
    private final Button button1day;
    private boolean chartInitialized = false;


    public SingleStocksView() {
        setId("single-stocks-view");

        //Initialize ComboBox
        this.comboBoxCompanies = new ComboBox<>();
        List<Company> companyList = CompanyList.createCompanyList();
        comboBoxCompanies.setItemLabelGenerator(Company::getName);
        comboBoxCompanies.setItems(companyList);
        comboBoxCompanies.setValue(companyList.get(12));
        comboBoxCompanies.addValueChangeListener(event -> {
            //Änderung des Unternehmens
            try {
                this.buildChart(comboBoxCompanies.getValue(), clickedButton.getText(), getInterval(clickedButton.getText()));
                System.out.println("TEST= " + clickedButton.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: MAX
        buttonMax = new Button("max");
        buttonMax.addClickListener(clickEvent -> {
            try {
                this.buildChart(comboBoxCompanies.getValue(), "max", "3mo");
                disableButton(buttonMax);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 10 Years
        button10years = new Button("10y");
        button10years.addClickListener(clickEvent -> {
            try {
                this.buildChart(comboBoxCompanies.getValue(), "10y", "1mo");
                disableButton(button10years);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 5 Years
        button5years = new Button("5y");
        button5years.addClickListener(clickEvent -> {
            try {
                this.buildChart(comboBoxCompanies.getValue(), "5y", "1wk");
                disableButton(button5years);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 2 Years
        button2years = new Button("2y");
        button2years.addClickListener(clickEvent -> {
            try {
                this.buildChart(comboBoxCompanies.getValue(), "2y", "5d");
                disableButton(button2years);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 1 Year
        button1year = new Button("1y");
        button1year.addClickListener(clickEvent -> {
            try {
                this.buildChart(comboBoxCompanies.getValue(), "1y", "1d");
                disableButton(button1year);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 6 Months
        button6months = new Button("6mo");
        button6months.addClickListener(clickEvent -> {
            try {
                this.buildChart(comboBoxCompanies.getValue(), "6mo", "1d");
                disableButton(button6months);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 3 Months
        button3months = new Button("3mo");
        button3months.addClickListener(clickEvent -> {
            try {
                this.buildChart(comboBoxCompanies.getValue(), "3mo", "1h");
                disableButton(button3months);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 1 Months
        button1month = new Button("1mo");
        button1month.addClickListener(clickEvent -> {
            try {
                this.buildChart(comboBoxCompanies.getValue(), "1mo", "15m");
                disableButton(button1month);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 1 Week (7d)
        button1week = new Button("7d");
        button1week.addClickListener(clickEvent -> {
            try {
                this.buildChart(comboBoxCompanies.getValue(), "7d", "5m");
                disableButton(button1week);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 1 Day (Intraday)
        button1day = new Button("1d");
        button1day.addClickListener(clickEvent -> {
            try {
                this.buildChart(comboBoxCompanies.getValue(), "1d", "1m");
                disableButton(button1day);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //ComboBox
        add(comboBoxCompanies);

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

        try {
            buildChart(companyList.get(12), "7d", "5m");
            disableButton(button1week);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void disableButton(Button toDisable) {
        if (clickedButton != null && clickedButton != toDisable) {
            clickedButton.setEnabled(true);
        }
        toDisable.setEnabled(false);
        clickedButton = toDisable;
    }

    private void buildChart(Company company, String range, String interval) throws IOException {
        if (chartInitialized) {
            remove(barChart);
        } else {
            chartInitialized = true;
        }



        barChart = ApexChartsBuilder.get()
                .withChart(ChartBuilder.get()
                        .withType(Type.candlestick)
                        .withZoom(ZoomBuilder.get().withEnabled(true).build())
                        .withHeight("490")
                        .withAnimations(AnimationsBuilder.get().withEnabled(true).withDynamicAnimation(DynamicAnimationBuilder.get().withEnabled(true).withSpeed(350).build()).build())
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
                .withTitle(TitleSubtitleBuilder.get()
                        .withText(company.getName() + " (" + company.getSymbol() + ")")
                        .withAlign(Align.left)
                        .build())
                .withSeries(DataSeriesController.getStockDataset(company.getSymbol(), range, interval))
                .withXaxis(XAxisBuilder.get()
                        .withType(XAxisType.categories)
                        .withTooltip(TooltipBuilder.get()
                                .withEnabled(true)
                                .build())
                        .build())
                .withYaxis(YAxisBuilder.get()
                        .withTooltip(TooltipBuilder.get()
                                .withEnabled(true)
                                .build())
                        .build())
                .build();

        add(barChart);
        setWidth("100%");
        setHeight("100%");

    }

}
