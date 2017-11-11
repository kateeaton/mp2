import java.io.FileNotFoundException;
import java.util.ArrayList;
//This class is used to pass in the parsed maze files into the search functions.
//Changing the arguments of the parse function will change which files are solved
/*public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Parser s = new Parser();
        //Commented out code used for 'dumb' implementation
//        ArrayList<String> list = s.Parse("input77.txt");
//        Integer size = 7;
//        ArrayList<ArrayList<CSP>> assignment = new ArrayList();
//        Character[][] result;
//        assignment = s.getCSP(list, size);
//        CSP current = assignment.get(0).get(1);
//        ArrayList<Character> domain = new ArrayList<>();
//        for(Character k : current.getDomain()){
//            domain.add(k);
//        }
        Flow m = new Flow();
//        result = m.Smart(assignment, size, domain);
//        Character bleh;
//        System.out.println();
//        System.out.println();
//        for(int i = 0; i < size; i++){
//            for(int j = 0; j < size; j++){
//               System.out.print(result[i][j]);
//            }
//            System.out.println();
//        }

        //Parse the file and set up the assignment to use for the CSP solver
        Integer size = 7;
        ArrayList<String> rList = s.Parse("input77.txt");
        ArrayList<ArrayList<CSP>> rAssignment = s.getCSP(rList, size);
        CSP current = m.findSource(rAssignment, 'R', size);
        CSP dest = m.findSource(rAssignment, 'R', size);
        current.parent = true;
        m.node = 0;
        Integer[] toInsert = new Integer[2];
        toInsert[0] = dest.x;
        toInsert[1] = dest.y;
        m.destination = toInsert;

        //time the execution
        final long startTime = System.currentTimeMillis();
        rAssignment = m.recursive(rAssignment, size, current.getDomain(), current, 0);
        final long endTime = System.currentTimeMillis();

        //print the results
        System.out.println();
        System.out.println(m.node);
        System.out.println("Total execution time: " + (endTime - startTime) );
        System.out.println();
        for(ArrayList<CSP> i : rAssignment){
            for(CSP j : i){
                System.out.print(j.getValue());
            }
            System.out.println();
        }
    }

}
*/