import java.util.ArrayList;
import java.util.Stack;

public class Flow {
    public ArrayList<ArrayList<CSP>> BackTracking(ArrayList<ArrayList<CSP>> assignment, Integer size){
        if(complete(assignment)){
            return assignment;
        }
        else{
//            Stack<CSP> frontier = new Stack<>();
            CSP current = selectVariable(assignment);
            for(Character value : current.getDomain()){
            }
//            frontier.add(current);
//            while(!frontier.empty()){
//                CSP right;
//                CSP left;
//                CSP up;
//                CSP down;
//                Integer x = current.x;
//                Integer y = current.y;
//                if(x-1 > 0 && assignment.get(x-1).get(y).getValue() == '_'){
//                    left = assignment.get(x-1).get(y);
//                    frontier.add(left);
//                }
//                if(y-1 > 0 && assignment.get(x).get(y-1).getValue() == '_'){
//                    up = assignment.get(x).get(y-1);
//                    frontier.add(up);
//                }
//                if(x+1 < size && assignment.get(x+1).get(y).getValue() == '_'){
//                    right = assignment.get(x+1).get(y);
//                    frontier.add(right);
//                }
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
        CSP right = current;
        CSP left = current;
        CSP up = current;
        CSP down = current;
        Integer x = current.x;
        Integer y = current.y;
        if (x - 1 > 0) {
            left = assignment.get(x - 1).get(y);
            l = true;
        }
        if (y - 1 > 0) {
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
            if(left.getValue() == value && (right.getValue() != value && up.getValue() != value && down.getValue() != value)) {
                return true;
            }
            else if(right.getValue() == value && (left.getValue() != value && up.getValue() != value && down.getValue() != value)) {
                return true;
            }
            else if(up.getValue() == value && (right.getValue() != value && left.getValue() != value && down.getValue() != value)) {
                return true;
            }
            else if(down.getValue() == value && (left.getValue() != value && up.getValue() != value && right.getValue() != value)) {
                return true;
            }
            else{
                return false;
            }

        }
        else if( l && u && d) {
            if(left.getValue() == value && (up.getValue() != value && down.getValue() != value)) {
                return true;
            }
            else if(up.getValue() == value && (left.getValue() != value && down.getValue() != value)) {
                return true;
            }
            else if(down.getValue() == value && (left.getValue() != value && up.getValue() != value )){
                return true;
            }
            else{
                return false;
            }
        }
        else if(r && u && d) {
            if(right.getValue() == value && (up.getValue() != value && down.getValue() != value)) {
                return true;
            }
            else if(up.getValue() == value && (right.getValue() != value && down.getValue() != value)) {
                return true;
            }
            else if(down.getValue() == value && (right.getValue() != value && up.getValue() != value )){
                return true;
            }
            else{
                return false;
            }
        }
        else if(l && r && d) {
            if(left.getValue() == value && (right.getValue() != value && down.getValue() != value)) {
                return true;
            }
            else if(right.getValue() == value && (left.getValue() != value && down.getValue() != value)) {
                return true;
            }
            else if(down.getValue() == value && (left.getValue() != value && right.getValue() != value )){
                return true;
            }
            else{
                return false;
            }
        }
        else if(l && r && u) {
            if(left.getValue() == value && (up.getValue() != value && right.getValue() != value)) {
                return true;
            }
            else if(up.getValue() == value && (left.getValue() != value && right.getValue() != value)) {
                return true;
            }
            else if(right.getValue() == value && (left.getValue() != value && up.getValue() != value )){
                return true;
            }
            else{
                return false;
            }
        }
        else if(l && d) {
            if(left.getValue() == value && (down.getValue() != value)) {
                return true;
            }
            else if(down.getValue() == value && (left.getValue() != value)){
                return true;
            }
            else{
                return false;
            }
        }
        else if(l && u) {
            if(left.getValue() == value && (up.getValue() != value)) {
                return true;
            }
            else if(up.getValue() == value && (left.getValue() != value)){
                return true;
            }
            else{
                return false;
            }
        }
        else if(r && u) {
            if(right.getValue() == value && (up.getValue() != value)) {
                return true;
            }
            else if(up.getValue() == value && (right.getValue() != value)){
                return true;
            }
            else{
                return false;
            }
        }
        else if(r && d) {
            if(right.getValue() == value && (down.getValue() != value)) {
                return true;
            }
            else if(down.getValue() == value && (right.getValue() != value)){
                return true;
            }
            else{
                return false;
            }
        }
        return retVal;
    }

}
