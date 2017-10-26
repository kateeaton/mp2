import java.util.ArrayList;
import java.util.Stack;

public class Flow {
    public ArrayList<ArrayList<CSP>> BackTracking(ArrayList<ArrayList<CSP>> assignment){
        if(complete(assignment)){
            return assignment;
        }
        else{
            Stack<CSP> frontier = new Stack<>();
            CSP current = selectVariable(assignment);
            frontier.add(current);
            while(!frontier.empty()){

            }
        }
        return assignment;
    }
    public boolean complete(ArrayList<ArrayList<CSP>> arg) {
        for(ArrayList<CSP> i : arg){
            for(int j=0; j< i.size();j++ ){
                if(i.get(j).getValue() == '_'){
                    return false;
                }
            }
        }
        return true;
    }
    public CSP selectVariable(ArrayList<ArrayList<CSP>> assignment){
        for(ArrayList<CSP> i : assignment){
            for(CSP j : i){
                if(j.getValue() != '_'){
                    return j;
                }
            }
        }
        return assignment.get(0).get(0);
    }

}
