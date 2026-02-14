package se.adlez.game;

import java.io.Serializable;

public interface Item extends Serializable {
    String getDescription();
    String getGraphic();
}