package se.adlez.game;

import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public class Forest implements Serializable {

    public static final int WIDTH = 10; 
    public static final int HEIGHT = 10; 
    
    private Map<Position, Item> items; 
    private AbstractMoveableItem player; 
    private AbstractMoveableItem hunter; 
    private AbstractMoveableItem home; 
    private StringBuffer status; 
    private boolean gameOver; 

    public Forest() {
        init();
    }

    public void init() {
        this.items = new HashMap<>(); 
        this.status = new StringBuffer();
        this.gameOver = false;
        this.player = null;
        this.hunter = null;
        this.home = null;
    }

    public String getGamePlan() { 
        StringBuilder sb = new StringBuilder();
        
        for (int y = 1; y <= HEIGHT; y++) {
            for (int x = 1; x <= WIDTH; x++) {
                
                Position currentPos = new Position(x, y);
                String graphicToPrint = "🟩"; 
                
                if (player != null && player.getPosition().equals(currentPos)) {
                    graphicToPrint = player.getGraphic();
                } else if (hunter != null && hunter.getPosition().equals(currentPos)) {
                    graphicToPrint = hunter.getGraphic();
                } else if (home != null && home.getPosition().equals(currentPos)) {
                    graphicToPrint = home.getGraphic();
                } 
                else if (items.containsKey(currentPos)) {
                    graphicToPrint = items.get(currentPos).getGraphic();
                }
                
                sb.append(graphicToPrint);
            }
            sb.append('\n');
        }
        
        return sb.toString();
    } 
    
    public boolean addItem(Item item, Position position) { 
        if (items.containsKey(position)) {
            return false;
        }
        
        items.put(position, item);
        return true;
    } 

    public String listItems() { 
        StringBuilder sb = new StringBuilder();
        
        for (Map.Entry<Position, Item> entry : items.entrySet()) {
            Position pos = entry.getKey();
            Item item = entry.getValue();
            
            sb.append(pos.toString());
            sb.append(" ");
            sb.append(item.toString());
            sb.append('\n');
        }
        
        return sb.toString();
    } 
    
    public boolean tryAddItem(Item item, Position position) { 
        if (position.getX() < 1 || position.getX() > WIDTH || 
            position.getY() < 1 || position.getY() > HEIGHT) {
            return false; 
        }
        
        if ((player != null && player.getPosition().equals(position)) ||
            (hunter != null && hunter.getPosition().equals(position)) ||
            (home != null && home.getPosition().equals(position))) {
            return false;
        }
        
        return addItem(item, position);
    } 

    public void addPlayerItem(AbstractMoveableItem player) { 
        this.player = player; 
    } 
    
    public void addHunterItem(AbstractMoveableItem hunter) { 
        this.hunter = hunter; 
    }
    
    public void addHomeItem(AbstractMoveableItem home) { 
        this.home = home; 
    }
    
    private boolean isPositionInBounds(Position pos) {
        if (pos.getX() >= 1 && pos.getX() <= WIDTH && 
            pos.getY() >= 1 && pos.getY() <= HEIGHT) {
            return true;
        }
        return false;
    }
    
    public void movePlayer(Position relative) {
        if (gameOver || player == null || hunter == null || home == null) {
            return;
        }
        
        status.setLength(0); 
        
        Position playerPos = player.getPosition();
        Position newPos = new Position(playerPos);
        newPos.move(relative);
        
        if (!isPositionInBounds(newPos)) {
            status.append("Player can not move!\n");
            return;
        }
        
        if (newPos.equals(home.getPosition())) {
            player.setPosition(newPos);
            status.append("Player reached home!\nGame is over!\n");
            gameOver = true;
            return;
        } 
        
        if (newPos.equals(hunter.getPosition())) {
            player.setPosition(newPos);
            status.append("The hunter caught up!\nGame is over!\n");
            gameOver = true;
            return;
        } 
        
        if (items.containsKey(newPos)) {
            status.append("Player can not move!\n");
            return;
        }
        
        player.setPosition(newPos); 
        status.append("Player moved!\n");
        
        moveHunter();
    } 

    private void moveHunter() {
        Position hunterPos = hunter.getPosition();
        Position playerPos = player.getPosition();
        
        int dx = playerPos.getX() - hunterPos.getX();
        int dy = playerPos.getY() - hunterPos.getY();

        Position bestRelativeMove = new Position(0, 0); 
        
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                bestRelativeMove = new Position(1, 0);
            } else {
                bestRelativeMove = new Position(-1, 0);
            }
        } else if (Math.abs(dy) > 0) {
            if (dy > 0) {
                bestRelativeMove = new Position(0, 1);
            } else {
                bestRelativeMove = new Position(0, -1);
            }
        }

        Position newHunterPos = new Position(hunterPos);
        newHunterPos.move(bestRelativeMove);
        
        if (!isPositionInBounds(newHunterPos) || items.containsKey(newHunterPos)) {
            status.append("Hunter is hiding and not moving!\n");
            return;
        }
        
        if (newHunterPos.equals(playerPos)) {
            hunter.setPosition(newHunterPos);
            status.append("Hunter caught up to the player!\nGame is over!\n");
            gameOver = true;
            return;
        } 
        
        hunter.setPosition(newHunterPos);
        status.append("Hunter is coming closer\n"); 
    }

    public boolean isGameOver() { 
        return gameOver; 
    } 
    
    public String getStatus() { 
        return status.toString(); 
    } 
    
    public Map<Position, Item> getItems() { 
        return items; 
    }
    public AbstractMoveableItem getPlayer() { 
        return player; 
    }
    public AbstractMoveableItem getHunter() { 
        return hunter; 
    }
    public AbstractMoveableItem getHome() { 
        return home; 
    }
}