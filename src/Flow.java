import java.lang.reflect.Array;
import java.util.*;

public class Flow {
    public Integer node;
    public Integer[] destination = new Integer[2];
    public ArrayList<ArrayList<CSP>> recursive(ArrayList<ArrayList<CSP>> assignment, Integer size, ArrayList<Character> domain, CSP prev, int k){
        if(complete(assignment)){
            return assignment;
        }
        System.out.println();
        System.out.println();
        for(ArrayList<CSP> i : assignment){
            for(CSP j : i){
                System.out.print(j.getValue());
            }
            System.out.println();
        }
        //prev.parent = true;
        //CSP current = getVariable(assignment);
        Comparator<CSP> comparator = new CSPComparator();
        PriorityQueue<CSP> vars;// = new PriorityQueue<CSP>(4, comparator);
        vars = getVars(assignment, prev);
        //ArrayList<CSP> vars = getVars(assignment, prev);
        boolean done = false;
        for(CSP current : vars ) {

            Integer x = current.x;
            Integer y = current.y;
            //for (Character value : current.getDomain()) {
            if(current.getValue() == '_') {
                if (nearInitial(assignment, current, prev.getValue()) && isValid(prev.getValue(), current, assignment, size) ) {
                    node++;
                    current.setValue(prev.getValue());
                    if (k < domain.size() - 1) {
                        CSP newPrev = findSource(assignment, domain.get(k), size);
                        CSP forMap = findDestination(assignment, domain.get(k), size);
                        Integer[] toInsert = new Integer[2];
                        toInsert[0] = forMap.x;
                        toInsert[1] = forMap.y;
                        destination = toInsert;
                        ArrayList<ArrayList<CSP>> result = recursive(assignment, size, domain, newPrev, k+1);
                        if(complete(result)){
                            return result;
                        }
                        else{
                            current.setValue('_');
                            current.subDomain(prev.getValue());
                        }
                    }
                }
                else if (isValid(prev.getValue(), current, assignment, size) ) {
                    node++;
                    current.setValue(prev.getValue());
                    ArrayList<ArrayList<CSP>> result = recursive(assignment, size, domain, current, k);
                    if (complete(result)) {
                        return result;
                    } else {
                        current.setValue('_');
                        current.subDomain(prev.getValue());
                    }
                }
//                  else if (nearInitial(assignment, current, prev.getValue())) {
//                    current.setValue(prev.getValue());
//                    if (k < domain.size() - 1) {
//                        k++;
//                        CSP newPrev = findSource(assignment, domain.get(k), size);
//                        ArrayList<ArrayList<CSP>> result = recursive(assignment, size, domain, newPrev, k);
//                        return result;
//                    }
//                }
            }
            //}
        }
//        ArrayList<ArrayList<CSP>> failure = assignment;
//        failure.get(0).get(0).setValue('_');
        return assignment;
    }
    boolean nearInitial(ArrayList<ArrayList<CSP>> assignment, CSP current, Character value){
        Integer x = current.x;
        Integer y = current.y;
        if(x-1 >= 0){
           if(assignment.get(x-1).get(y).initialValue && assignment.get(x-1).get(y).getValue() == value && !assignment.get(x-1).get(y).parent ){
               return true;
           }
        }
        if(y-1 >= 0){
            if(assignment.get(x).get(y-1).initialValue&& assignment.get(x).get(y-1).getValue() == value&& !assignment.get(x).get(y-1).parent){
                return true;
            }
        }
        if(x+1 < assignment.get(0).size()){
            if(assignment.get(x+1).get(y).initialValue&& assignment.get(x+1).get(y).getValue() == value&& !assignment.get(x+1).get(y).parent){
                return true;
            }
        }
        if(y+1 < assignment.get(0).size()){
            if(assignment.get(x).get(y+1).initialValue&& assignment.get(x).get(y+1).getValue() == value&& !assignment.get(x).get(y+1).parent){
                return true;
            }
        }
        return false;

    }
    public CSP getVariable(ArrayList<ArrayList<CSP>> assignment){
        //Integer minDomain = 1000;
        CSP retVal = assignment.get(0).get(0);
//        for(ArrayList<CSP> i : assignment){
//            for(CSP j : i){
//                if(j.getValue() != '_'){
//                    //minDomain = j.getDomain().size();
//                }
//            }
//        }
        return retVal;
    }
    public PriorityQueue<CSP> getVars(ArrayList<ArrayList<CSP>> assignment, CSP prev){
        Comparator<CSP> comparator = new CSPComparator();
        PriorityQueue<CSP> vars = new PriorityQueue<CSP>(4, comparator);
        Integer x = prev.x;
        Integer y = prev.y;
        Character value = prev.getValue();
        Integer[] length = destination;
        //ArrayList<CSP> retVal = new ArrayList<>();
        if(y+1 < assignment.get(0).size()){
            assignment.get(x).get(y+1).distance = Math.abs(x-length[0]) + Math.abs(y+1-length[1]);
            vars.add(assignment.get(x).get(y+1));
        }
        if(x+1 < assignment.get(0).size()){
            assignment.get(x+1).get(y).distance = Math.abs(x+1-length[0]) + Math.abs(y-length[1]);
            vars.add(assignment.get(x+1).get(y));
        }

        if(x-1 >= 0){
            assignment.get(x-1).get(y).distance = Math.abs(x-1-length[0]) + Math.abs(y-length[1]);
            vars.add(assignment.get(x-1).get(y));
        }
        if(y-1 >= 0){
            assignment.get(x).get(y-1).distance = Math.abs(x-length[0]) + Math.abs(y-1-length[1]);
            vars.add(assignment.get(x).get(y-1));
        }

        return vars;
    }


    public Character[][] Smart(ArrayList<ArrayList<CSP>> assignment, Integer size, ArrayList<Character> domain){
        Stack<CSP> frontier = new Stack<>();
        if(complete(assignment)){
            return assignment.get(0).get(0).charMap;
        }
        else{
            Integer nodes = 0;
            Integer i = 0;
            Character currValue = domain.get(i);
            CSP current = findSource( assignment, currValue, size);
            current.i = i;
            current.parent = true;
            Integer x = current.x;
            Integer y = current.y;
            Comparator<CSP> comparator = new CSPComparator();
            PriorityQueue<CSP> toInsert = new PriorityQueue<CSP>(size*size, comparator);
            if(x-1 >= 0){
                CSP left = assignment.get(x-1).get(y);
                left.i = i;
                if(isValid(currValue, left, assignment, size)){
                    nodes++;
                    left.distance = distance(left, size);
                    toInsert.add(left);
                    frontier.push(left);
                }
            }
            if(y-1 >= 0){
                CSP up = assignment.get(x).get(y-1);
                up.i = i;
                if(isValid(currValue, up, assignment, size)){
                    nodes++;
                    up.distance = distance(up, size);
                    toInsert.add(up);
                    frontier.push(up);
                }
            }
            if(x+1 < size){
                CSP right = assignment.get(x+1).get(y);
                right.i = i;
                if(isValid(currValue, right, assignment, size)){
                    nodes++;
                    right.distance = distance(right, size);
                    toInsert.add(right);
                   frontier.push(right);
                }
            }
            if(y+1 < size){
                CSP down = assignment.get(x).get(y+1);
                down.i = i;
                if(isValid(currValue, down, assignment, size)){
                    nodes++;
                    down.distance = distance(down, size);
                    toInsert.add(down);
                    frontier.push(down);
                }
            }
//            for(CSP curr : toInsert){
//                frontier.add(curr);
//            }
            toInsert.clear();
            boolean done = false;
            while (!frontier.empty()) {
                Character bleh;
                System.out.println();
                System.out.print(x);
                System.out.println(y);
                current = frontier.pop();
                currValue = domain.get(current.i);
                x = current.x;
                y = current.y;
                current.setMap(currValue);
                for(int j = 0; j < size; j++){
                    for(int k = 0; k < size; k++){
                        System.out.print(current.charMap[j][k]);
                    }
                    System.out.println();
                }
                System.out.println();
                current.visited.set(i, true);
                ArrayList<CSP> modAssignment = assignment.get(x);
                modAssignment.set(y, current);
                assignment.set(x, modAssignment);
                boolean u = false;
                boolean d = false;
                boolean r = false;
                boolean l = false;
                if (x - 1 >= 0) {
                    CSP left = assignment.get(x - 1).get(y);
                    left.i = current.i;

                    if (isNewValid(currValue, left, current.charMap, assignment, size) ) {
                        left.updateMap(current.charMap);
                        nodes++;
                        left.distance = distance(left, size);
                        toInsert.add(left);
                        if(!frontier.contains(left)) {
                            frontier.push(left);
                        }
                        l = true;
                    }
                    else if(left.initialValue && Character.toUpperCase(left.getValue()) == currValue && !left.parent){
                        left.setValue(Character.toUpperCase(left.getValue()));
                        done = true;
                    }
                    else{
                        left.setMapValue(x, y, '_');
                    }
                }
                if (y - 1 >= 0) {
                    CSP up = assignment.get(x).get(y - 1);
                    up.i = current.i;
                    if (isNewValid(currValue, up, current.charMap, assignment,size) ) {
                        up.updateMap(current.charMap);
                        nodes++;
                        up.distance = distance(up, size);
                        toInsert.add(up);
                        if(!frontier.contains(up)) {
                            frontier.push(up);
                        }
                        u = true;
                    }
                    else if(up.initialValue  && Character.toUpperCase(up.getValue()) == currValue &&!up.parent){
                        up.setValue(Character.toUpperCase(up.getValue()));
                        done = true;
                    }
                    else{
                        up.setMapValue(x, y, '_');
                    }
                }
                if (x + 1 < size) {
                    CSP right = assignment.get(x + 1).get(y);
                    right.i = current.i;
                    if ( isNewValid(currValue, right, current.charMap,assignment,size) ) {
                        Character[][] oldMap = right.getMap();
                        right.updateMap(current.charMap);
                        nodes++;
                        right.distance = distance(right, size);
                        toInsert.add(right);
                        if(!frontier.contains(right)) {
                            frontier.push(right);
                        }
                        r = true;
                    }
                    else if(right.initialValue && Character.toUpperCase(right.getValue()) == currValue &&!right.parent){
                        right.setValue(Character.toUpperCase(right.getValue()));
                        done = true;
                    }
                    else{
                        right.setMapValue(x, y, '_');
                    }
                }
                if (y + 1 < size) {
                    CSP down = assignment.get(x).get(y + 1);
                    down.i = current.i;
                    if (isNewValid(currValue, down, current.charMap,assignment,size) ) {
                        down.updateMap(current.charMap);
                        nodes++;
                        down.distance = distance(down, size);
                        toInsert.add(down);
                        frontier.push(down);
                        d = true;
                    }
                    else if(down.initialValue  && Character.toUpperCase(down.getValue()) == currValue &&!down.parent){
                        down.setValue(Character.toUpperCase(down.getValue()));
                        done = true;
                    }
                    else{
                        down.setMapValue(x, y, '_');
                    }
                }
//                for(CSP curr : toInsert){
//                    if(!frontier.contains(curr)) {
//                        frontier.add(curr);
//                    }
//                }
                toInsert.clear();
                if (!u && !d && !l && !r && !done) {
                    current.setMap('_');
                    current.visited.set(i, false);
                    modAssignment = assignment.get(x);
                    modAssignment.set(y, current);
                    assignment.set(x, modAssignment);
                }
                if (done) {
                    i = current.i;
                    if(i < domain.size()-1) {

                        i++;
                        CSP temp;
                        currValue = domain.get(i);
                        temp = findSource(assignment, currValue, size);
                        temp.i = i;
                        Integer tempx = temp.x;
                        Integer tempy = temp.y;
                        temp.updateMap(current.charMap);
                        temp.charMap[tempx][tempy] = temp.getValue();
                        temp.parent = true;
                        nodes++;
                        frontier.push(temp);
                        done = false;
                    }
                }
                if(isComplete(current.charMap)){
                    frontier.clear();
                }
            }
            current.upperCase();
            System.out.println(nodes);
            System.out.println();
            return current.charMap;
        }

    }
    public Integer distance(CSP current, Integer size){

        Integer x = (current.x - (size-1)/2);
        if(x < 0){
            x = x*(-1);
        }
        Integer y = (current.y - (size-1)/2);
        if(y < 0){
            y = y*(-1);
        }
        return x+y;
    }
    public Character[][] BackTracking(ArrayList<ArrayList<CSP>> assignment, Integer size, ArrayList<Character> domain){//(ArrayList<ArrayList<CSP>> assignmentIn, Integer sizeIn, ArrayList<Character> domainIn){
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
                return assignment.get(0).get(0).charMap;
            }

            //if(completedDomain(assignment)){
            //    temp = findSource();
            //}
//            else{
//                value = frontier.pop();
//                assignment.get(value.x).get(value.y).setValue(currDomain);
//                //assignment.get(value.x).get(value.y).setVisited(currDomain, true);
//
//                if(isValid(value.x - 1), value.y)){
//
//                }
//                else if(isValid(value.x + 1, value.y)){
//
//                }
//                else if (isValid(value.x, value.y - 1)){
//
//                }
//                else if (isValid(value.x, value.y+1)){
//
//                }
//                else{ assignment.get(value.x).get(value.y).setValue(clear); }
//
//            }
//        }

        else{
                Integer nodes = 0;
            Integer i = 0;
            Character currValue = domain.get(i);
            CSP current = findSource( assignment, currValue, size);
            current.i = i;
            current.parent = true;
            Integer x = current.x;
            Integer y = current.y;
            if(x-1 >= 0){
                CSP left = assignment.get(x-1).get(y);
                left.i = i;
                if(isValid(currValue, left, assignment, size)){
                    nodes++;
                    frontier.push(left);
                }
            }
            if(y-1 >= 0){
                CSP up = assignment.get(x).get(y-1);
                up.i = i;
                if(isValid(currValue, up, assignment, size)){
                    nodes++;
                    frontier.push(up);
                }
            }
            if(x+1 < size){
                CSP right = assignment.get(x+1).get(y);
                right.i = i;
                if(isValid(currValue, right, assignment, size)){
                    nodes++;
                    frontier.push(right);
                }
            }
            if(y+1 < size){
                CSP down = assignment.get(x).get(y+1);
                down.i = i;
                if(isValid(currValue, down, assignment, size)){
                    nodes++;
                    frontier.push(down);
                }
            }
            boolean done = false;
            //frontier.push(current);
            while (!frontier.empty()) {
                Character bleh;
//                for(int j = 0; j < 5; j++){
//                    for(int k = 0; k < 5; k++){
//                        System.out.print(current.charMap[j][k]);
//                    }
//                    System.out.println();
//                }
                updateDomain(assignment, domain, size);
                System.out.println();
                System.out.print(x);
                System.out.println(y);
                current = frontier.pop();
                currValue = domain.get(current.i);
                x = current.x;
                y = current.y;
                    current.setMap(currValue);
                    for(int j = 0; j < size; j++){
                        for(int k = 0; k < size; k++){
                            System.out.print(current.charMap[j][k]);
                        }
                        System.out.println();
                    }
                    System.out.println();
                    current.visited.set(i, true);
                    ArrayList<CSP> modAssignment = assignment.get(x);
                    modAssignment.set(y, current);
                    assignment.set(x, modAssignment);
                    boolean u = false;
                    boolean d = false;
                    boolean r = false;
                    boolean l = false;
                    if (x - 1 >= 0) {
                        CSP left = assignment.get(x - 1).get(y);
                        left.i = current.i;

                        if (isNewValid(currValue, left, current.charMap, assignment, size) ) {
                            left.updateMap(current.charMap);
                            nodes++;
                            frontier.push(left);
                            l = true;
                        }
                        else if(left.initialValue && Character.toUpperCase(left.getValue()) == currValue && !left.parent){
                            left.setValue(Character.toUpperCase(left.getValue()));
                            done = true;
                        }
                        else{
                            left.setMapValue(x, y, '_');
                        }
                    }
                    if (y - 1 >= 0) {
                        CSP up = assignment.get(x).get(y - 1);
                        up.i = current.i;
                        if (isNewValid(currValue, up, current.charMap, assignment,size) ) {
                            up.updateMap(current.charMap);
                            nodes++;
                            frontier.push(up);
                            u = true;
                        }
                        else if(up.initialValue  && Character.toUpperCase(up.getValue()) == currValue &&!up.parent){
                            up.setValue(Character.toUpperCase(up.getValue()));
                            done = true;
                        }
                        else{
                            up.setMapValue(x, y, '_');
                        }
                    }
                    if (x + 1 < size) {
                        CSP right = assignment.get(x + 1).get(y);
                        right.i = current.i;
                        if ( isNewValid(currValue, right, current.charMap,assignment,size) ) {
                            right.updateMap(current.charMap);
                            nodes++;
                            frontier.push(right);
                            r = true;
                        }
                        else if(right.initialValue && Character.toUpperCase(right.getValue()) == currValue &&!right.parent){
                            right.setValue(Character.toUpperCase(right.getValue()));
                            done = true;
                        }
                        else{
                            right.setMapValue(x, y, '_');
                        }
                    }
                    if (y + 1 < size) {
                        CSP down = assignment.get(x).get(y + 1);
                        down.i = current.i;
                        if (isNewValid(currValue, down, current.charMap,assignment,size) ) {
                            down.updateMap(current.charMap);
                            nodes++;
                            frontier.push(down);
                            d = true;
                        }
                        else if(down.initialValue  && Character.toUpperCase(down.getValue()) == currValue &&!down.parent){
                            down.setValue(Character.toUpperCase(down.getValue()));
                            done = true;
                        }
                        else{
                            down.setMapValue(x, y, '_');
                        }
                    }
                    if (!u && !d && !l && !r && !done) {
                        current.setMap('_');
                        current.visited.set(i, false);
                        modAssignment = assignment.get(x);
                        modAssignment.set(y, current);
                        assignment.set(x, modAssignment);
                    }
                if (done) {
                    i = current.i;
                    if(i < domain.size()-1) {

                        i++;
                        CSP temp;
                        currValue = domain.get(i);
                        temp = findSource(assignment, currValue, size);
                        temp.i = i;
                        Integer tempx = temp.x;
                        Integer tempy = temp.y;
                        temp.updateMap(current.charMap);
                        temp.charMap[tempx][tempy] = temp.getValue();
                        temp.parent = true;
                        nodes++;
                        frontier.push(temp);
                        done = false;
                    }
                }
                //}
                if(isComplete(current.charMap)){
                        frontier.clear();
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
             //   return assignment;        t
                current.upperCase();
            System.out.println(nodes);
            System.out.println();
                return current.charMap;
        }
    }
    public void updateDomain(ArrayList<ArrayList<CSP>> assignment, ArrayList<Character> domain, Integer size){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                CSP current = assignment.get(i).get(j);
                for(int k=0; k<domain.size(); k++){
                    if(isNewSubValid(domain.get(k), current, current.charMap, assignment, size) ){
                        current.addDomain(domain.get(k));
                    }
                    else{
                        current.subDomain(domain.get(k));
                    }
                }
            }
        }
    }
    public CSP findSource(ArrayList<ArrayList<CSP>> assignment, Character value, Integer size){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                Character currValue = assignment.get(i).get(j).getValue();
                if(currValue == value){
                    assignment.get(i).get(j).parent = true;
                    return assignment.get(i).get(j);
                }
            }
        }
        return assignment.get(size-1).get(size-1);
    }
    public CSP findDestination(ArrayList<ArrayList<CSP>> assignment, Character value, Integer size){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                Character currValue = assignment.get(i).get(j).getValue();
                if(currValue == value && !assignment.get(i).get(j).parent){
                    return assignment.get(i).get(j);
                }
            }
        }
        return assignment.get(size-1).get(size-1);
    }
    public boolean isComplete(Character[][] arg){
        for(Character[]i : arg){
            for(Character j : i){
                if(j == '_'){
                    return false;
                }
            }
        }
        return true;
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
   public boolean nearParent(Character value, CSP current, Character[][] charMap, ArrayList<ArrayList<CSP>> assignment, Integer size){
       Integer x = current.x;
       Integer y = current.y;
       CSP left = current;
       CSP right = current;
       CSP down = current;
       CSP up = current;
       boolean retVal = false;
       if(x-1 >= 0){
           left = assignment.get(x-1).get(y);
           if(left.initialValue && left.getValue() == value && !left.parent){
               retVal = true;
           }
       }
       if(y-1 >= 0){
           up = assignment.get(x).get(y-1);
           if(up.initialValue && up.getValue() == value && !up.parent){
               retVal = true;
           }
       }
       if(x+1 < size){
           right = assignment.get(x+1).get(y);
           if(right.initialValue && right.getValue() == value && !right.parent){
               retVal = true;
           }
       }
       if(y+1 < size){
           down = assignment.get(x).get(y+1);
           if(down.initialValue && down.getValue() == value && !down.parent){
               retVal = true;
           }
       }
       return retVal;

   }
    public boolean isNewValid(Character value, CSP current, Character[][] charMap, ArrayList<ArrayList<CSP>> assignment, Integer size){
       Integer x = current.x;
       Integer y = current.y;
       //boolean retVal = false;
       boolean lval = false;
       boolean rval = false;
       boolean uval = false;
       boolean dval = false;
       Character temp = charMap[x][y];
       if(temp != '_'){
           return false;
       }
       charMap[x][y] = value;
       if(x-1 >= 0){
           CSP left = assignment.get(x-1).get(y);
           if(charMap[x-1][y] == '_') {
               if(!isNewSubValid(value, left, charMap, assignment, size)){
                   left.subDomain(value);
                   if(left.getDomain().size() == 0){
                       lval = false;
                       left.addDomain(value);
                   }
                   else{
                       lval = true;
                   }
               }
               else{
                   lval = true;
               }
           }
           else{
               lval = true;
           }
       }
       else{
           lval = true;
       }
        if(y-1 >= 0){
            CSP up = assignment.get(x).get(y-1);
            if(charMap[x][y-1] == '_') {
                if(!isNewSubValid(value, up, charMap, assignment, size)){
                    up.subDomain(value);
                    if(up.getDomain().size() == 0){
                        uval = false;
                        up.addDomain(value);
                    }
                    else{
                        uval = true;
                    }
                }
                else{
                    uval = true;
                }
            }
            else{
                uval = true;
            }
        }
        else{
            uval = true;
        }
        if(x+1 < size){
            CSP right = assignment.get(x+1).get(y);
            if(charMap[x+1][y] == '_') {
                if(!isNewSubValid(value, right, charMap, assignment, size)){
                    right.subDomain(value);
                    if(right.getDomain().size() == 0){
                        rval = false;
                        right.addDomain(value);
                    }
                    else{
                        rval = true;
                    }
                }
                else{
                    rval = true;
                }
            }
            else{
                rval = true;
            }
        }
        else{
            rval = true;
        }
        if(y+1 < size){
            CSP down = assignment.get(x).get(y+1);
            if(charMap[x][y+1] == '_') {
                if(!isNewSubValid(value, down, charMap, assignment, size)){
                    down.subDomain(value);
                    if(down.getDomain().size() == 0){
                        dval = false;
                        down.addDomain(value);
                    }
                    else{
                        dval = true;
                    }
                }
                else{
                    dval = true;
                }
            }
            else{
                dval = true;
            }
        }
        else{
            dval = true;
        }
        charMap[x][y] = temp;
        if(temp == Character.toLowerCase(value)){
            rval = false;
        }
       return (rval & dval & uval & lval);
    }

    public boolean isNewSubValid(Character value, CSP current, Character[][] charMap, ArrayList<ArrayList<CSP>> assignment, Integer size) {
        boolean retVal = false;
        boolean r = false;
        boolean l = false;
        boolean u = false;
        boolean d = false;

        boolean rinit = false;
        boolean linit = false;
        boolean uinit = false;
        boolean dinit = false;


        CSP rc = current;
        CSP lc = current;
        CSP uc = current;
        CSP dc = current;
        Character right;
        Character left;
        Character up;
        Character down;
        right = current.getValue();
        left = current.getValue();
        up = current.getValue();
        down = current.getValue();

        //Character[][] charMap = current.charMap;


        Integer x = current.x;
        if(current.initialValue){
            return false;
        }
        Integer y = current.y;
        if (x - 1 >= 0) {
            left = charMap[x - 1][y];
            lc = assignment.get(x-1).get(y);
            if(lc.initialValue){
                linit = true;
            }
            l = true;
        }
        if (y - 1 >= 0) {
            up = charMap[x][y-1];
            uc = assignment.get(x).get(y-1);
            if(uc.initialValue){
                uinit = true;
            }
            u = true;
        }
        if (x + 1 < size) {
            right = charMap[x+1][y];
            rc = assignment.get(x+1).get(y);
            if(rc.initialValue){
                rinit = true;
            }
            r = true;
        }
        if (y + 1 < size) {
            down = charMap[x][y+1];
            dc = assignment.get(x).get(y+1);
            if(dc.initialValue){
                dinit = true;
            }
            d = true;
        }
        if(charMap[x][y] == '_') {
            if (l && r && u && d) {
                if (left == value && (right != value && up != value && down != value)) {
                    return true;
                } else if (right == value && (left != value && up != value && down != value)) {
                    return true;
                } else if (up == value && (right != value && left != value && down != value)) {
                    return true;
                } else if (down == value && (left != value && up != value && right != value)) {
                    return true;
                } else {
                    return false;
                }
            } else if (l && u && d) {
                if (left == value && (up != value && down != value)) {
                    return true;
                } else if (up == value && (left != value && down != value)) {
                    return true;
                } else if (down == value && (left != value && up != value)) {
                    return true;
                } else {
                    return false;
                }
            } else if (r && u && d) {
                if (right == value && (up != value && down != value)) {
                    return true;
                } else if (up == value && (right != value && down != value)) {
                    return true;
                } else if (down == value && (right != value && up != value)) {
                    return true;
                } else {
                    return false;
                }
            } else if (l && r && d) {
                if (left == value && (right != value && down != value)) {
                    return true;
                } else if (right == value && (left != value && down != value)) {
                    return true;
                } else if (down == value && (left != value && right != value)) {
                    return true;
                } else {
                    return false;
                }
            } else if (l && r && u) {
                if (left == value && (up != value && right != value)) {
                    return true;
                } else if (up == value && (left != value && right != value)) {
                    return true;
                } else if (right == value && (left != value && up != value)) {
                    return true;
                } else {
                    return false;
                }
            } else if (l && d) {
                if (left == value && (down != value)) {
                    return true;
                } else if (down == value && (left != value)) {
                    return true;
                } else {
                    return false;
                }
            } else if (l && u) {
                if (left == value && (up != value)) {
                    return true;
                } else if (up == value && (left != value)) {
                    return true;
                } else {
                    return false;
                }
            } else if (r && u) {
                if (right == value && (up != value)) {
                    return true;
                } else if (up == value && (right != value)) {
                    return true;
                } else {
                    return false;
                }
            } else if (r && d) {
                if (right == value && (down != value)) {
                    return true;
                } else if (down == value && (right != value)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return retVal;
    }
    public boolean preIsValid(Character value, CSP current, ArrayList<ArrayList<CSP>> assignment, Integer size){
        Integer x = current.x;
        Integer y = current.y;
        //boolean retVal = false;
        boolean lval = false;
        boolean rval = false;
        boolean uval = false;
        boolean dval = false;
        Character temp = assignment.get(x).get(y).getValue();
        if(temp != '_'){
            return false;
        }
        assignment.get(x).get(y).setValue(value);
        if(x-1 >= 0){
            CSP left = assignment.get(x-1).get(y);
            if(assignment.get(x-1).get(y).getValue() == '_') {
                if(!isValid(value, left, assignment, size)){
                    left.subDomain(value);
                    if(left.getDomain().size() == 0){
                        lval = false;
                        left.addDomain(value);
                    }
                    else{
                        lval = true;
                    }
                }
                else{
                    lval = true;
                }
            }
            else{
                lval = true;
            }
        }
        else{
            lval = true;
        }
        if(y-1 >= 0){
            CSP up = assignment.get(x).get(y-1);
            if(assignment.get(x).get(y-1).getValue() == '_') {
                if(!isValid(value, up, assignment, size)){
                    up.subDomain(value);
                    if(up.getDomain().size() == 0){
                        uval = false;
                        up.addDomain(value);
                    }
                    else{
                        uval = true;
                    }
                }
                else{
                    uval = true;
                }
            }
            else{
                uval = true;
            }
        }
        else{
            uval = true;
        }
        if(x+1 < size){
            CSP right = assignment.get(x+1).get(y);
            if(assignment.get(x+1).get(y).getValue() == '_') {
                if(!isValid(value, right, assignment, size)){
                    right.subDomain(value);
                    if(right.getDomain().size() == 0){
                        rval = false;
                        right.addDomain(value);
                    }
                    else{
                        rval = true;
                    }
                }
                else{
                    rval = true;
                }
            }
            else{
                rval = true;
            }
        }
        else{
            rval = true;
        }
        if(y+1 < size){
            CSP down = assignment.get(x).get(y+1);
            if(assignment.get(x).get(y+1).getValue() == '_') {
                if(!isValid(value, down, assignment, size)){
                    down.subDomain(value);
                    if(down.getDomain().size() == 0){
                        dval = false;
                        down.addDomain(value);
                    }
                    else{
                        dval = true;
                    }
                }
                else{
                    dval = true;
                }
            }
            else{
                dval = true;
            }
        }
        else{
            dval = true;
        }
        assignment.get(x-1).get(y).setValue(temp);
        if(temp == Character.toLowerCase(value)){
            rval = false;
        }
        return (rval & dval & uval & lval);
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



        Integer x = current.x;     if(current.initialValue){
            return false;
        }
        Integer y = current.y;
        if (x - 1 >= 0) {
            left = assignment.get(x - 1).get(y);
            if(left.getValue() == value && left.initialValue && !left.parent){
                left.setValue(Character.toLowerCase(left.getValue()));
            }
            l = true;
        }
        if (y - 1 >= 0) {
            up = assignment.get(x).get(y - 1);
            if(up.getValue() == value && up.initialValue && !up.parent){
                up.setValue(Character.toLowerCase(up.getValue()));
            }
            u = true;
        }
        if (x + 1 < size) {
            right = assignment.get(x + 1).get(y);
            if(right.getValue() == value && right.initialValue && !right.parent){
                right.setValue(Character.toLowerCase(right.getValue()));
            }
            r = true;
        }
        if (y + 1 < size) {
            down = assignment.get(x).get(y + 1);
            if(down.getValue() == value && down.initialValue && !down.parent){
                down.setValue(Character.toLowerCase(down.getValue()));
            }
            d = true;
        }

        if(l && r && u && d) {
            if(left.getValue() == value && (right.getValue() != value && up.getValue() != value && down.getValue() != value)) { retVal =  true; }
            else if(right.getValue() == value && (left.getValue() != value && up.getValue() != value && down.getValue() != value)) { retVal = true; }
            else if(up.getValue() == value && (right.getValue() != value && left.getValue() != value && down.getValue() != value)) { retVal = true; }
            else if(down.getValue() == value && (left.getValue() != value && up.getValue() != value && right.getValue() != value)) { retVal = true; }
            else{ retVal = false; }
        }
        else if(l && u && d) {
            if(left.getValue() == value && (up.getValue() != value && down.getValue() != value)) { retVal = true; }
            else if(up.getValue() == value && (left.getValue() != value && down.getValue() != value)){ retVal = true; }
            else if(down.getValue() == value && (left.getValue() != value && up.getValue() != value )){ retVal = true; }
            else{ retVal = false; }
        }
        else if(r && u && d) {
            if(right.getValue() == value && (up.getValue() != value && down.getValue() != value)) { retVal = true; }
            else if(up.getValue() == value && (right.getValue() != value && down.getValue() != value)) { retVal = true; }
            else if(down.getValue() == value && (right.getValue() != value && up.getValue() != value )){ retVal = true; }
            else{ retVal = false; }
        }
        else if(l && r && d) {
            if(left.getValue() == value && (right.getValue() != value && down.getValue() != value)) { retVal = true; }
            else if(right.getValue() == value && (left.getValue() != value && down.getValue() != value)) { retVal = true; }
            else if(down.getValue() == value && (left.getValue() != value && right.getValue() != value )){ retVal = true; }
            else{ retVal = false; }
        }
        else if(l && r && u) {
            if(left.getValue() == value && (up.getValue() != value && right.getValue() != value)) { retVal = true; }
            else if(up.getValue() == value && (left.getValue() != value && right.getValue() != value)) { retVal = true; }
            else if(right.getValue() == value && (left.getValue() != value && up.getValue() != value )){ retVal = true; }
            else{ retVal = false; }
        }
        else if(l && d) {
            if(left.getValue() == value && (down.getValue() != value)) { retVal = true; }
            else if(down.getValue() == value && (left.getValue() != value)){ retVal = true; }
            else{ retVal = false; }
        }
        else if(l && u) {
            if(left.getValue() == value && (up.getValue() != value)) { retVal = true; }
            else if(up.getValue() == value && (left.getValue() != value)){ retVal = true; }
            else{ retVal = false; }
        }
        else if(r && u) {
            if(right.getValue() == value && (up.getValue() != value)) { retVal = true; }
            else if(up.getValue() == value && (right.getValue() != value)){ retVal = true; }
            else{ retVal = false; }
        }
        else if(r && d) {
            if(right.getValue() == value && (down.getValue() != value)) { retVal = true; }
            else if(down.getValue() == value && (right.getValue() != value)){ retVal = true; }
            else{ retVal = false; }
        }
        up.setValue(Character.toUpperCase(up.getValue()));
        down.setValue(Character.toUpperCase(down.getValue()));
        right.setValue(Character.toUpperCase(right.getValue()));
        left.setValue(Character.toUpperCase(left.getValue()));
        return retVal;
    }
}
