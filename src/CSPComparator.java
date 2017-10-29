import java.util.Comparator;
//Used as a comparator for Greedy heuristic
public class CSPComparator implements Comparator<CSP> {
    @Override
    public int compare(CSP node1, CSP node2) {
        return (node1.distance - node2.distance);
    }
}

