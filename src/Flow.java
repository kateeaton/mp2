import java.util.ArrayList;
import java.util.Stack;

public class Flow {
    public ArrayList<ArrayList<CSP>> BackTracking(ArrayList<ArrayList<CSP>> assignment, Integer size, CSP current){
        if(complete(assignment)){
            return assignment;
        }
        else{
            //CSP current = selectVariable(assignment);
            Integer x = current.x;
            Integer y = current.y;
            for(Character value : current.getDomain()){
                if(isValid(value, current, assignment, size)) {
                    ArrayList<CSP> mod = assignment.get(x);
                    CSP update = current;
                    update.setValue(value);
                    mod.set(y, update);
                    assignment.set(x, mod);
                    ArrayList<ArrayList<CSP>> newAssignment;
                    CSP newCurrent = current;
                    if(y-1 >= 0){
                        newCurrent = assignment.get(x).get(y-1);
                    }
                    else if(y+1 < size){
                        newCurrent = assignment.get(x).get(y+1);
                    }
                    else if(x-1 >= 0){
                        newCurrent = assignment.get(x-1).get(y);
                    }
                    else if(x+1 < size){
                        newCurrent = assignment.get(x+1).get(y);
                    }
                    newAssignment = BackTracking(assignment, size, newCurrent);
                    if(complete(newAssignment)){
                        return newAssignment;
                    }
                    mod.set(y, current);
                    assignment.set(x, mod);
                }
            }
            return assignment;
            }
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


    public boolean isValid(Character value, CSP current, ArrayList<ArrayList<CSP>> assignment, Integer size) {
        boolean retVal = true;
        boolean r = false;
        boolean l = false;
        boolean u = false;
        boolean d = false;
        CSP right = new CSP();
        CSP left = new CSP();
        CSP up = new CSP();
        CSP down = new CSP();
        right = current;
        left = current;
        up = current;
        down = current;

        Integer x = current.x;
        Integer y = current.y;
        if (x - 1 >= 0) {
            left = assignment.get(x - 1).get(y);
            l = true;
        }
        if (y - 1 >= 0) {
            up = assignment.get(x).get(y - 1);
            u = true;
        }
        if (x + 1 <= size) {
            right = assignment.get(x + 1).get(y);
            r = true;
        }
        if (y + 1 <= size) {
            down = assignment.get(x).get(y + 1);
            d = true;
        }

        if(l && r && u && d) {
            if(left.getValue() == value && (right.getValue() != value && up.getValue() != value && down.getValue() != value)) { return true; }
            else if(right.getValue() == value && (left.getValue() != value && up.getValue() != value && down.getValue() != value)) { return true; }
            else if(up.getValue() == value && (right.getValue() != value && left.getValue() != value && down.getValue() != value)) { return true; }
            else if(down.getValue() == value && (left.getValue() != value && up.getValue() != value && right.getValue() != value)) { return true; }
            else{ return false; }
        }
        else if(l && u && d) {
            if(left.getValue() == value && (up.getValue() != value && down.getValue() != value)) { return true; }
            else if(up.getValue() == value && (left.getValue() != value && down.getValue() != value)){ return true; }
            else if(down.getValue() == value && (left.getValue() != value && up.getValue() != value )){ return true; }
            else{ return false; }
        }
        else if(r && u && d) {
            if(right.getValue() == value && (up.getValue() != value && down.getValue() != value)) { return true; }
            else if(up.getValue() == value && (right.getValue() != value && down.getValue() != value)) { return true; }
            else if(down.getValue() == value && (right.getValue() != value && up.getValue() != value )){ return true; }
            else{ return false; }
        }
        else if(l && r && d) {
            if(left.getValue() == value && (right.getValue() != value && down.getValue() != value)) { return true; }
            else if(right.getValue() == value && (left.getValue() != value && down.getValue() != value)) { return true; }
            else if(down.getValue() == value && (left.getValue() != value && right.getValue() != value )){ return true; }
            else{ return false; }
        }
        else if(l && r && u) {
            if(left.getValue() == value && (up.getValue() != value && right.getValue() != value)) { return true; }
            else if(up.getValue() == value && (left.getValue() != value && right.getValue() != value)) { return true; }
            else if(right.getValue() == value && (left.getValue() != value && up.getValue() != value )){ return true; }
            else{ return false; }
        }
        else if(l && d) {
            if(left.getValue() == value && (down.getValue() != value)) { return true; }
            else if(down.getValue() == value && (left.getValue() != value)){ return true; }
            else{ return false; }
        }
        else if(l && u) {
            if(left.getValue() == value && (up.getValue() != value)) { return true; }
            else if(up.getValue() == value && (left.getValue() != value)){ return true; }
            else{ return false; }
        }
        else if(r && u) {
            if(right.getValue() == value && (up.getValue() != value)) { return true; }
            else if(up.getValue() == value && (right.getValue() != value)){ return true; }
            else{ return false; }
        }
        else if(r && d) {
            if(right.getValue() == value && (down.getValue() != value)) { return true; }
            else if(down.getValue() == value && (right.getValue() != value)){ return true; }
            else{ return false; }
        }
        return retVal;
    }

}
