import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

public class Flow {

    //ArrayList<ArrayList<CSP>> assignment = new ArrayList<ArrayList<CSP>>();
    //Integer size = new Integer();
    //ArrayList<Character> domain = new ArrayList<Character>();
    //Character currDomain = new Character();
    //Character clear = '_';

    public ArrayList<ArrayList<CSP>> BackTracking(ArrayList<ArrayList<CSP>> assignment, Integer size, ArrayList<Character> domain){//(ArrayList<ArrayList<CSP>> assignmentIn, Integer sizeIn, ArrayList<Character> domainIn){
        //initialize
        Stack<CSP> frontier = new Stack<>();
        //assignment = assignmentIn;
        //size = sizeIn;
        //domain = domainIn;
        CSP value = new CSP();
        //value = findSource();


        //loop
       // while(!frontier.isEmpty()){
            if(complete(assignment)){
                return assignment;
            }

            //if(completedDomain(assignment)){
            //    temp = findSource();
            //}
            /*else{
                value = frontier.pop();
                assignment.get(value.x).get(value.y).setValue(currDomain);
                //assignment.get(value.x).get(value.y).setVisited(currDomain, true);

                if(isValid(value.x - 1), value.y)){

                }
                else if(isValid(value.x + 1, value.y)){

                }
                else if (isValid(value.x, value.y - 1)){

                }
                else if (isValid(value.x, value.y+1)){

                }
                else{ assignment.get(value.x).get(value.y).setValue(clear); }

            }
        }*/

        else{
            Integer i = 0;
            Character currValue = domain.get(i);
            CSP current = findSource( assignment, currValue, size);
            Integer x = current.x;
            Integer y = current.y;
            if(x-1 >= 0){
                CSP left = assignment.get(x-1).get(y);
                if(isValid(currValue, left, assignment, size)){
                    frontier.push(left);
                }
            }
            if(y-1 >= 0){
                CSP up = assignment.get(x).get(y-1);
                if(isValid(currValue, up, assignment, size)){
                    frontier.push(up);
                }
            }
            if(x+1 < size){
                CSP right = assignment.get(x+1).get(y);
                if(isValid(currValue, right, assignment, size)){
                    frontier.push(right);
                }
            }
            if(y+1 < size){
                CSP down = assignment.get(x).get(y+1);
                if(isValid(currValue, down, assignment, size)){
                    frontier.push(down);
                }
            }
            //frontier.push(current);
            while (!frontier.empty()) {
                current = frontier.pop();
                x = current.x;
                y = current.y;
                current.setValue(currValue);
                current.visited.set(i, true);
                ArrayList<CSP> modAssignment = assignment.get(x);
                modAssignment.set(y, current);
                assignment.set(x, modAssignment);
                boolean u = false;
                boolean d = false;
                boolean r = false;
                boolean l = false;
                if(x-1 >= 0){
                    CSP left = assignment.get(x-1).get(y);
                    if(isValid(currValue, left, assignment, size)){
                        frontier.push(left);
                        l = true;
                    }
                }
                if(y-1 >= 0){
                    CSP up = assignment.get(x).get(y-1);
                    if(isValid(currValue, up, assignment, size)){
                        frontier.push(up);
                        u = true;
                    }
                }
                if(x+1 < size){
                    CSP right = assignment.get(x+1).get(y);
                    if(isValid(currValue, right, assignment, size)){
                        frontier.push(right);
                        r = true;
                    }
                }
                if(y+1 < size){
                    CSP down = assignment.get(x).get(y+1);
                    if(isValid(currValue, down, assignment, size)){
                        frontier.push(down);
                        d = true;
                    }
                }
                if(!u && !d && !l && !r){
                    current.setValue('_');
                    modAssignment = assignment.get(x);
                    modAssignment.set(y, current);
                    assignment.set(x, modAssignment);
                }
            }
//            //CSP current = selectVariable(assignment);
//            Integer x = current.x;
//            Integer y = current.y;
//            for(Character value : current.getDomain()){
//                if(isValid(value, current, assignment, size)) {
//                    ArrayList<CSP> mod = assignment.get(x);
//                    CSP update = current;
//                    update.setValue(value);
//                    mod.set(y, update);
//                    assignment.set(x, mod);
//                    ArrayList<ArrayList<CSP>> newAssignment;
//                    CSP newCurrent = current;
//                    if(y-1 >= 0){
//                        newCurrent = assignment.get(x).get(y-1);
//                    }
//                    else if(y+1 < size){
//                        newCurrent = assignment.get(x).get(y+1);
//                    }
//                    else if(x-1 >= 0){
//                        newCurrent = assignment.get(x-1).get(y);
//                    }
//                    else if(x+1 < size){
//                        newCurrent = assignment.get(x+1).get(y);
//                    }
//                    newAssignment = BackTracking(assignment, size, newCurrent);
//                    if(complete(newAssignment)){
//                        return newAssignment;
//                    }
//                    mod.set(y, current);
//                    assignment.set(x, mod);
//                }
//            }
             //   return assignment;
            return assignment;
        }
    }
    public CSP findSource(ArrayList<ArrayList<CSP>> assignment, Character value, Integer size){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                Character currValue = assignment.get(i).get(j).getValue();
                if(currValue == value){
                    return assignment.get(i).get(j);
                }
            }
        }
        return assignment.get(size-1).get(size-1);
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

    public boolean isInBounds(Integer x, Integer y){
        Boolean retVal = true;
        //if( x < 0 || y < 0 || x >= size || y >= size){
          //  retVal = false;
        //}
        return retVal;
    }

    public boolean isOpen(CSP value){
        if (value.getValue() == '_') {
            //if (value.Visited(currDomain) == false) {
                return true;
            //}
        }
        return false;
    }

   /* public boolean isValid(Integer x, Integer y){
        if(isInBounds(x, y)){
            if(isOpen(assignment.get(x).get(y))){ return true;}
        }
        return false;
    }*/

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
        if (x + 1 < size) {
            right = assignment.get(x + 1).get(y);
            r = true;
        }
        if (y + 1 < size) {
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
