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
        if (row > n || row < 1 || col > n || col < 1) {
            throw new IllegalArgumentException();
        }
        row--;
        col--;
        if (!grid[row][col]) {
            grid[row][col] = true;
            openCount++;
            boolean top = false;
            boolean bottom = false;

            if (row > 0 && grid[row - 1][col]) {
                top = topConnected[uf.find((row - 1) * n + col)];
                bottom = bottomConnected[uf.find((row - 1) * n + col)];
                uf.union(row * n + col, (row - 1) * n + col);
            }
            if (row < n - 1 && grid[row + 1][col]) {
                top = top || topConnected[uf.find((row + 1) * n + col)];
                bottom = bottom || bottomConnected[uf.find((row + 1) * n + col)];
                uf.union(row * n + col, (row + 1) * n + col);
            }
            if (col > 0 && grid[row][col - 1]) {
                top = top || topConnected[uf.find(row * n + col - 1)];
                bottom = bottom || bottomConnected[uf.find(row * n + col - 1)];
                uf.union(row * n + col, row * n + col - 1);
            }
            if (col < n - 1 && grid[row][col + 1]) {
                top = top || topConnected[uf.find(row * n + col + 1)];
                bottom = bottom || bottomConnected[uf.find(row * n + col + 1)];
                uf.union(row * n + col, row * n + col + 1);
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
            topConnected[uf.find(row * n + col)] = top;
            bottomConnected[uf.find(row * n + col)] = bottom;

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > n || row < 1 || col > n || col < 1) {
            throw new IllegalArgumentException();
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > n || row < 1 || col > n || col < 1) {
            throw new IllegalArgumentException();
        }
        return topConnected[uf.find((row - 1) * n + (col - 1))];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolated;
    }

    // private void printState() {
    //     for (int i = 0; i < n; i++) {
    //         for (int j = 0; j < n; j++) {
    //             StdOut.print(grid[i][j] ? '1' : '0');
    //         }
    //         StdOut.println();
    //     }
    //     StdOut.println(String.format("%02d ", uf.find(n * n)));
    //     for (int i = 0; i < n; i++) {
    //         for (int j = 0; j < n; j++) {
    //             StdOut.print(String.format("%02d ", uf.find(i * n + j)));
    //         }
    //         StdOut.println();
    //     }
    //     StdOut.println(String.format("%02d ", uf.find(n * n + 1)));
    //     StdOut.println(percolates());
    // }

    // test client (optional)
    // public static void main(String[] args) {
    // }
}
