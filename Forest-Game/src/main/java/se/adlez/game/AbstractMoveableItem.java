package se.adlez.game;


public abstract class AbstractMoveableItem extends AbstractItem implements Moveable {

    protected Position position; 

    public AbstractMoveableItem(String description, String graphic, Position position) {
        super(description, graphic);
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    @Override
    public String toString() {
        return super.toString() + " " + position.toString();
    }
}