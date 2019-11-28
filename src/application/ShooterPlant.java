package application;

import javafx.scene.Group;

public class ShooterPlant extends Plants
{
    protected int shootingTime;
    protected Pea peas;

    public ShooterPlant(int x, int y, Group g)
    {
        super(x,y,g);

    }
    @Override
    public void step(){
        this.peas.step();
    }
}