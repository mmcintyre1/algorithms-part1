package src.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] grid;
    private final int gridSize;
    private int openSites;
    private final WeightedQuickUnionUF wqf;
    private final int virtualTop = 0;
    private final int virtualBottom;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        gridSize = n;
        openSites = 0;
        grid = new boolean[n][n];
        wqf = new WeightedQuickUnionUF(n * n + 2);
        virtualBottom = n * n + 1;
    }

    public void open(int row, int col) {
        if (row > gridSize || col > gridSize) {
            throw new IllegalArgumentException("Row or Column is too large.");
        }
        if (!isOpen(row, col)) {
            openSites += 1;
            grid[row - 1][col - 1] = true;
        }

        if (row == 1) {
            wqf.union(getNumberIndex(row, col), virtualTop);
        }
        if (row == gridSize) {
            wqf.union(getNumberIndex(row, col), virtualBottom);
        }

        // union neighbors
        if (row > 1 && isOpen(row - 1, col)) {
            wqf.union(getNumberIndex(row, col), getNumberIndex(row - 1, col));
        }
        if (row < gridSize && isOpen(row + 1, col)) {
            wqf.union(getNumberIndex(row, col), getNumberIndex(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            wqf.union(getNumberIndex(row, col), getNumberIndex(row, col - 1));
        }
        if (col < gridSize && isOpen(row, col + 1)) {
            wqf.union(getNumberIndex(row, col), getNumberIndex(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        try {
            return grid[row - 1][col - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("argument out of range of grid size.");
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > 0 && row <= gridSize && col > 0 && col <= gridSize) {
            return wqf.find(virtualTop) == wqf.find(getNumberIndex(row, col));
        } else {
            throw new IllegalArgumentException("argument out of range of grid size.");
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    private int getNumberIndex(int row, int col) {
        return gridSize * (row - 1) + col;
    }

    // does the system percolate?
    public boolean percolates() {
        return wqf.find(virtualTop) == wqf.find(virtualBottom);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(9);
        for (int i = 1; i < 10; i++) {
            p.open(i, 1);
        }
        System.out.println(p.numberOfOpenSites());
        System.out.println(p.percolates());
    }
}
