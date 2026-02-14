# Arcade Games Collection
**GUI Development & Event-Driven Programming**

A collection of interactive Java Swing applications focused on responsive UI design, structured game loops, and asset management. Each game is built as a standalone module for modular expansion and independent execution.

---

## 🕹️ Current Games

### 🐹 Whac-A-Mole

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

### 🟡 Pac-Man

The next major addition to the arcade collection.

---

## 🚀 How to Run

To play a game, navigate into its directory and use the Gradle wrapper.

### 🎯 Whac-A-Mole

```bash
cd WhacAMole
./gradlew run
```

### 🟡 Pac-Man (Coming Soon)

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
