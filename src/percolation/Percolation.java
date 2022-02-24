/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private boolean[] topConnected;
    private boolean[] bottomConnected;
    private final int n;
    private final WeightedQuickUnionUF uf;
    private int openCount = 0;
    private boolean percolated = false;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        uf = new WeightedQuickUnionUF(n * n);
        grid = new boolean[n][n];
        topConnected = new boolean[n * n];
        bottomConnected = new boolean[n * n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        row--;
        col--;
        if (!grid[row][col]) {
            grid[row][col] = true;
            openCount++;
            boolean top = false;
            boolean bottom = false;

            if (row > 0 && grid[row - 1][col]) {
                top = topConnected[find(row - 1, col)];
                bottom = bottomConnected[find(row - 1, col)];
                union(row, col, row - 1, col);
            }
            if (row < n - 1 && grid[row + 1][col]) {
                top = top || topConnected[find(row + 1, col)];
                bottom = bottom || bottomConnected[find(row + 1, col)];
                union(row, col, row + 1, col);
            }
            if (col > 0 && grid[row][col - 1]) {
                top = top || topConnected[find(row, col - 1)];
                bottom = bottom || bottomConnected[find(row, col - 1)];
                union(row, col, row, col - 1);
            }
            if (col < n - 1 && grid[row][col + 1]) {
                top = top || topConnected[find(row, col + 1)];
                bottom = bottom || bottomConnected[find(row, col + 1)];
                union(row, col, row, col + 1);
            }
            if (row == 0) {
                top = true;
            }
            if (row == (n - 1)) {
                bottom = true;
            }

            if (top && bottom) {
                percolated = true;
            }
            topConnected[find(row, col)] = top;
            bottomConnected[find(row, col)] = bottom;

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return topConnected[find(row - 1, col - 1)];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolated;
    }

    private int map(int row, int col) {
        return row * n + col;
    }

    private int find(int row, int col) {
        return uf.find(map(row, col));
    }

    private void union(int row1, int col1, int row2, int col2) {
        uf.union(map(row1, col1), map(row2, col2));
    }

    private void validate(int row, int col) {
        if (row > n || row < 1 || col > n || col < 1) {
            throw new IllegalArgumentException();
        }
    }
}
