/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] thresholds;
    private final int trialCount;

    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }
        trialCount = trials;
        thresholds = new double[trials];
        for (int i = 0; i < trials; i++) {
            thresholds[i] = simulate(n);
        }
    }

    private double simulate(int n) {
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int row = StdRandom.uniform(n);
            int col = StdRandom.uniform(n);
            p.open(row + 1, col + 1);
        }
        return ((double) p.numberOfOpenSites()) / (n * n);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trialCount));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trialCount));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
                                                   Integer.parseInt(args[1]));
        StdOut.println(String.format("mean                    = %f", ps.mean()));
        StdOut.println(String.format("stddev                  = %f", ps.stddev()));
        StdOut.println(String.format("95%% confidence interval = [%f, %f]", ps.confidenceLo(),
                                     ps.confidenceHi()));

    }
}
