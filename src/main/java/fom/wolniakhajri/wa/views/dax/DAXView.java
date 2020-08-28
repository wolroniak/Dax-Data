package fom.wolniakhajri.wa.views.dax;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import fom.wolniakhajri.wa.views.main.MainView;

@Route(value = "dax", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("DAX")
@CssImport("styles/views/dax/d-ax-view.css")
public class DAXView extends Div {

    public DAXView() {
        setId("d-ax-view");
        add(new Label("Content placeholder"));
    }

}
