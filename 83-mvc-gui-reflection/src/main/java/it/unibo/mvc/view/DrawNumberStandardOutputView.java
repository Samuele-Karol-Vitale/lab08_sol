package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

public class DrawNumberStandardOutputView implements DrawNumberView{
    private static final String STARTING_MESSAGE = "Draw Number App Started";
    private static final String NEW_GAME = ": a new game starts!";
    @Override
    public void setController(DrawNumberController observer) {
    }

    @Override
    public void start() {
        showMessage(STARTING_MESSAGE);
    }

    @Override
    public void result(DrawResult res) {
        switch (res) {
            case YOURS_HIGH, YOURS_LOW -> {
                showMessage(res.getDescription());
                return;
            }
            case YOU_WON -> showMessage(res.getDescription() + NEW_GAME);
            case YOU_LOST -> showMessage("LOST:" + res.getDescription() + NEW_GAME );
        }
    }
    private void showMessage(String msg) {
        IO.println(msg);
    }

    
}