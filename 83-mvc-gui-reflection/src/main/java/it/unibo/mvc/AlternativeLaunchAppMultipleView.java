package it.unibo.mvc;

import java.lang.reflect.InvocationTargetException;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberMultipleViewControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

/**
 * Application entry-point.
 */
public final class AlternativeLaunchAppMultipleView {
    static private String packageStructure = "it.unibo.mvc.view.";
    static private String[] DRAWNUMBER_VIEWS = {"DrawNumberSwingView","DrawNumberStandardOutputView"};
    static private int NUM_OF_VIEWS_TO_CREATE = 3;
    private AlternativeLaunchAppMultipleView() { }

    /**
     * Runs the application.
     *
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main() {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberMultipleViewControllerImpl(model);
        for(var newView: DRAWNUMBER_VIEWS) {
            try {
                IO.println("TRYING TO LOAD A NEW VIEW: " + newView);
                var viewClassConstructor = Class.forName(packageStructure + newView).getDeclaredConstructor();
                for(int i = 0; i < NUM_OF_VIEWS_TO_CREATE; i++) {
                    app.addView((DrawNumberView)viewClassConstructor.newInstance());
                }
            } catch (
                ClassNotFoundException|
                NoSuchMethodException|
                InstantiationException|
                IllegalAccessException|
                IllegalArgumentException|
                InvocationTargetException e
            ) {
                IO.println("FAILED TO INSTANCE: " + packageStructure + newView);
            }
            
        }
    }
}
