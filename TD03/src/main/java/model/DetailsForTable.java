package model;

/**
 * It's a class that holds the details for a row in the table
 */
public class DetailsForTable {
    private String typeDeTri;
    private int taille;
    private long operations;
    private double durée;

    public DetailsForTable(String typeDeTri, int taille, long operations, double durée) {
        this.typeDeTri = typeDeTri;
        this.taille = taille;
        this.operations = operations;
        this.durée = durée;
    }

    /**
     * This function returns the type of sorting used
     *
     * @return The type of sorting.
     */
    public String getTypeDeTri() {
        return typeDeTri;
    }

    /**
     * > This function returns the size of the array
     *
     * @return The size of the array
     */
    public int getTaille() {
        return taille;
    }

    /**
     * This function returns the number of operations that have been performed on the calculator.
     *
     * @return The number of operations that have been performed.
     */
    public long getOperations() {
        return operations;
    }

    public double getDurée() {
        return durée;
    }
}
