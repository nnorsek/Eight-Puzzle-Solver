import java.util.*;

public class EightPuzzleSolver {
    public static void main(String[] args) {
        // Initialize the 3x3 array to store the initial state of the puzzle
        int[][] initialBoard = new int[3][3];
        // Create a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);
        // Prompt the user to enter the initial state of the puzzle
        System.out.println("Enter the initial state of the puzzle (0 represents the blank tile):");
        // Read the initial state of the puzzle from the user
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                initialBoard[i][j] = scanner.nextInt();
            }
        }

        // Validate the input provided by the user
        if (!validateInput(initialBoard)) {
            System.out.println("Invalid input. Please provide a valid puzzle configuration.");
            return;
        }

        // Create the initial state object for the puzzle
        PuzzleState initialState = new PuzzleState(initialBoard, 0, null);
        // Solve the puzzle using the A* search algorithm
        PuzzleState solution = solvePuzzle(initialState);

        // Print the series of moves required to solve the puzzle
        if (solution != null) {
            System.out.println("Series of moves required to solve the puzzle:");
            printSolution(solution);
        } else {
            System.out.println("No solution found.");
        }
    }

    // A* search algorithm to solve the puzzle
    private static PuzzleState solvePuzzle(PuzzleState initialState) {
        // Priority queue to store puzzle states based on their heuristic value
        PriorityQueue<PuzzleState> priorityQueue = new PriorityQueue<>();
        // Set to store visited states to avoid redundant expansion
        Set<String> visitedStates = new HashSet<>();
        // Add the initial state to the priority queue
        priorityQueue.offer(initialState);

        // Loop until the priority queue is empty
        while (!priorityQueue.isEmpty()) {
            // Retrieve and remove the state with the minimum priority
            PuzzleState currentState = priorityQueue.poll();

            // Check if the current state is the goal state
            if (currentState.isGoalState()) {
                return currentState;
            }

            // Generate neighboring states
            List<PuzzleState> neighbors = currentState.getNeighbors();
            for (PuzzleState neighbor : neighbors) {
                // Add neighbors to the priority queue if they haven't been visited before
                if (!visitedStates.contains(Arrays.deepToString(neighbor.board))) {
                    priorityQueue.offer(neighbor);
                    visitedStates.add(Arrays.deepToString(neighbor.board));
                }
            }
        }
        return null;
    }

    // Print the series of moves required to solve the puzzle
    private static void printSolution(PuzzleState solution) {
        // Store the series of moves in a list
        List<PuzzleState> moves = new ArrayList<>();
        // Iterate through the states in reverse order and add them to the list
        while (solution != null) {
            moves.add(solution);
            solution = solution.prevState;
        }
        // Reverse the list to print the moves in the correct order
        Collections.reverse(moves);

        // Iterate through the list and print each state
        for (PuzzleState state : moves) {
            printBoard(state.board);
            System.out.println();
        }
    }

    // Print the current state of the puzzle board
    private static void printBoard(int[][] board) {
        // Iterate through the rows and columns of the board and print each element
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Validate the input provided by the user
    private static boolean validateInput(int[][] board) {
        // Check if the board is a 3x3 grid
        if (board.length != 3 || board[0].length != 3) {
            return false;
        }
        // Set to store unique numbers encountered in the board
        Set<Integer> numbers = new HashSet<>();
        // Iterate through the rows and columns of the board
        for (int[] row : board) {
            for (int num : row) {
                // Check if the number is within the valid range (0-8)
                if (num < 0 || num > 8) {
                    return false;
                }
                // Check if the number has already been encountered
                if (numbers.contains(num)) {
                    return false;
                }
                // Add the number to the set
                numbers.add(num);
            }
        }
        return true;
    }
}
