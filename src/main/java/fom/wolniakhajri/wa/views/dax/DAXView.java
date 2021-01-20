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
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import fom.wolniakhajri.wa.controllers.DataSeriesController;
import fom.wolniakhajri.wa.models.Company;
import fom.wolniakhajri.wa.views.main.MainView;

import java.io.IOException;

@Route(value = "dax", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("DAX")
@CssImport("styles/views/dax/d-ax-view.css")
public class DAXView extends Div {

    private ApexCharts barChart;
    private ApexCharts daxChart;
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

    public DAXView() {
        setId("d-ax-view");
        this.daxChart = new ApexCharts();
        //Initialize Button: MAX
        buttonMax = new Button("max");
        buttonMax.addClickListener(clickEvent -> {
            try {
                this.buildChart( "max", "3mo");
                disableButton(buttonMax);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 10 Years
        button10years = new Button("10y");
        button10years.addClickListener(clickEvent -> {
            try {
                this.buildChart("10y", "1mo");
                disableButton(button10years);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 5 Years
        button5years = new Button("5y");
        button5years.addClickListener(clickEvent -> {
            try {
                this.buildChart( "5y", "1wk");
                disableButton(button5years);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 2 Years
        button2years = new Button("2y");
        button2years.addClickListener(clickEvent -> {
            try {
                this.buildChart("2y", "5d");
                disableButton(button2years);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 1 Year
        button1year = new Button("1y");
        button1year.addClickListener(clickEvent -> {
            try {
                this.buildChart("1y", "1d");
                disableButton(button1year);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 6 Months
        button6months = new Button("6mo");
        button6months.addClickListener(clickEvent -> {
            try {
                this.buildChart("6mo", "1d");
                disableButton(button6months);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 3 Months
        button3months = new Button("3mo");
        button3months.addClickListener(clickEvent -> {
            try {
                this.buildChart("3mo", "1h");
                disableButton(button3months);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 1 Months
        button1month = new Button("1mo");
        button1month.addClickListener(clickEvent -> {
            try {
                this.buildChart("1mo", "15m");
                disableButton(button1month);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 1 Week (7d)
        button1week = new Button("7d");
        button1week.addClickListener(clickEvent -> {
            try {
                this.buildChart("7d", "5m");
                disableButton(button1week);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Initialize Button: 1 Day (Intraday)
        button1day = new Button("1d");
        button1day.addClickListener(clickEvent -> {
            try {
                this.buildChart("1d", "1m");
                disableButton(button1day);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


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
            buildChart("7d", "5m");
            disableButton(button1week);
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(daxChart);
    }

    private void buildChart(String range, String interval) throws IOException {
        if (chartInitialized) {
            remove(barChart);
        } else {
            chartInitialized = true;
        }

        barChart = ApexChartsBuilder.get()
                .withChart(ChartBuilder.get()
                        .withType(Type.area)
                        .withAnimations(AnimationsBuilder.get().withEnabled(true).withDynamicAnimation(DynamicAnimationBuilder.get().withEnabled(true).withSpeed(350).build()).build())
                        .withHeight("490")
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
                .withTitle(TitleSubtitleBuilder.get()
                        .withText("Dax")
                        .withAlign(Align.left)
                        .build())
                .withSeries(DataSeriesController.getStockDataset("%5EGDAXI", range, interval))
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

    private void disableButton(Button toDisable) {
        if (clickedButton != null && clickedButton != toDisable) {
            clickedButton.setEnabled(true);
        }
        toDisable.setEnabled(false);
        clickedButton = toDisable;
    }

}
