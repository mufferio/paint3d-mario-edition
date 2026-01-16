package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class StrategyDecider {

    private NormalShapeHandler n_handler;
    private PolylineHandler p_handler;
    private SelectionModeHandler s_handler;

    private PaintModel p_model;
    private ShapeChooserModel s_model;

    public StrategyDecider(PaintModel p_model, ShapeChooserModel s_model) {
        this.p_model = p_model;
        this.s_model = s_model;

        this.n_handler = new NormalShapeHandler(this.p_model, this.s_model);
        this.p_handler = new PolylineHandler(this.p_model, this.s_model);
        this.s_handler = new SelectionModeHandler(this.p_model, this.s_model);
    }

    public void decide(MouseEvent mouseEvent) {
        MouseEventHandler strategy = this.n_handler;
        if (this.s_model.isSelectMode()) {
            strategy = this.s_handler;
        } else if (this.modeIsPolyline()) {
            strategy = this.p_handler;
        }

        strategy.handleMouse(mouseEvent);
    }

    private boolean modeIsPolyline() {
        return "Polyline".equals(this.s_model.getMode());
    }
}
