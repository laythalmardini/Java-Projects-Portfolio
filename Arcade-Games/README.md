# Arcade Games Collection
**GUI Development & Event-Driven Programming**

A collection of interactive Java Swing applications focused on responsive UI design, structured game loops, and asset management. Each game is built as a standalone module for modular expansion and independent execution.

---

## 🕹️ Current Games


### 🍕 PacMan

A grid-based maze game featuring pathfinding logic and asset-driven animations.

- **Entity Management**  
  Utilizes dedicated classes for `Player`, `Ghost`, and `Food` entities to handle collision and movement.

- **Dynamic Level Loading**  
  Features a multi-level system that parses string-based maps into playable game boards.

- **State-Driven Rendering**  
  Manages game states for win/loss conditions and power-up durations like Scared Ghosts.

- **Asset Pipeline**  
  Integrates high-resolution PNG sprites for Pac-Man, ghosts, and environment tiles.


---
### 🐹 WhacAMole

A classic arcade implementation built using Java AWT and Swing libraries.

- **Tile-Based Logic**  
  Uses a custom `Tile` class to manage board state, rendering, and entity positioning.

- **Dynamic Event Handling**  
  Implements `ActionListener` for precise input handling and real-time score updates.

- **Automated Game Loop**  
  Includes controlled spawning cycles for moles and obstacles.

- **Asset Pipeline**  
  Integrates external high-resolution PNG sprites for polished visuals.

---

## 🔜 Coming Soon

### 🕊️ Flappy Bird

The next major addition to the arcade collection.

---

## 🚀 How to Run

To play a game, navigate into its directory and use the Gradle wrapper.

### 🎯 WhacAMole

```bash
cd WhacAMole
./gradlew run
```

### 🟡 PacMan

```bash
cd PacMan
./gradlew run
```

---

## 🧠 Architectural Philosophy

- **Event-Driven Design**  
  Uses Java Swing’s event model to separate UI rendering from gameplay logic.

- **Modular Structure**  
  Each game operates as its own independent project with isolated dependencies.

- **Scalable Expansion**  
  New games can be added by simply creating a new folder with its own Gradle wrapper.

---

*Part of the Java Projects Portfolio*
