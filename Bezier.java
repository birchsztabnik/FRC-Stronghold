package org.usfirst.frc.team2875.robot.util;

//Use the BezierPointCalculator project instead
@Deprecated
public class Bezier {
	private double a,b,c,d;
	private double a2,b2;
	
	/**
	 * ax^3+bx^2+cx+d
	 * @param a a term
	 * @param b b term
	 * @param c c term
	 * @param d d term
	 */
	public Bezier(double a, double b, double c, double d){
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		a2 = 3*a;
		b2 = 2*b;
	}
	public double find_y(double x){
		return (a*(x*x*x))+(b*(x*x))+(c*x)+d;
	}
	public double find_dy(double x){
		return (a2*(x*x))+(b2*(x))+(c);
	}
	public double find_angle_with_y(double x){
		return 90-((180*(Math.atan(find_dy(x)/x)))/Math.PI);
	}
}