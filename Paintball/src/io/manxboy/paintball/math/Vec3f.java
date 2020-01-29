/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.manxboy.paintball.math;

/**
 * Immutable Vector class
 * @author manxboy
 */
public class Vec3f {
    /**
     * X component of the vector
     */
    public final double x;
    
    /**
     * Y component of the vector
     */
    public final double y;
    
    /**
     * Z component of the vector
     */
    public final double z;
    
    /**
     * create new vector from the x, y, z components specified
     * @param x the x component
     * @param y the y component
     * @param z the z component
     */
    public Vec3f(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * returns the sum of this Vector and the Vector specified
     * @param v the specified Vector
     * @return the sum
     */
    public Vec3f add(Vec3f v) {
        return new Vec3f(x + v.x, y + v.y, z + v.z);
    }
    
    /**
     * returns the sum of this Vector and the Vector components specified
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @return the sum
     */
    public Vec3f add(double x, double y, double z) {
        return new Vec3f(this.x + x, this.y + y, this.z + z);
    }
    
    /**
     * returns the sum of this Vector's components and the scalar value
     * @param s the scalar value
     * @return the sum
     */
    public Vec3f add(double s) {
        return new Vec3f(x + s, y + s, z + s);
    }
    
    /**
     * returns the difference of this Vector and the Vector specified
     * @param v the specified Vector
     * @return the difference
     */
    public Vec3f sub(Vec3f v) {
        return new Vec3f(x - v.x, y - v.y, z - v.z);
    }
    
    /**
     * returns the difference of this Vector's components and the scalar value
     * @param s the scalar value
     * @return the difference
     */
    public Vec3f sub(double s) {
        return new Vec3f(x - s, y - s, z - s);
    }
    
    /**
     * returns the difference of this Vector and the Vector components specified
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @return the difference
     */
    public Vec3f sub(double x, double y, double z) {
        return new Vec3f(this.x - x, this.y - y, this.z - z);
    }
    
    /**
     * returns the result of the multiplication of this Vectors component and
     * the specified scalar value
     * @param s the scalar value
     * @return a new Vector with the multiplication
     */
    public Vec3f mult(double s) {
        return new Vec3f(x * s, y * s, z * s);
    }
    
    /**
     * returns the result of multiplying each component of this vector by the
     * component, effectively scaling this vector unevenly by the components.
     * @param v the vector to scale with
     * @return a new Vector with the result
     */
    public Vec3f scale(Vec3f v) {
        return new Vec3f(x * v.x, y * v.y, z * v.z);
    }
    
    /**
     * returns the result of multiplying each component of this vector by the
     * components specified, effectively scaling this vector unevenly by
     * components.
     * @param x the x scale
     * @param y the y scale
     * @param z the z scale
     * @return a new Vector with the result
     */
    public Vec3f scale(double x, double y, double z) {
        return new Vec3f(this.x * x, this.y * y, this.z * z);
    }
    
    /**
     * Divides this vector by the scalar value specified.
     * @param s the scalar
     * @return a new Vector with the result
     */
    public Vec3f div(double s) {
        return new Vec3f(x / s, y / s, z / s);
    }
    
    /**
     * returns the Vector dot product of this Vector and the Vector specified.
     * @param v the Vector specified
     * @return a new Vector with the result
     */
    public double dot(Vec3f v) {
        return (x * v.x) + (y * v.y) + (z * v.z);
    }
    
    /**
     * returns the Vector cross product of this Vector and the Vector specified
     * @param v the Vector specified
     * @return a new Vector with the result
     */
    public Vec3f cross(Vec3f v) {
        return new Vec3f(
                (y * v.z) - (x * v.y),
                (z * v.x) - (x * v.z),
                (x * v.y) - (y * v.x)
        );
    }
    
    /**
     * calculates and returns the length of the vector
     * @return a double with the length
     */
    public double length() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }
    
    /**
     * returns a new Vector that is of normal length that is, length returns 1
     * @return the result of dividing this vector by its length
     */
    public Vec3f normalize() {
        return div(length());
    }
    
    /**
     * Converts this vector to a string for display purposes
     * @return the vector as a string
     */
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
    
    /**
     * Converts this vector to a string for display purposes, fixing the number
     * of decimal places.
     * @return 
     */
    public String toStringFixed() {
        return String.format("(%.2f, %.2f, %.2f)", x, y, z);
    }
    
    /**
     * returns a Vector with the biggest component of either Vector (the implicit
     * or specified). i.e x = Math.max(a.x, b.x) ...
     * @param v the Vector to compare with
     * @return the new Vector
     */
    public Vec3f max(Vec3f v) {
        return new Vec3f (
                Math.max(x, v.x),
                Math.max(y, v.y),
                Math.max(z, v.z)
        );
    }
    
    /**
     * returns a Vector with the smallest component of either Vector (the implicit
     * or specified). i.e x = Math.min(a.x, b.x) ...
     * @param v the Vector to compare with
     * @return the new Vector
     */
    public Vec3f min(Vec3f v) {
        return new Vec3f (
                Math.min(x, v.x),
                Math.min(y, v.y),
                Math.min(z, v.z)
        );
    }
    
    /**
     * returns the Euclidean distance between this Vector and the parameter
     * @param v the vector to find the distance to
     * @return the scalar distance
     */
    public double distance(Vec3f v) {
        return (x * v.x) - (y * v.y) - (z * v.z);
    }
    
    /**
     * Checks wether the components of this Vector is inside the range specified
     * @param start the minimum value, compared by each components
     * @param end the maximum value, compared by each components
     * @return true if inside of the range, else false
     */
    public boolean inBounds(Vec3f start, Vec3f end) {
        return (start.x <= x && x <= end.x) &&
               (start.y <= y && y <= end.y) &&
               (start.z <= z && z <= end.z);
    }
    
    //Zero Vector
    public static final Vec3f ZERO = new Vec3f(0.0, 0.0, 0.0);
    //Identity Vector
    public static final Vec3f IDENTITY = new Vec3f(1, 1, 1);
}
