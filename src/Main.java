import java.io.FileNotFoundException;
import java.util.ArrayList;
//This class is used to pass in the parsed maze files into the search functions.
//Changing the arguments of the parse function will change which files are solved
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Parser s = new Parser();
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
        ArrayList<String> rList = s.Parse("input77.txt");
        ArrayList<ArrayList<CSP>> rAssignment = s.getCSP(rList, 7);
        CSP current = m.findSource(rAssignment, 'R', 7); //// r for 7
        current.parent = true;
        rAssignment = m.recursive(rAssignment, 7, current.getDomain(), current, 0, 0);
        System.out.println();
        System.out.println();
        for(ArrayList<CSP> i : rAssignment){
            for(CSP j : i){
                System.out.print(j.getValue());
            }
            System.out.println();
        }
    }

}
