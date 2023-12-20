package model.out.progress;

/**
 * progress bar:
 * Displays the percentage of tasks executed.
 */
public class ProgressBar {
    private double total;    // total number of tasks
    private double barLength;// length of the progress bar

    /**
     * Initializes a progress bar given the number
     * of tasks and the length of the progress bar
     *
     * @param total  number of tasks
     * @param barLen length of the progress bar
     */
    public ProgressBar(double total, int barLen) {
        this.total = total;
        this.barLength = barLen;
    }


    /**
     * Try updating the progress bar status based
     * on the number of tasks completed
     *
     * @param completed the number of tasks completed
     */
    public void update(double completed) {
        double percentage = completed / this.total;
        int barSign = (int) (percentage * this.barLength);
        // generate progress bar
        System.out.print("\r" + generateProgressBar(barSign) + String.format(" %.2f%%", percentage * 100));
    }

    /**
     * Gets a string representation of the current progress bar
     *
     * @param barSign Progress bar Indicates the number of progress symbols
     * @return a string of current progress bar
     */
    private String generateProgressBar(int barSign) {
        StringBuilder bar = new StringBuilder();

        bar.append("[");
        for (int i = 1; i <= this.barLength; i++) {
            if (i < barSign) {
                bar.append("-");
            } else if (i == barSign) {
                bar.append(">");
            } else {
                bar.append("");
            }
        }
        bar.append("]");
        return bar.toString();
    }
}
