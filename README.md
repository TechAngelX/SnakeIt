#  SnakeIt

<p align="center">
  <img src="src/main/resources/Screenshot1.png" width="45%" />
  <img src="src/main/resources/Screenshot2.png" width="45%" />
</p>

## 🎮 Gameplay

- Use the arrow keys to control the snake
- Eat apples to grow longer
- The game ends if you:
  - Hit the wall
  - Run into yourself

## 🛠️ Tech Stack

- Java
- Swing (GUI)
- Maven (project structure & build)

## 📂 Project Structure

```
SnakeIt/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── org/
        │       └── snakeIt/
        │           ├── Audio.java
        │           ├── GameFrame.java
        │           ├── GamePanel.java
        │           └── SnakeGame.java
        └── resources/
            ├── Screenshot1.png
            └── Screenshot2.png
```

## ▶️ How to Run

### Option 1: Run with Java (Quickest)

From the project root:

```bash
cd src/main/java
javac org/snakeIt/*.java
java org.snakeIt.SnakeGame
```

### Option 2: Run with Maven

From the project root:

```bash
mvn clean package
java -cp target/classes org.snakeIt.SnakeGame
```

### Option 3: Run in an IDE (Recommended)

1. Open IntelliJ IDEA or Eclipse
2. Open the `SnakeIt` folder
3. Import as a Maven project
4. Open `SnakeGame.java`
5. Right-click → Run

## ⌨️ Controls

| Key | Action |
|-----|--------|
| ↑ | Move Up |
| ↓ | Move Down |
| ← | Move Left |
| → | Move Right |


## 📚 Credits

- [Bro Code](https://www.youtube.com/watch?v=bI6e6qjJ8JQ) – Snake Game tutorial
- [Stack Overflow](https://stackoverflow.com/questions/34036216/drawing-java-grid-using-swing) – Java Swing grid rendering

## 🤝 Contributing

Feel free to fork, experiment, and improve the project. Pull requests and suggestions are welcome.


## License

© Ricki Angel 2026 | TechAngelX
Internal use only.


## Disclaimer

This tool is for personal or educational use only and comes without any warranty.
***
<h2 style="text-align: center;">Support</h2>
<div align="center">
  <span style="font-size: 1.4em; font-weight: 300;">
    For issues or questions, feel free to reach out to me on GitHub.
  </span>
  <br /><br />
  <a href="https://techangelx.com" target="_blank" rel="noopener noreferrer">
    <img src="./readme_images/logo.png" alt="Tech Angel X Logo" width="70" height="70" style="vertical-align: middle; border-radius: 50%; border: 4px solid #ffffff; box-shadow: 0 4px 10px rgba(0,0,0,0.2);">
  </a>
  <br /><br />
  <span style="font-size: 1.4em; font-weight: 300;">
    <b>Built by Ricki Angel</b> • <a href="https://techangelx.com" target="_blank" rel="noopener noreferrer" style="text-decoration: none;">Tech Angel X</a>
  </span>
</div>

