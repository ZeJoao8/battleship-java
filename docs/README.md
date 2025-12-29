# LDTS_Group_0607 – Battleship

This project is a terminal-based implementation of the classic game **Battleship**, where a human player competes against an AI-controlled opponent.  
The player can configure the game settings, place ships manually or automatically, and play a complete match against different bot strategies with increasing levels of difficulty.

The game follows a clear separation between model, view, and controller components, and makes extensive use of design patterns to ensure maintainability, extensibility, and testability.  
This project was developed by **José Silva** (*up202406359@up.pt*), **José Lopes** (*up202406360@up.pt*) and **António Machado** (*up202404630@up.pt*) for **LDTS 2025/2026**.

---

## IMPLEMENTED FEATURES

- **Configurable Game Settings**  
  The player can configure board width and height, as well as the difficulty of the bot opponent before starting the game.


- **Menu-Based Navigation**  
  The game includes a fully functional menu system that allows navigation between game states such as settings, placement, battle, and end screens.


- **Manual Fleet Placement**  
  The player can manually place ships on the board, rotate them, undo previous placements, and validate placement constraints.


- **Automatic Fleet Placement**  
  The game supports automatic placement of the entire fleet according to the official game rules.


- **Multiple AI Bot Strategies**
    - **Random Bot (Easy)** – Selects a random valid target.
    - **Hunt Bot (Medium)** – Selects a random valid target following a checkerboard-like pattern. After a successful hit, targets neighboring cells.
    - **Probability Bot (Hard)** – Uses hit clustering and orientation inference to improve targeting, creating heatmaps of the board.
    - **Perfect Bot (Impossible)** – Always targets ship positions when available.


- **Turn-Based Battle System**  
  Player and bot alternate turns, with validation to prevent repeated or invalid shots.


- **Shot Validation and Feedback**  
  Hits, misses, sunk ships, and valid placement for boats or for shooting, are correctly detected and displayed.


- **End Game Screen**  
  Displays the winner and final statistics at the end of the match.


- **Game Statistics Tracking**  
  Tracks total shots, hits, misses, accuracy, and total game duration.


- **Final Boards**  
  Shows the state that both boards were in when the game ended.


---

## DESIGN

### UML
![UML](docs/images/UML.png)

### GAME FLOW MANAGEMENT WITH THE STATE PATTERN

#### Problem in Context
The game progresses through multiple distinct phases (menu, settings, placement, battle, end).  
Handling these phases with conditional logic in a single game loop would result in complex and error-prone code.

#### The Pattern
The **State Pattern** was applied to encapsulate each phase of the game as a separate object.

#### Implementation
- Abstract base class: `State<T>`
- Concrete states:
    - `MenuState`
    - `SettingsState`
    - `PlacementState`
    - `BattleState`
    - `EndState`

Each state defines its own controller and viewer.

#### Consequences
**Benefits**
- Clear separation of responsibilities.
- Elimination of large conditional structures.
- Easy extension with new states.

**Liabilities**
- Increased number of classes.

---

### BOT BEHAVIOR USING THE STRATEGY PATTERN

#### Problem in Context
Different difficulty levels require different targeting algorithms.  
Embedding all behaviors in a single class would lead to large conditional blocks and poor extensibility.

#### The Pattern
The **Strategy Pattern** allows each bot behavior to be implemented independently.

#### Implementation
- Strategy interface: `BotStrategy`
- Concrete strategies:
    - `Random (Easy)`
    - `Hunt (Medium)`
    - `Probability (Hard)`
    - `Perfect (Impossible)`

#### Consequences
**Benefits**
- Easy to add or modify bot behaviors.
- Improved testability and separation of concerns.

**Liabilities**
- Requires additional abstraction.

---

### BOT CREATION USING THE FACTORY PATTERN

#### Problem in Context
The game must create different bot strategies depending on the selected difficulty.

#### The Pattern
The **Factory Pattern** encapsulates the creation of bot strategies.

#### Implementation
- `BotFactory` selects and instantiates the correct bot strategy.

#### Consequences
**Benefits**
- Centralized creation logic.
- Reduced coupling between game logic and concrete bot implementations.

---

### FLEET CREATION USING THE FACTORY PATTERN

#### Problem in Context
Fleet composition rules (number of ships and sizes) should not be hardcoded into the board or game logic.

#### The Pattern
A **Factory Pattern** was applied to encapsulate fleet creation.

#### Implementation
- `FleetFactory` is responsible for creating and configuring the fleet.
- The board receives a fully initialized fleet.

#### Consequences
**Benefits**
- Separation between game rules and game state.
- Easier modification of fleet rules.

---

## KNOWN CODE SMELLS

- **High Cyclomatic Complexity in Advanced Bots**  
  - The Probability bot contains complex decision logic that is harder to reason about and test exhaustively.

- **Indirect Control Flow**
    - The use of the State pattern introduces indirect control flow, which may make execution paths harder to follow without tooling or debugging support. 

- **Long Classes**  
  - Some classes, such as the board, advanced bot strategies (e.g. Probability), and viewer classes, are relatively long. 
  This is largely due to the amount of domain logic or rendering logic they must encapsulate, and splitting them further would risk reducing cohesion or increasing coupling.
  
---

## TESTING


- **Unit Testing Frameworks**
    - JUnit 5
    - Mockito
    - jqwik (property-based testing)


- **Coverage Analysis**
    - JaCoCo used for line and branch coverage.
    - Overall line coverage above **95%** and branch coverage at **90%**.
  
![Jacoco](docs/images/Jacoco.png)


- **Mutation Testing**
    - PIT (Pitest) used to assess test effectiveness.
    - Mutation coverage around **60%**, with most surviving mutants located in probabilistic bot logic and viewer rendering code.

![Pitest](docs/images/Pitest.png)

### SELF-EVALUATION

- José Silva: 33%
- José Lopes: 33%
- António Machado: 33%