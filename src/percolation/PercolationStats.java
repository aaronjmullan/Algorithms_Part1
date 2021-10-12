/*
 * Name:        Aaron Mullan
 * Last Edited: 12/10/2021
 * Course:      Algorithms, Part I - https://www.coursera.org/learn/algorithms-part1
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double STD_MULT = 1.96;
    private final int size;
    private final int trials;
    private double[] stats;

    public PercolationStats(int n, int trials) {
        
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }
        
        this.size = n;
        this.trials = trials;
        stats = new double[trials];
        displayStats();    
    }
    
    public double mean() {
        
        return StdStats.mean(stats);
    }
    
    public double stddev() {
        
        return StdStats.stddev(stats);
    }
    
    // low point of confidence interval
    public double confidenceLo() {
        
        return mean() - (STD_MULT * stddev() / Math.sqrt(size));
    }
    
    // high point of confidence interval
    public double confidenceHi() {
        
        return mean() + (STD_MULT * stddev() / Math.sqrt(size));
    }
    
    
    private void displayStats() {
        
        Percolation perco;
        
        for (int i = 0; i < trials; i++) {
            perco = new Percolation(size);
            while (!perco.percolates()) {
                perco.open(StdRandom.uniform(1, size + 1), StdRandom.uniform(1, size + 1));
            }
            stats[i] = (double) perco.numberOfOpenSites() / (size * size);
        }   
    }
    
    public static void main(String[] args) {
        
        PercolationStats percoStats = new PercolationStats(Integer.parseInt(args[0]), 
                                                            Integer.parseInt(args[1]));
        
        System.out.println(" mean =  " + percoStats.mean());
        System.out.println(" sttdev = " + percoStats.stddev());
        System.out.println(" 95% confidence interval = " + percoStats.confidenceLo()
                + percoStats.confidenceHi());
    }
}
