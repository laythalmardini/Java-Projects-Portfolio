# Forest Simulation Engine
**Advanced OOP & Data Persistence**

A Java-based simulation engine managing a 10x10 grid world where entities interact through a unified interface system.

## 🛠 Technical Highlights

### 🏗 Object-Oriented Architecture
* **Polymorphism & Interfaces**: Decouples engine logic from specific behaviors using `Item` and `Moveable` interfaces.
* **Abstract Inheritance**: Utilizes `AbstractItem` and `AbstractMoveableItem` to enforce shared logic across entities like Robots, Wolves, and Trees.
* **Spatial Tracking**: Efficient coordinate-based tracking for entity positions and interaction logic.

### 💾 Dual Persistence
The engine implements two distinct strategies for saving the world state:
1. **JSON (Jackson)**: Provides a human-readable, editable world configuration for easy debugging.
2. **Binary Serialization**: Captures high-fidelity snapshots of the object graph for instant restoration.

## 🚀 How to Run
From the `Forest-Game` directory, use the Gradle wrapper:

```bash
./gradlew run
