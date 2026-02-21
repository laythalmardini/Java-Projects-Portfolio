# Java Projects Portfolio

A centralized hub for professional Java development projects, focusing on Object-Oriented Programming (OOP), modular architecture, and Graphical User Interfaces (GUI).

---

## 📂 Portfolio Structure

This repository uses a **Standalone Container** architecture. Each project is independent and carries its own build engine.

- **[Forest-Game](./Forest-Game)**  
  A backend-focused simulation engine demonstrating advanced data structures, inheritance, and dual-mode persistence (JSON & Binary).

- **[Arcade-Games](./Arcade-Games)**  
  A collection of event-driven GUI applications built with Java Swing, featuring real-time game loops and asset management.

---

## 🏗️ Technical Philosophy

- **Independence**  
  Every project folder contains its own Gradle wrapper. This allows different projects to use different Java versions or dependencies without conflicts.

- **Separation of Concerns**  
  Logic is strictly decoupled from UI, following standard Java package conventions.

- **Scalability**  
  The "Plug-and-Play" design allows new games or engines to be added by simply dropping in a new standalone folder.

---

## 🚀 Quick Start

To run any project, navigate into its specific directory and use the Gradle wrapper.

### 🌲 Forest Simulation

```bash
cd Forest-Game
./gradlew run
```

### 🎯 Whac-A-Mole (Arcade)

```bash
cd Arcade-Games/WhacAMole
./gradlew run
```

### 🍕 PacMan (Arcade)

```bash
cd Arcade-Games/PacMan
./gradlew run
```

---

## 👤 Maintained By

**Layth Al Mardini**
