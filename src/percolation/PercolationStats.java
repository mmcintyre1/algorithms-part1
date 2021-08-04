package src.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] probabilities;
    private final int numberOfTrials;

    public PercolationStats(int n, int trials) {
        probabilities = new double[trials];
        numberOfTrials = trials;

        for (int i = 0; i < trials; i++) {
            Percolation percolator = new Percolation(n);
            while (!percolator.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolator.open(row, col);
            }
            probabilities[i] = (double) percolator.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(probabilities);
    }

    public double stddev() {
        return StdStats.stddev(probabilities);
    }

    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(numberOfTrials));
    }

    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(numberOfTrials));
    }

    public static void main(String[] args) {
        int gridSize = Integer.parseInt(args[0]);
        int numberOfTrials = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(gridSize, numberOfTrials);

        String confidence = "[" + percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]";
        StdOut.println("mean                    = " + percStats.mean());
        StdOut.println("stddev                  = " + percStats.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
