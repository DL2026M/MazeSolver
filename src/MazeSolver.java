/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam, David Lutch
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // Should be from start to end cells
        MazeCell currentCell = maze.getEndCell();
        ArrayList<MazeCell> notInOrderList = new ArrayList<MazeCell>();
        // Adds the ending MazeCell to the beginning of the ArrayList
        notInOrderList.add(currentCell);
        // Runs until the current cell meets the starting cell
        while (maze.getStartCell() != currentCell) {
                currentCell = currentCell.getParent();
                notInOrderList.add(currentCell);
        }
        return reverseOrder(notInOrderList);
    }
    // A helper function that reverses the order of an ArrayList of MazeCells
    private ArrayList<MazeCell> reverseOrder(ArrayList<MazeCell> list) {
        Stack<MazeCell> stack = new Stack<MazeCell>();
        ArrayList<MazeCell> inOrderList = new ArrayList<MazeCell>();
        int size = list.size();
        // Adds every element of the ArrayList to a stack
        for (int i = 0; i < size; i++) {
            stack.push(list.get(i));
        }
        // Removes the top MazeCell from the stack and adds it to ArrayList
        for (int j = 0; j < size; j++) {
            inOrderList.add(stack.pop());
        }
        return inOrderList;
    }
    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Stack<MazeCell> order = new Stack<MazeCell>();
        // Adding the starting cell to the stack
        order.push(maze.getStartCell());
        MazeCell nextCell;
        // Runs until the maze has been solved using depth first search
        while (order.peek() != maze.getEndCell()) {
            MazeCell top = order.pop();
            if (maze.isValidCell(top.getRow() - 1, top.getCol())) {
                nextCell = maze.getCell(top.getRow() - 1, top.getCol());
                // If the cell north of the current cell is valid, then add it to the stack of maze cells
                helperFunctionDFS(order, nextCell, top);
            }
            // Checks to see if the cell to the east is valid
            if (maze.isValidCell(top.getRow(),top.getCol() + 1)) {
                nextCell = maze.getCell(top.getRow(),top.getCol() + 1);
                helperFunctionDFS(order, nextCell, top);
            }
            if (maze.isValidCell(top.getRow() + 1, top.getCol())) {
                nextCell = maze.getCell(top.getRow() + 1, top.getCol());
                helperFunctionDFS(order, nextCell, top);

            }
            if (maze.isValidCell(top.getRow(), top.getCol() - 1)) {
                nextCell = maze.getCell(top.getRow(), top.getCol() - 1);
                // Setting the cell to the west as the parent of the current cell
                helperFunctionDFS(order, nextCell, top);
            }
        }
        return getSolution();
    }
    private void helperFunctionDFS(Stack<MazeCell> list, MazeCell nextCell, MazeCell topCell) {
        // Adds the next cell to the top of the stack
        list.push(nextCell);
        nextCell.setParent(topCell);
        nextCell.setExplored(true);
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Using a queue instead of a stack to solve the maze
        Queue<MazeCell> order = new LinkedList<MazeCell>();
        order.add(maze.getStartCell());
        MazeCell nextCell;
        while (order.peek() != maze.getEndCell()) {
            MazeCell top = order.remove();
            if (maze.isValidCell(top.getRow() + 1, top.getCol())) {
                nextCell = maze.getCell(top.getRow() + 1, top.getCol());
                helperFunctionBFS(order, nextCell, top);
            }
            if (maze.isValidCell(top.getRow(),top.getCol() + 1)) {
                nextCell = maze.getCell(top.getRow(), top.getCol() + 1);
                helperFunctionBFS(order, nextCell, top);
            }
            // Checks to see if the cell to the south is valid
            if (maze.isValidCell(top.getRow() - 1, top.getCol())) {
                nextCell = maze.getCell(top.getRow() - 1, top.getCol());
                helperFunctionBFS(order, nextCell, top);
            }
            if (maze.isValidCell(top.getRow(), top.getCol() - 1)) {
                nextCell = maze.getCell(top.getRow(), top.getCol() - 1);
                helperFunctionBFS(order, nextCell, top);
            }
        }
        return getSolution();
    }
    private void helperFunctionBFS(Queue<MazeCell> list, MazeCell nextCell, MazeCell topCell) {
            list.add(nextCell);
            nextCell.setParent(topCell);
            // Sets the next cell to be explored so that it isn't explored again
            nextCell.setExplored(true);
    }


    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
