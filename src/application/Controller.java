package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import code.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

/**
 * Controller class for the GUI
 * @author Adan Moran-Macdonald
 * @since 01-April-2016
 */
public class Controller {

	/**
	 * Required variables for interfacing with GUI
	 */
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField windText;

    @FXML
    private Slider windSlider;

    @FXML
    private Slider angleSlider;

    @FXML
    private TextField angleText;

    @FXML
    private Button stopButton;

    @FXML
    private Button startButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button resetButton;

    @FXML
    private Canvas mainCanvas;

    @FXML
    private Canvas launcherCanvas;
    
    private ArrayList<TextField> texts = new ArrayList<>();
    private ArrayList<Slider> sliders = new ArrayList<>();
    private GraphicsContext canvas;
    private GraphicsContext launcher;
    private double startTime;
    private double angle;
    private double windSpeed;
    private ParticleManager manager;
    private Timeline timeline = null;
    
    /**
     * A change in the slider position will result in changing the angle of the launcher,
     * as well as the text.
     */
    @FXML
    void angleSliderEvent() {
    	int value = (int) angleSlider.getValue();
    	angleText.setText(Integer.toString(value));
    	angle = value;
    	drawLauncher(value);
    }

    /**
     * Changing the textbox will change the angle slider and the launcher position.
     * @param event An event describing the enter key being pressed
     */
    @FXML
    void angleTextEvent(ActionEvent event) {
    	// Get the current text
    	String text = angleText.getText();
    	// Filter out anything that is not related to numbers
		text = text.replaceAll("[^-\\d.]", "");
		angleText.setText(text);
		double val;
		try
		{
			//Get a number from the  textbox and set the angle value.
			val = Double.parseDouble(text);
			if(val < -15)
				val = -15;
			else if(val > 15)
				val = 15;
			angleSlider.setValue(val);
			angleText.setText(Double.toString(val));
			drawLauncher(val);
			angle = val;
		}
		catch(NumberFormatException e)
		{}
    }
    
    /**
     * A change in the slider position will result in changing the windSpeed,
     * as well as the text.
     */
    @FXML
    void windSliderEvent() {
    	
    	int value = (int) windSlider.getValue();
    	windText.setText(Integer.toString(value));
    	windSpeed = value;
    }
    
    /**
     * Changing the textbox will change the angle slider and the launcher position.
     * @param event An event describing the enter key being pressed
     */
    @FXML
    void windTextEvent(ActionEvent event) {
    	//Get the current text
    	String text = windText.getText();
    	//Filter out anything that is not related to numbers
		text = text.replaceAll("[^-\\d.]", "");
		windText.setText(text);
		double val;
		try
		{
			//Get a number from the textbox and set the windSpeed value
			val = Double.parseDouble(text);
			if(val < -20)
				val = -20;
			else if(val > 20)
				val = 20;
			windSlider.setValue(val);
			windText.setText(Double.toString(val));
			windSpeed = val;
		}
		catch(NumberFormatException e)
		{}
    }

    /**
     * Close the window
     * @param event The Exit button was pressed
     */
    @FXML
    void exitEvent(ActionEvent event) {
    	Platform.exit();
    }

    /**
     * Displays the help window
     * @param event The Help button was pressed
     */
    @FXML
    void helpEvent(ActionEvent event) {
    	Alert help = new Alert(AlertType.INFORMATION);
    	help.setTitle("Fireworks Help");
    	help.setHeaderText(null);
    	help.setContentText("This app models a firework in flight. " + 
    			"The firework's flight path during simulation is affected by:\n\n" +
    	 "a) Launcher angle [deg]: You can snap to an integer angle with the middle slider," +
    	 " or you can directly input any real-valued angle to the middle text box (the change will register once you press ENTER)." + 
    	 "\nAngles allowed are between -15 and +15 degrees.\n\n" + 
    	 "b) Wind Speed [km/h]: You can snap to an integer speed with the left-most slider," +
    	 " or you can directly input any real-valued speed to the middle text box (the change will register once you press ENTER). " +
    	 "\nSpeeds allowed are between -20 and +20 km/h.\n\n" +
    	 "You can change these values at any time during simulation!\n" + 
    	 "(Copyright Adan Moran-MacDonald, 2016)");
    	
    	help.showAndWait();
    }

    /**
     * Creates a new particle manager and starts simulation of the firework launch.
     * @param event The Start button was pressed
     * @throws EnvironmentException The attained somehow produced an illegal value
     * @throws EmitterException The attained angle somehow produced an illegal value
     */
    @FXML
    void startEvent(ActionEvent event) throws EnvironmentException, EmitterException {
    	// Hide the start button and show the stop button
    	startButton.setVisible(false);
    	stopButton.setVisible(true);
    	//Create a new particle manager
		manager = new ParticleManager(windSpeed, angle);
		startTime = System.currentTimeMillis() / 1000.0;
		manager.start(0);

		//Create a frame that runs the drawFireworks() to display the updated launch value.
		KeyFrame frame = new KeyFrame(Duration.ZERO, actionEvent ->
				{
					try
					{
						drawFireworks();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				});
		//Create a timeline and run it.
		timeline = new Timeline( frame,
				new KeyFrame(Duration.millis(1000 / 60)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.playFromStart();
    }

    /**
     * Stops simulation and clears the screen.
     * @param event The Stop button was pressed.
     */
    @FXML
    void stopEvent(ActionEvent event) {
    	// Hide the stop button and show the start button
    	startButton.setVisible(true);
    	stopButton.setVisible(false);
    	// Stop simulation
    	timeline.stop();
    	//Clear the screen
    	clearCanvasAndDraw();
    }
    
    /**
     * Resets the application
     * @param event The Reset button was pressed.
     */
    @FXML
    void resetEvent(ActionEvent event) {
    	// Clear the text fields
    	for(TextField f : texts)
    		f.clear();
    	// Reset the sliders
    	for(Slider s : sliders)
    		s.setValue(0);
    	// Reset other values
    	startTime = 0;
    	angle = 0;
    	windSpeed = 0;
    	// Stop the animation, if it is running
    	if(stopButton.isVisible())
    		stopEvent(event);
    	// Reset the canvas screen
    	clearCanvasAndDraw();
    }
    
    /**
     * Set up all parameters for use in FXML. Instantiates listeners and draws to Canvases.
     */
    @FXML
    void initialize() {
    	// Ensure all values were injected properly.
        assert windText != null : "fx:id=\"windText\" was not injected: check your FXML file 'app.fxml'.";
        assert windSlider != null : "fx:id=\"windSlider\" was not injected: check your FXML file 'app.fxml'.";
        assert angleSlider != null : "fx:id=\"angleSlider\" was not injected: check your FXML file 'app.fxml'.";
        assert angleText != null : "fx:id=\"angleText\" was not injected: check your FXML file 'app.fxml'.";
        assert stopButton != null : "fx:id=\"stopButton\" was not injected: check your FXML file 'app.fxml'.";
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'app.fxml'.";
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'app.fxml'.";
        assert helpButton != null : "fx:id=\"helpButton\" was not injected: check your FXML file 'app.fxml'.";
        assert resetButton != null : "fx:id=\"resetButton\" was not injected: check your FXML file 'app.fxml'.";
        assert mainCanvas != null : "fx:id=\"mainCanvas\" was not injected: check your FXML file 'app.fxml'.";
        assert launcherCanvas != null : "fx:id=\"launcherCanvas\" was not injected: check your FXML file 'app.fxml'.";

        //Set the most essential firework parameters
        angle = 0;
        windSpeed = 0;
        startTime = 0;
        
        // Add parameters to array lists to clear in Reset
        texts.add(windText);
        texts.add(angleText);
        sliders.add(windSlider);
        sliders.add(angleSlider);
        //Instantiate Canvases
    	launcher = launcherCanvas.getGraphicsContext2D();
    	canvas = mainCanvas.getGraphicsContext2D();
    	//Clear the canvas and draw the launcher
    	clearCanvasAndDraw();
        
    	// Add listeners for the sliders
        windSlider.valueProperty().addListener((observable, oldValue, newValue) -> windSliderEvent());
        angleSlider.valueProperty().addListener((observable, oldValue, newValue) -> angleSliderEvent());
    }
    
    /**
     * Draw the launcher at the specified angle
     * @param angle An angle between -20 and 20 degrees
     */
    void drawLauncher(double angle)
    {
    	launcherCanvas.rotateProperty().set(angle);
    	launcher.setFill(Color.GREY);
        launcher.fillRect(0,0,launcherCanvas.getWidth(),launcherCanvas.getHeight());
    }
    
    /**
     * Draws a rectangle at the bottom of the canvas as "grass"
     */
    void drawGrass()
    {
    	Paint color = canvas.getFill();
    	canvas.setFill(Color.GREEN);
    	canvas.fillRect(0, mainCanvas.getHeight()-30, mainCanvas.getWidth(), 30);
    	canvas.setFill(color);
    }
    
    /**
     * Fill the canvas with blue as a "sky"
     */
    void drawSky()
    {
    	Paint color = canvas.getFill();
    	canvas.setFill(Color.DEEPSKYBLUE);
    	canvas.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
    	canvas.setFill(color);
    }
    
    /**
     * Erase the canvas, redraw the launcher, sky, and grass
     */
    void clearCanvasAndDraw()
    {
    	launcher.clearRect(0, 0, launcherCanvas.getWidth(), launcherCanvas.getHeight());
    	drawLauncher(angle);
    	canvas.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
    	drawSky();
    	drawGrass();
    }
    
    /**
     * Iterate through the manager (which should be instantiated) and draw the fireworks in the correct positions.
     * Note that streaks don't appear often because they are drawn while the particle is still behind the launcher.
     * @throws EmitterException
     * @throws EnvironmentException
     */
    void drawFireworks() throws EmitterException, EnvironmentException
    {
    	// Reset the angle and velocity so the launcher is redrawn correctly
    	manager.setLaunchAngle(angle);
    	manager.setWindVelocity(windSpeed);
    	// Wipe the canvases and redraw the launcher, grass, and sky
    	clearCanvasAndDraw();
    	//Re-Draw the launcher
    	launcher.setStroke(Color.GREY);
    	launcher.setLineWidth(launcherCanvas.getWidth());
    	launcher.strokeLine(launcherCanvas.getWidth()/2, 0, 
    			launcherCanvas.getWidth()/2, launcherCanvas.getHeight());
    	
    	// Get the particles at the current time.
    	ArrayList<Particle> particles = manager.getFireworks((System.currentTimeMillis()/1000.0 - startTime));

    	double[] pos;
    	for(Particle particle : particles)
    	{
    		
    		pos = particle.getPosition();
    		//Recalculate the positions of particles as pixels. We multiply by 16 because it draws nicely on 640x480
    		double x = mainCanvas.getWidth()/2 + pos[0]*16;
    		double y = mainCanvas.getHeight() - pos[1]*16;
    		// If it's a burning particle, draw it larger
    		if(particle instanceof BurningParticle)
    		{
    			canvas.setFill(particle.getColour());
    			canvas.fillOval(x,y,6,6);
    		}
    		// If it's a streak, draw a line to the origin
    		// Streaks don't
    		else if(particle instanceof Streak)
    		{
    			canvas.setStroke(particle.getColour());
    			canvas.strokeLine(x, y, mainCanvas.getWidth()/2, mainCanvas.getHeight());
    		}
    		// Draw anything else as sparks
    		else
    		{
    			canvas.setFill(particle.getColour());
    			canvas.fillOval(x,y,2,2);
    		}
    		// Draw the grass over the particles near the ground so they aren't seen protruding through the launcher
    		drawGrass();
    	}
    }
}