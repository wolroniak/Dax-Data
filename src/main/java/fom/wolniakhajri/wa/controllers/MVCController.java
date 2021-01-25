package fom.wolniakhajri.wa.controllers;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.animations.builder.DynamicAnimationBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.AnimationsBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import fom.wolniakhajri.wa.models.ChartTypes;
import fom.wolniakhajri.wa.views.MVCView;
import fom.wolniakhajri.wa.views.dax.DAXView;
import fom.wolniakhajri.wa.views.singlestocks.SingleStocksView;
import com.vaadin.flow.component.button.Button;

import java.io.IOException;

public class MVCController {
    private MVCView singleStocksView, daxView;
    private static final MVCController OBJ = new MVCController();

    public static MVCController getInstance(){
        return OBJ;
    }

    private MVCController(){
  //      this.singleStocksView = new SingleStocksView(this);
//        this.daxView = new DAXView(this);

    }

    public Button createButton(String range, String interval, MVCView view){
        Button b = new Button(range);
        b.addClickListener(buttonClickEvent -> {
            try {
                buildChart(range,interval, (ChartTypes) view.chartTypeBox.getValue(),view);
                view.disableButton(b);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });

        return b;
    }

    public void buildChart(String range, String interval, ChartTypes types, MVCView view) throws IOException{
        if (view.chartInitialized) {
            view.remove(view.barChart);
        } else {
            view.chartInitialized = true;
        }
        ApexCharts toReturn;
        if(view instanceof SingleStocksView) {
            this.singleStocksView = view;
            toReturn = ApexChartsBuilder.get()
                    .withChart(ChartBuilder.get()
                            .withType(view.getChartType(types.name()))
                            .withZoom(ZoomBuilder.get().withEnabled(true).build())
                            .withHeight("490")
                            .withAnimations(AnimationsBuilder.get().withEnabled(true).withDynamicAnimation(DynamicAnimationBuilder.get().withEnabled(true).withSpeed(350).build()).build())
                            .build())
                    .withDataLabels(DataLabelsBuilder.get()
                            .withEnabled(false)
                            .build())
                    .withTitle(TitleSubtitleBuilder.get()
                            .withText(((SingleStocksView) view).comboBoxCompanies.getValue().getName() + " (" + ((SingleStocksView) view).comboBoxCompanies.getValue().getSymbol() + ")")
                            .withAlign(Align.left)
                            .build())
                    .withSeries(DataSeriesController.getStockDataset(((SingleStocksView) view).comboBoxCompanies.getValue().getSymbol(), range, interval))
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
        }
        else
        {
            this.daxView = view;
            toReturn = ApexChartsBuilder.get()
                    .withChart(ChartBuilder.get()
                            .withType(view.getChartType(types.name()))
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
        }

        view.add(toReturn);
        view.setWidth("100%");
        view.setHeight("100%");
    }
}
