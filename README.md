# 8Puzzle-Solver
8Puzzle Solver that reads a file of commands and solves using either A* search or beam search.
# 8Puzzle-Solver
8Puzzle Solver that reads a file of commands and solves using either A* search or beam search.

Commands:
setState state
  Sets the state to solve to the specified state.  
  Takes a string formated as "b12 345 678" where b is the blank space
randomizeState n
  Moves the blank space n times in a random direction each time.
printState
  Prints the current state
move direction
  move the blank space in the specified direction, if possible
  Directions are "left", "right", "up", "down".
solve a-Star heuristic
  Solves the puzzle using A* search with the given heuristic. 
  h1: The number of tiles (excluding the blank) that are not in their correct place 
  h2: The sum of the distance (vertical + horizontal) each tile is away from its correct place. 
solve beam k
  Solves the puzzle using local beam search with a beamsize of k.
maxNodes n
  Sets the maximum number of nodes to expand.  If either puzzle reaches the maximum, the puzzle returns with no solution.
