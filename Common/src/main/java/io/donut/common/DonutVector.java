package io.donut.common;

import com.raylib.Raylib;

/**
 * 2D vector as known in math, with various helper methods.
 * All angles are in degrees.
 */
public class DonutVector extends Raylib.Vector2 {

    /**
     * Creates a 2D vector.
     * @param x the x-component of the vector.
     * @param y the y-component of the vector.
     */
    public DonutVector(float x, float y){
        super.x(x);
        super.y(y);
    }

    /**
     * Creates a 2D unit vector with a given heading in degrees.
     * @param angleDegrees the heading in degrees.
     * @return the 2D unit vector with the given heading.
     */
    public static DonutVector fromAngle(float angleDegrees){
        return new DonutVector((float) Math.cos(Math.toRadians(angleDegrees)), (float) Math.sin(Math.toRadians(angleDegrees)));
    }

    /**
     * Creates a 2D vector with a given heading and magnitude.
     * @param angleDegrees the heading in degrees.
     * @param mag the mangitude.
     * @return the 2D vector with the given heading and magnitude.
     */
    public static DonutVector fromAngleWithMag(float angleDegrees, float mag){
        return new DonutVector(
                (float) Math.cos(Math.toRadians(angleDegrees))*mag,
                (float) Math.sin(Math.toRadians(angleDegrees))*mag
        );
    }

    /**
     * Add the components of the other vector.
     * @param other the other vector to add.
     */
    public void add(DonutVector other){
        super.x(super.x()+other.x());
        super.y(super.y()+other.y());
    }

    /**
     * Subtracts the components of the other vector.
     * @param other the other vector to subtract.
     */
    public void sub(DonutVector other){
        super.x(super.x()-other.x());
        super.y(super.y()-other.y());
    }

    /**
     * Multiplies the vector by a value.
     * Equivalent to {@link DonutVector#scale(float)}.
     * @param multiplicator the value multiply the vector with.
     */
    public void mult(float multiplicator){
        super.x(super.x()*multiplicator);
        super.y(super.y()*multiplicator);
    }

    /**
     * Scales the vector by the factor.
     * Equivalent to {@link DonutVector#mult(float)}.
     * @param factor the scale-factor.
     */
    public void scale(float factor){
        mult(factor);
    }

    /**
     * Divides the vector by a value.
     * @param divisor the value to divide the vector with.
     */
    public void div(float divisor){
        mult(1/divisor);
    }

    /**
     * Creates a copy of the vector.
     * @return a copy of the vector.
     */
    public DonutVector copy(){
        return new DonutVector(super.x(), super.y());
    }

    /**
     * Calculates the magnitude of the vector. AKA length.
     * @return the magnitude of the vector.
     */
    public float mag(){
        return (float)Math.sqrt(magSq());
    }

    /**
     * Calculates the length of the vector.
     * <br>
     * Equivalent to {@link DonutVector#mag()}.
     * @return the magnitude of the vector.
     */
    public float length(){
        return mag();
    }

    /**
     * Calculates the squared magnitude of the vector.
     * @return the squared magnitude of the vector.
     */
    public float magSq(){
        return super.x()*super.x()+super.y()*super.y();
    }

    /**
     * Normalized the vector, e.g. sets the magnitude to 1.
     */
    public void normalize(){
        this.div(this.mag());
    }

    /**
     * Sets the magnitude of the vector.
     * @param magnitude the new magnitude.
     */
    public void setMag(float magnitude){
        this.normalize();
        this.mult(magnitude);
    }

    /**
     * Calculates the heading of the vector in degrees.
     * @return the heading of the vector in degrees.
     */
    public float heading(){
        return (float) Math.toDegrees(Math.atan2(super.y(), super.x()));
    }

    /**
     * Sets the heading of the vector to an angle in degrees.
     * @param angleDegrees the new heading in degrees.
     */
    public void setHeading(float angleDegrees){
        float mag = mag();
        super.x((float) (mag*Math.cos(Math.toRadians(angleDegrees))));
        super.y((float) (mag*Math.sin(Math.toRadians(angleDegrees))));
    }

    /**
     * Rotates the vector by a given angle in degrees.
     * @param deltaAngleDegrees the amount to rotate in degrees.
     */
    public void rotate(float deltaAngleDegrees){
        setHeading(heading()+deltaAngleDegrees);
    }

}
