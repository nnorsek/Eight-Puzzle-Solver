import java.util.*;

class PuzzleState implements Comparable<PuzzleState> {
    int[][] board;
    int moves;
    PuzzleState prevState;

    public PuzzleState(int[][] board, int moves, PuzzleState prevState) {
        this.board = board;
        this.moves = moves;
        this.prevState = prevState;
    }

    @Override
    public int compareTo(PuzzleState other) {
        // Define comparison for priority queue
        return Integer.compare(this.moves + manhattanDistance(), other.moves + other.manhattanDistance());
    }

    private int manhattanDistance() {
        // Calculate Manhattan distance heuristic value
        int distance = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = board[i][j];
                if (value != 0) {
                    int goalRow = (value - 1) / 3;
                    int goalCol = (value - 1) % 3;
                    distance += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return distance;
    }

    public boolean isGoalState() {
        // Check if the state is the goal state
        int[][] goalState = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        return Arrays.deepEquals(board, goalState);
    }

    public List<PuzzleState> getNeighbors() {
        // Generate neighboring states
        List<PuzzleState> neighbors = new ArrayList<>();
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        int x = 0, y = 0;

        // Find the blank tile position
        outerloop:
        for (x = 0; x < 3; x++) {
            for (y = 0; y < 3; y++) {
                if (board[x][y] == 0) {
                    break outerloop;
                }
            }
        }

        // Generate neighboring states by moving the blank tile
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {
                int[][] newBoard = deepCopy(board);
                // Swap the blank tile with the neighboring tile
                newBoard[x][y] = newBoard[nx][ny];
                newBoard[nx][ny] = 0;
                PuzzleState neighborState = new PuzzleState(newBoard, moves + 1, this);
                neighbors.add(neighborState);
            }
        }
        return neighbors;
    }

    private int[][] deepCopy(int[][] matrix) {
        int[][] copy = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = Arrays.copyOf(matrix[i], matrix[i].length);
        }
        return copy;
    }
}
