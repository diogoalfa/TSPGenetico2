/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tspgenetico2;



public class Ciudad {
    
    private double x;
    private double y;
    private String id;

    public Ciudad(String id, double x, double y) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double distanceTo(Ciudad c2){
        double temp1 = Math.pow(this.x - c2.getX(),2);
        double temp2 = Math.pow(this.y - c2.getY(),2);
        
        return Math.sqrt(temp1 + temp2);
    }
    
  /*  
    public static void main (String args[]){
        Ciudad c1 = new Ciudad("1", 10,10);
        Ciudad c2 = new Ciudad("2", 10,20);
        
        System.out.println(c1.distanceTo(c2));
    }
 */
    
    @Override
    public String toString() {
        return "   " + id + "{" + x + ", " + y + "}\n";
    }
    
    
    
    
}
