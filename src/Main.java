import java.io.FileNotFoundException;
import java.util.ArrayList;
//This class is used to pass in the parsed maze files into the search functions.
//Changing the arguments of the parse function will change which files are solved
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Parser s = new Parser();
        ArrayList<String> list = s.Parse("input55.txt");
        ArrayList<ArrayList<CSP>> assignment = new ArrayList();
        assignment = s.getCSP(list, 5);
        CSP current = assignment.get(0).get(0);
        Flow m = new Flow();
        assignment = m.BackTracking(assignment, 5, current);
        Character bleh;
        System.out.println();
        System.out.println();
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
               bleh = assignment.get(i).get(j).getValue();
               System.out.print(bleh);
            }
            System.out.println();
        }
//        ArrayList<String> list0 = s.Parse("mediumMaze.txt");
//        ArrayList<String> list1 = s.Parse("mediumMaze.txt");
//        ArrayList<String> list2 = s.Parse("mediumMaze.txt");
//        ArrayList<String> alist = s.Parse("mediumSearch.txt");
//        ArrayList<Integer> start = s.findP(list);
//        ArrayList<ArrayList<Integer>> end = s.findMultipleE(list);
//        ArrayList<Integer> astart = s.findP(alist);
//        ArrayList<ArrayList<Integer>> aend = s.findMultipleE(alist);
//        ArrayList<String> visited = new ArrayList<>(list);
//        ArrayList<String> visited0 = new ArrayList<>(list);
//        ArrayList<String> visited1 = new ArrayList<>(list);
//        ArrayList<String> visited2 = new ArrayList<>(list);
//        ArrayList<String> visiteda = new ArrayList<>(alist);

//        Search find = new Search();
//        find.unifiedSearch(list, start, visited );
//        find.unifiedDFSSearch(list2, start, visited2);
//        find.informedSearch(list0, start, visited0, end, 'a');
//        find.informedSearch(list1, start, visited1, end, 'g');
//        find.informedSearch(alist, astart, visiteda, aend, 'a');
    }

}
