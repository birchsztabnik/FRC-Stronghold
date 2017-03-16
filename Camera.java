package org.usfirst.frc.team2875.robot.subsystems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

import org.usfirst.frc.team2875.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;
/**
 * 
 * @author Birch, Logan 2016
 * Enables USB camera, Axis camera(maybe not) and finds goals
 * */
public class Camera extends Subsystem{
	private static CameraServer server;
	private static USBCamera camera;
	private static USBCamera camera2;
	private static int current_session;
	private static int shooting_session;
	private static int view_session;
	
	private static Image goal_frame;
	private static boolean binary;
	public static Thread goalFinder;
	private static double currentRectLeft;
	private static double theta;
	private static boolean view;
	private static int MAX_FPS=15;
	private static int QUALITY=10;
	private final long sleep_time=100;
	private ArrayList<USBCamera> cams;
	private int currCam;
	private Image frame;
	private USBCamera cam;
	
	//ADJUST BRIGHTNESS
	public Camera() {
		//the camera name (ex "cam0") can be found through the roborio web interface
		binary = true;
		view = false;
		currentRectLeft=0;
		theta = 0.0D;
		//goal_frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		shooting_session = 1;
		view_session=2;
		current_session = shooting_session;
		
		frame=NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		cams=new ArrayList<USBCamera>();
		currCam=0;
		//CameraServer.getInstance().setQuality(QUALITY);
		addCamera("cam0");
		addCamera("cam1");
		
		cam=cams.get(currCam);
		cam.setBrightness(1);
		cam.setSize(640, 480);
		cam.openCamera();
		cam.startCapture();
		
		//NIVision.IMAQdxConfigureGrab(current_session);
		//NIVision.IMAQdxStartAcquisition(current_session);
		
		/*
		camera = new USBCamera("cam0");
		camera2= new USBCamera("cam1");
		camera.setBrightness(1);
		camera.setSize(640, 480);
		
		camera.setFPS(MAX_FPS);
		camera2.setFPS(MAX_FPS);
		*/
		//camera.setExposureManual(-5);
		//camera.setExposureHoldCurrent();
		//camera.openCamera();
		//camera.startCapture();
		
		//camera2=new USBCamera("cam1");
		
		
		goalFinder=new Thread(new GoalFinder());
		goalFinder.start();
		start();
	}
	
	public void addCamera(String camName)
	{
		USBCamera temp = new USBCamera(camName);
		temp.setFPS(MAX_FPS);
		cams.add(temp);
		temp=null;
	}
	
	/**
	 * Toggle the binary image on SmartDashboard
	 */
	public void toggle_binary(){
		//if(current_session!=view_session)
			binary = !binary;
		
	}
	
	public boolean isShootingView()
	{
		if(currCam==0)
			return true;
		else
			return false;
	}
	
	public void enable_view_camera(){
		//view_session = NIVision.IMAQdxOpenCamera("cam1", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		//camera2.openCamera();
		view_session=2;
		//camera2.openCamera();
	}
	@Deprecated
	public void disable_view_camera(){
		NIVision.IMAQdxCloseCamera(view_session);
	}
	
	public void toggle_view(){
		if(current_session==shooting_session && binary)
			binary=!binary;
		
		try{
			if(current_session==shooting_session){
				current_session=view_session;
				cam.stopCapture();
				cam.closeCamera();
				currCam++;
				currCam%=cams.size();
				cam=cams.get(currCam);
				cam.openCamera();
				cam.startCapture();
				
			}
			else{
				current_session=shooting_session;
				cam.stopCapture();
				cam.closeCamera();
				currCam++;
				currCam%=cams.size();
				cam=cams.get(currCam);
				cam.setSize(640, 480);
				cam.setBrightness(1);
				cam.openCamera();
				cam.startCapture();
				
			}
			/*
			cam.stopCapture();
			cam.closeCamera();
			currCam++;
			currCam%=cams.size();
			cam=cams.get(currCam);
			cam.openCamera();
			cam.startCapture();*/
			Thread.sleep(sleep_time);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		/*
		try{
		if(current_session == shooting_session){
     		 
			 //NIVision.IMAQdxStopAcquisition(shooting_session);
			System.out.println("Switching to View Session");
     		 current_session = view_session;
     		 camera.stopCapture();
     		 camera.closeCamera();
     		 camera2.openCamera();
     		 camera2.startCapture();
     		 view=false;
	          //NIVision.IMAQdxConfigureGrab(current_session);
	} else if(current_session == view_session){
    		  //NIVision.IMAQdxStopAcquisition(current_session);
    		  current_session = shooting_session;
    		  System.out.println("Switching to Shooting Session");
     		  //NIVision.IMAQdxConfigureGrab(current_session);
    		  camera2.stopCapture();
    		  camera2.closeCamera();
    		  camera.setBrightness(1);
    		  camera.setSize(640, 480);
    		  camera.openCamera();
    		  camera.startCapture();
    		  view=true;
      }
		Thread.sleep(sleep_time);
		}catch(Exception e){
			e.printStackTrace();
		}*/
		
		

	}
	
	public void set_theta(double theta){
		Camera.theta = theta;
	}
	
	public double get_theta(){
		return Camera.theta;
	}
	
	public void setRectLeft(double rectLeft)
	{
		currentRectLeft=rectLeft;
	}

	public double getRectLeft()
	{
		return currentRectLeft;
	}
	
	public void startThread()
	{
		goalFinder=new Thread(new GoalFinder());
		goalFinder.start();
	}

	public static void start() {
	}
	
	private void CamLoop()
	{
		
		cams.get(currCam).getImage(frame);
		CameraServer.getInstance().setImage(frame);
		
	}

	public static void execute() {
		//NIVision.IMAQdxGrab(current_session, goal_frame, 0);
		//Robot.webcamera.camera.getImage(goal_frame);
		/*Image temp= NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		if(current_session==shooting_session)
			Robot.webcamera.camera.getImage(temp);
		
		if(!binary&&current_session==shooting_session){
			System.out.println("switch");
			Robot.webcamera.camera.getImage(temp);
			Camera.server.getInstance().setImage(temp);
		}
		if(view){
			System.out.println("Showing View Camera");
			Image temp2 = null;
			Robot.webcamera.camera2.getImage(temp2);
			Camera.server.getInstance().setImage(temp2);
			temp2=null;
		}
		temp=null;*/
		if(!binary)
			Robot.webcamera.CamLoop();
		
		if(Robot.isEnabled)
		{
			if(!goalFinder.isAlive())
			{
				Robot.webcamera.startThread();
			}
		}
	}

	/*
	private static void grab_usb_session(){
		NIVision.IMAQdxGrab(session, usb_camera_frame, 0);
		NIVision.Rect rect = new NIVision.Rect(10,10,100,100);
		if (!SmartDashboard.getBoolean("Show Masking"))
			Camera.server.getInstance().setImage(usb_camera_frame);
		if(Robot.isEnabled)
		{
			if(!goalFinder.isAlive())
			{
				Robot.webcamera.startThread();
			}
		}
	}

	private static void grab_usb_session2(){
		NIVision.Range goal_HUE_RANGE = new NIVision.Range(110,125); // 162-168
		NIVision.Range goal_SAT_RANGE = new NIVision.Range(10,255); // 55-62
		NIVision.Range goal_VAL_RANGE = new NIVision.Range(220,255);

		NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
		NIVision.IMAQdxGrab(session, usb_camera_frame, 1);
		Image binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		NIVision.imaqColorThreshold(binaryFrame, usb_camera_frame, 255,
				(NIVision.ColorMode.HSL), goal_HUE_RANGE, goal_SAT_RANGE,
				goal_VAL_RANGE);

		NIVision.imaqMask(usb_camera_frame, usb_camera_frame, binaryFrame);
		//NIVision.imaqConvexHull(usb_camera_frame,usb_camera_frame, 0);
		CameraServer.getInstance().setImage(usb_camera_frame);

		/** robot code here! **/
		//Timer.delay(0.005);		// wait for a motor update time

		//NIVision.IMAQdxStopAcquisition(session);}/*
	/*
	public Image getCurrentImage()
	{
		return usb_camera_frame;
	}*/

	public static void end() {
		//NIVision.IMAQdxCloseCamera(shooting_session);
		camera.stopCapture();
		camera.closeCamera();
	}

	@Override
	protected void initDefaultCommand() {
	}

	public class GoalFinder implements Runnable {
		public static final String RECT_RIGHT = "RectRight";
		public static final String RECT_LEFT = "RectLeft";
		public static final String DISTANCE = "Distance";
		private static final double PX_PER_DEGREE = 10.66666666666666666666;
		// A structure to hold measurements of a particle
		public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport> {
			double PercentAreaToImageArea;
			double Area;
			double ConvexHullArea;
			double BoundingRectLeft;
			double BoundingRectTop;
			double BoundingRectRight;
			double BoundingRectBottom;

			public int compareTo(ParticleReport r) {
				return (int) (r.Area - this.Area);
			}

			public int compare(ParticleReport r1, ParticleReport r2) {
				return (int) (r1.Area - r2.Area);
			}


		};


		// Structure to represent the scores for the various tests used for target
		// identification
		public class Scores {
			double Trapezoid;
			double LongAspect;
			double ShortAspect;
			double AreaToConvexHullArea;

		};

		// Images
		Image frame;
		Image binaryFrame;
		int imaqError;
		/* OLD
		NIVision.Range GOAL_HUE_RANGE = new NIVision.Range(110,125); // 110-125
		NIVision.Range GOAL_SAT_RANGE = new NIVision.Range(80,150); // 80-150
		NIVision.Range GOAL_VAL_RANGE = new NIVision.Range(200,225);// 200-225

		// values 110,125
		// 35, 225
		// 180, 255
		*/
		
		NIVision.Range GOAL_HUE_RANGE = new NIVision.Range(97, 122);	//97/122Default hue range for yellow tote
		NIVision.Range GOAL_SAT_RANGE = new NIVision.Range(230, 255);	//Default saturation range for yellow tote
		NIVision.Range GOAL_VAL_RANGE = new NIVision.Range(180, 255);	//200/255Default value range for yellow tote
		double AREA_MINIMUM = .1; // Default Area minimum for particle as a .1
		// percentage of total image area
		double LONG_RATIO = 10;
		double SHORT_RATIO = 0.1;
		// 1.4
		double SCORE_MIN = 10.0; // Minimum score to be considered a goal
		double VIEW_ANGLE = 60.0D; // View angle for camera, set to Axis m1011 by
		// default, 64 for m1013, 51.7 for 206, 52 for
		// HD3000 square, 60 for HD3000 640x480
		NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
		NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(
				0, 0, 1, 1);
		Scores scores = new Scores();

		public GoalFinder()
		{
			frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
			binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);


			criteria[0] = new NIVision.ParticleFilterCriteria2(
					NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM,
					100.0, 0, 0);


			//SmartDashboard.putBoolean("Show Masking", false);
		}

		public void initGoalFinder() {
			// create images
			frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
			binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);


			criteria[0] = new NIVision.ParticleFilterCriteria2(
					NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM,
					100.0, 0, 0);


			//SmartDashboard.putBoolean("Show Masking", false);
			/*
			SmartDashboard.putNumber(
					"goal hue min", GOAL_HUE_RANGE.minValue);
			SmartDashboard.putNumber(
					"goal hue max", GOAL_HUE_RANGE.maxValue);
			SmartDashboard.putNumber(
					"goal sat min", GOAL_SAT_RANGE.minValue);
			 SmartDashboard.putNumber(
					"goal sat max", GOAL_SAT_RANGE.maxValue);
			 SmartDashboard.putNumber(
					"goal val min", GOAL_VAL_RANGE.minValue);
			SmartDashboard.putNumber(
					"goal val max", GOAL_VAL_RANGE.maxValue);
			 */
		}

		private boolean is_goal;

		public boolean is_it_a_goal()
		{
			return is_goal;
		}
		
		public void findGoal() {
			
			//frame=frame2;
			Image temp= NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
			//NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
			//NIVision.IMAQdxGrab(shooting_session, frame, 1);
			Robot.webcamera.cam.getImage(frame);
			//Image binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
			NIVision.imaqColorThreshold(binaryFrame, frame, 255,
					(NIVision.ColorMode.HSV), GOAL_HUE_RANGE, GOAL_SAT_RANGE,
					GOAL_VAL_RANGE);

			NIVision.imaqMask(frame, frame, binaryFrame);
						int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
			
			if(binary){
				Camera.server.getInstance().setImage(frame);
			}/*
			else{
				Robot.webcamera.camera.getImage(temp);
				Camera.server.getInstance().setImage(temp);
			}*/
			
			
			float areaMin = (float) SmartDashboard.getNumber("Area min %",
					AREA_MINIMUM);
			criteria[0].lower = areaMin;
			imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame,
					criteria, filterOptions, null);
			numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
			
			if (numParticles > 0) {
				// Measure particles and sort by particle size
				Vector<ParticleReport> particles = new Vector<ParticleReport>();
				for (int particleIndex = 0; particleIndex < numParticles; particleIndex++) {
					ParticleReport par = new ParticleReport();
					par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(
							binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
					par.Area = NIVision.imaqMeasureParticle(binaryFrame,
							particleIndex, 0, NIVision.MeasurementType.MT_AREA);
					par.ConvexHullArea = NIVision.imaqMeasureParticle(binaryFrame,
							particleIndex, 0,
							NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
					par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryFrame,
							particleIndex, 0,
							NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
					par.BoundingRectLeft = NIVision.imaqMeasureParticle(
							binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
					par.BoundingRectBottom = NIVision.imaqMeasureParticle(
							binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
					par.BoundingRectRight = NIVision.imaqMeasureParticle(
							binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
					particles.add(par);
				}
				particles.sort(null);
				scores.Trapezoid = TrapezoidScore(particles.elementAt(0));
				scores.LongAspect = LongSideScore(particles.elementAt(0));
				scores.ShortAspect = ShortSideScore(particles.elementAt(0));
				scores.AreaToConvexHullArea = ConvexHullAreaScore(particles
						.elementAt(0));
				is_goal = scores.Trapezoid > SCORE_MIN
				&& (scores.LongAspect > SCORE_MIN || scores.ShortAspect > SCORE_MIN)
				&& scores.AreaToConvexHullArea > SCORE_MIN;
				boolean isLong = scores.LongAspect > scores.ShortAspect;

				SmartDashboard.putBoolean("IsGoal", is_goal);
				SmartDashboard.putNumber(DISTANCE,computeDistance(binaryFrame,particles.elementAt(0), isLong));
			} else {
				SmartDashboard.putBoolean("IsGoal", false);
			}
			frame=null;
			
		}

		// Comparator function for sorting particles. Returns true if particle 1 is
		// larger
		boolean CompareParticleSizes(ParticleReport particle1,
				ParticleReport particle2) {
			// we want descending sort order
			return particle1.PercentAreaToImageArea > particle2.PercentAreaToImageArea;
		}

		/**
		 * Converts a ratio with ideal value of 1 to a score. The resulting function
		 * is piecewise linear going from (0,0) to (1,100) to (2,0) and is 0 for all
		 * inputs outside the range 0-2
		 */
		double ratioToScore(double ratio) {
			return (Math.max(0, Math.min(100 * (1 - Math.abs(1 - ratio)), 100)));
		}

		/**
		 * Method to score convex hull area. This scores how "complete" the particle
		 * is. Particles with large holes will score worse than a filled in shape
		 */
		double ConvexHullAreaScore(ParticleReport report) {
			return ratioToScore((report.Area / report.ConvexHullArea) * 1.18);
		}


		private double rect_left;
		private double rect_right;
		public double get_rect_left(){
			return rect_left;
		}
		/**
		 * Finds the angle between the camera and the center of the goal
		 * @return Angle robot needs to rotate to center
		 */
		public double find_angle_to_goal(){
			double center = ((rect_right-rect_left)/2)+rect_left;
			return ((320.0D-center)/PX_PER_DEGREE);
		}
		/**
		 * Method to score if the particle appears to be a trapezoid. Compares the
		 * convex hull (filled in) area to the area of the bounding box. The
		 * expectation is that the convex hull area is about 95.4% of the bounding
		 * box area for an ideal goal.
		 */		
		double TrapezoidScore(ParticleReport report) {
			//SmartDashboard.putNumber(RECT_RIGHT,report.BoundingRectRight);
			//SmartDashboard.putNumber(RECT_LEFT,report.BoundingRectLeft);
			
			
			rect_left=report.BoundingRectLeft;
			rect_right=report.BoundingRectRight;
			Robot.webcamera.setRectLeft(rect_left);
			
			return ratioToScore(report.ConvexHullArea
					/ ((report.BoundingRectRight - report.BoundingRectLeft)
							* (report.BoundingRectBottom - report.BoundingRectTop) * .954));
		}

		/**
		 * Method to score if the aspect ratio of the particle appears to match the
		 * long side of a goal.
		 */
		double LongSideScore(ParticleReport report) {
			return ratioToScore(((report.BoundingRectRight - report.BoundingRectLeft) / (report.BoundingRectBottom - report.BoundingRectTop))
					/ LONG_RATIO);
		}

		/**
		 * Method to score if the aspect ratio of the particle appears to match the
		 * short side of a goal.
		 */
		double ShortSideScore(ParticleReport report) {
			return ratioToScore(((report.BoundingRectRight - report.BoundingRectLeft) / (report.BoundingRectBottom - report.BoundingRectTop))
					/ SHORT_RATIO);
		}

		/**
		 * Computes the estimated distance to a target using the width of the
		 * particle in the image. For more information and graphics showing the math
		 * behind this approach see the Vision Processing section of the
		 * ScreenStepsLive documentation.
		 *
		 * @param image
		 *            The image to use for measuring the particle estimated
		 *            rectangle
		 * @param report
		 *            The Particle Analysis Report for the particle
		 * @param isLong
		 *            Boolean indicating if the target is believed to be the long
		 *            side of a goal
		 * @return The estimated distance to the target in feet.
		 */
		private double distance;
		public double get_distance(){
			return distance;
		}
		double computeDistance(Image image, ParticleReport report, boolean isLong) {
			double normalizedWidth, targetWidth;
			NIVision.GetImageSizeResult size;

			size = NIVision.imaqGetImageSize(image);
			
			//SmartDashboard.putNumber("Right-Left", report.BoundingRectRight-report.BoundingRectLeft);
			
			normalizedWidth = 1.75
					* (report.BoundingRectRight - report.BoundingRectLeft)
					/ size.width;
			targetWidth = isLong ? 26.0 : 16.9;
			
			//This is hypotenuse
			double h = targetWidth
					/ (normalizedWidth * 12 * Math.tan(VIEW_ANGLE * Math.PI
							/ (180.0D * 2.0D)));
			double inchVal=h*12;
			distance = (Math.sqrt((inchVal*inchVal)-(7225)))/12;
			return distance;
		}

		@Override
		public void run() {
			while(Robot.isEnabled){
				Image temp= NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
				
				//Camera.server.setQuality(50);
				//NIVision.IMAQdxGrab(Camera.shooting_session, temp, 0);
				//Robot.webcamera.camera.getImage(temp);
				//CameraServer.getInstance().setImage(temp);
				if(Robot.webcamera.isShootingView()){
				findGoal();
				set_theta(find_angle_to_goal());
				SmartDashboard.putNumber("Angle to goal", Camera.theta);}
				
			}
		}
	}	
}