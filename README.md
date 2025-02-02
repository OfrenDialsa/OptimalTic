# OptimalTic - Tic-Tac-Toe Game

OptimalTic is a Tic-Tac-Toe game developed using **Jetpack Compose** with AI-powered gameplay based on the **Minimax Algorithm**. The game provides multiple modes, including **Normal**, **Impossible**, and **Multiplayer**.

## Features

- **Single Player Mode**
  - **Normal Mode**: AI makes mistakes occasionally, allowing a fair challenge.
  - **Impossible Mode**: AI is unbeatable, using the Minimax algorithm for optimal moves.
- **Multiplayer Mode**: Play with a friend on the same device.
- **Sleek UI**: Built with Jetpack Compose for a modern and smooth user experience.
- **Optimized AI**: Uses the Minimax algorithm for strategic decision-making.

## Screenshots

_Add some screenshots here_

## Tech Stack

- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern UI toolkit
- **Minimax Algorithm** - AI for Impossible mode
- **MVVM Architecture** - Clean architecture for better maintainability

## How to Play

1. Choose a game mode:
   - **Normal** (AI with random moves)
   - **Impossible** (Unbeatable AI)
   - **Multiplayer** (Two players on the same device)
2. Play by tapping on an empty cell to mark **X** or **O**.
3. The game ends when a player wins or when the board is full (draw).
4. Restart the game to play again.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/OfrenDialsa/OptimalTic.git
   ```
2. Open the project in **Android Studio**.
3. Build and run the app on an emulator or a physical device.

## Minimax Algorithm

The **Minimax algorithm** is implemented to ensure optimal gameplay in **Impossible Mode**:
- AI evaluates all possible moves and picks the one that maximizes its chances of winning.
- Uses recursion to explore possible future states.
- Implements pruning techniques for performance optimization.


## Contributing

Feel free to contribute by submitting **pull requests** or **reporting issues**!

## License

This project is licensed under the **MIT License**.

---

Enjoy playing **OptimalTic** and challenge yourself against an unbeatable AI! 🎮

