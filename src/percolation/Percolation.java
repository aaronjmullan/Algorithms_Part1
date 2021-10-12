/*
 * Name:        Aaron Mullan
 * Last Edited: 12/10/2021
 * Course:      Algorithms, Part I - https://www.coursera.org/learn/algorithms-part1
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int gridLength;
    private final int top;
    private final int bot;
    private boolean[] isOpen;
    private int openSites;
    private final WeightedQuickUnionUF grid;

    /*
     * Creates n-by-n isOpen, with all sites blocked
     * Creates n-by-n + 2 WeightedQuickUnionUF grid, +2 for virtual top/bottom nodes
     * 
     * @param n Dimension of the grid
     */ 
    public Percolation(int n) {

        final int arrayLength;
        
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        
        gridLength = n;
        arrayLength = (n * n) + 2;
        isOpen = new boolean[arrayLength];      
        top = 0;
        bot = (n * n) + 1;
        openSites = 0;
        isOpen[top] = true;
        isOpen[bot] = true;
        grid = new WeightedQuickUnionUF(arrayLength);
        
        for (int j = 1; j <= n; j++) {
            int i = 1;
            int topSiteIndex = siteIndex(i, j);
            grid.union(top, topSiteIndex);
            
            i = n;
            int botSiteIndex = siteIndex(i, j);
            grid.union(bot, botSiteIndex);         
        }   
    }

    public int numberOfOpenSites() {
        
        return openSites;      
    }
    
    private void checkRanges(int row, int col) {
        
        if (row < 1 || row > gridLength) {
            throw new IllegalArgumentException();
        }
        
        if (col < 1 || col > gridLength) {
            throw new IllegalArgumentException();
        }        
    }
       
    private int siteIndex(int row, int col) {
        
        checkRanges(row, col);
        int x = row;
        int y = col;
        
        return (y - 1) * gridLength + (x);      
    }
    
    
    public void open(int row, int col) {
        
        int siteIndex = siteIndex(row, col);
        
        if (!isOpen[siteIndex]) {
            isOpen[siteIndex] = true;
            openSites += 1;
        }
        
        if (col < gridLength && isOpen(row, col+1)) {
            int right = siteIndex(row, col+1);
            grid.union(siteIndex, right);
        }
        
        if (col > 1 && isOpen(row, col-1)) {
            int left = siteIndex(row, col-1);
            grid.union(siteIndex, left);
        }
        
        if (row > 1 && isOpen(row - 1, col)) {
            int topIndex = siteIndex(row - 1, col);
            grid.union(siteIndex, topIndex);
        }
        
        if (row < gridLength && isOpen(row + 1, col)) {
            int botIndex = siteIndex(row + 1, col);
            grid.union(siteIndex, botIndex);
        }
    }
    
    public boolean percolates() {
        
        if (gridLength > 1) {
            return grid.find(top) == grid.find(bot);
        } else {
            return isOpen[siteIndex(1, 1)];
        }
    }
    
    /*
     * A site that is full is an open site that can be joined to an open site in the top row
     * through neighbouring open sites (left, right, top, bottom).
     * 
     * @param row
     * @param col
     * @return boolean
     */
    public boolean isFull(int row, int col) {
        
        int siteIndex = siteIndex(row, col); 
        return (grid.find(top) == grid.find(siteIndex) && isOpen[siteIndex]);
    }
    
    public boolean isOpen(int row, int col) {
        
        int siteIndex = siteIndex(row, col);     
        return isOpen[siteIndex];
    }
    
    public static void main(String[] args) {
        
        Percolation per1 = new Percolation(1);
        System.out.println(per1.percolates());
        per1.open(1, 1);
        System.out.println(per1.percolates());
        
        Percolation per2 = new Percolation(2);
        System.out.println(per2.percolates());
        per2.open(1, 1);
        per2.open(2, 1);
        System.out.println(per2.percolates());  
    }
}
