package fom.wolniakhajri.wa.controllers;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.animations.builder.DynamicAnimationBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.AnimationsBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.github.appreciated.apexcharts.config.xaxis.builder.LabelsBuilder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import fom.wolniakhajri.wa.models.ChartTypesModel;
import fom.wolniakhajri.wa.models.Company;
import fom.wolniakhajri.wa.models.CompanyList;
import fom.wolniakhajri.wa.models.DataSeriesModel;
import fom.wolniakhajri.wa.views.dax.DAXView;
import fom.wolniakhajri.wa.views.singlestocks.SingleStocksView;

import java.io.IOException;
import java.util.List;

public class Controller {

    public static ComboBox<ChartTypesModel> getComboBoxChartTypes(String range, HorizontalLayout view) {
        ComboBox<ChartTypesModel> cb = new ComboBox<>();
        List<ChartTypesModel> chartTypelist = ChartTypesModel.createChartTypeList();
        cb.setItemLabelGenerator(ChartTypesModel::getStringValue);
        cb.setItems(chartTypelist);
        cb.setValue(chartTypelist.get(0));
        if(view instanceof DAXView) {
            cb.addValueChangeListener(event -> {
                try {
                    ((DAXView) view).buildChartView(range, ((DAXView) view).getChartTypesComboBox().getValue(), "%5EGDAXI");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            cb.addValueChangeListener(event -> {
                try {
                    ((SingleStocksView) view).buildChartView(range, ((SingleStocksView) view).getChartTypesComboBox().getValue(), ((SingleStocksView) view).getComboBoxCompanies().getValue().getSymbol());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return cb;
    }

    public static ComboBox<Company> getComboBoxCompany(String range, HorizontalLayout view) {
        ComboBox<Company> cb = new ComboBox<>();
        List<Company> companyList = CompanyList.createCompanyList();

        cb.setItemLabelGenerator(Company::getName);
        cb.setItems(companyList);
        cb.setValue(companyList.get(13));       //set Value to Deutsche Telekom AG

        cb.addValueChangeListener(event -> {
            try {
                ((SingleStocksView) view).buildChartView(range, ((SingleStocksView)view).getChartTypesComboBox().getValue(), cb.getValue().getSymbol());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return cb;
    }

    public static Button createButton(String range, HorizontalLayout view) {
        Button b = new Button(range);
        if(view instanceof DAXView) {
            b.addClickListener(buttonClickEvent -> {
                try {
                    ((DAXView) view).buildChartView(range, ((DAXView) view).getChartTypesComboBox().getValue(), "%5EGDAXI");
                    ((DAXView) view).disableButton(b);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            b.addClickListener(buttonClickEvent -> {
                try {
                    ((SingleStocksView) view).buildChartView(range, ((SingleStocksView) view).getChartTypesComboBox().getValue(), ((SingleStocksView) view).getComboBoxCompanies().getValue().getSymbol());
                    ((SingleStocksView) view).disableButton(b);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return b;
    }

    public static ApexCharts buildChart(String range, ChartTypesModel types, String symbol, HorizontalLayout view) throws IOException {
        ApexCharts chart;
        if(view instanceof DAXView) {
            chart = ApexChartsBuilder.get()
                    .withChart(ChartBuilder.get()
                            .withType(getChartType(types.name()))
                            .withZoom(ZoomBuilder.get().withEnabled(true).build())
                            .withHeight("500")
                            .withAnimations(AnimationsBuilder.get().withEnabled(true).withDynamicAnimation(DynamicAnimationBuilder.get().withEnabled(true).withSpeed(350).build()).build())
                            .build())
                    .withDataLabels(DataLabelsBuilder.get()
                            .withEnabled(false)
                            .build())
                    .withTitle(TitleSubtitleBuilder.get()
                            .withText("DAX (^GDAXI)")
                            .withAlign(Align.left)
                            .build())
                    .withSeries(DataSeriesModel.getStockDataset(symbol, range, getInterval(range)))
                    .withXaxis(XAxisBuilder.get()
                            .withType(XAxisType.categories)
                            .withTooltip(TooltipBuilder.get()
                                    .withEnabled(true)
                                    .build())
                            .withLabels(LabelsBuilder.get()
                                    .withShow(false)
                                    .build())
                            .build())
                    .withYaxis(YAxisBuilder.get()
                            .withTooltip(TooltipBuilder.get()
                                    .withEnabled(true)
                                    .build())
                            .build())
                    .build();
        } else {
            chart = ApexChartsBuilder.get()
                    .withChart(ChartBuilder.get()
                            .withType(getChartType(types.name()))
                            .withZoom(ZoomBuilder.get().withEnabled(true).build())
                            .withHeight("500")
                            .withAnimations(AnimationsBuilder.get().withEnabled(true).withDynamicAnimation(DynamicAnimationBuilder.get().withEnabled(true).withSpeed(350).build()).build())
                            .build())
                    .withDataLabels(DataLabelsBuilder.get()
                            .withEnabled(false)
                            .build())
                    .withTitle(TitleSubtitleBuilder.get()
                            .withText(((SingleStocksView) view).getComboBoxCompanies().getValue().getName() + " (" + symbol + ")")
                            .withAlign(Align.left)
                            .build())
                    .withSeries(DataSeriesModel.getStockDataset(symbol, range, getInterval(range)))
                    .withXaxis(XAxisBuilder.get()
                            .withType(XAxisType.categories)
                            .withTooltip(TooltipBuilder.get()
                                    .withEnabled(true)
                                    .build())
                            .withLabels(LabelsBuilder.get()
                                    .withShow(false)
                                    .build())
                            .build())
                    .withYaxis(YAxisBuilder.get()
                            .withTooltip(TooltipBuilder.get()
                                    .withEnabled(true)
                                    .build())
                            .build())
                    .build();
        }
        return chart;
    }

    private static Type getChartType(String type){
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

    private static String getInterval(String text) {
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
