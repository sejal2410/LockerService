// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.


/*

objects: lo

functions:
1. find empty locker for given location
2. open locker with code
3. add lockers for given location for diff sizes
4. upgrade to new locker if given size compartment not found


Services:
 1. searchHubLocation
 2. allocationService
 3. notificationService
 3. Code generationService
 4. notification Service
 5.



 */

import java.time.LocalDate;
import java.util.*;
import java.util.random.RandomGenerator;


interface ISearchHubService{
    Hub searchNearbyHub(Location location);
}
class SearchHubService implements ISearchHubService{
    ArrayList<Hub> hubs;
    SearchHubService(ArrayList<Hub> hubs){
        this.hubs = hubs;
    }
   @Override
    public Hub searchNearbyHub(Location location){
        //logic to get nearby hubs;
       return null;
    }
}

interface ILockerManagementService{
    void emptyLockers();
}

interface PaymentManagmentService{

}

class LockerManagementService implements ILockerManagementService {

    HashMap<Locker, LockerOccupied> occupiedLockers;

    @Override
    public void emptyLockers() {
        for(Locker locker: occupiedLockers.keySet()){
            LockerOccupied occupied = occupiedLockers.get(locker);
            if(occupied.date.plusDays(occupied.noDays).isAfter(LocalDate.now())){
                occupied.locker.emptyLocker();
                occupiedLockers.remove(occupied.locker);
            }
        }
    }

}

interface IAllocationService{
    Locker assignLocker(LPackage pckge,Location location);
    void deallocate(Locker locker);
    LPackage openLocker(int code, Locker locker);
}

class AllocationService implements IAllocationService{
    ISearchHubService searchsrv;
    ICodeGenerationService codeGenerationService;
    HashMap<Locker, LockerOccupied> occupiedLockers;
    AllocationService(ISearchHubService srchSrvc, ICodeGenerationService codegenrator,HashMap<Locker, LockerOccupied> occupiedLockers){
        this.searchsrv = srchSrvc;
        this.codeGenerationService = codegenrator;
        this.occupiedLockers = occupiedLockers;
    }

    @Override
    public Locker assignLocker(LPackage pckge, Location location) {
        Hub hub = searchsrv.searchNearbyHub(location);
        if(hub!=null){
            for (Locker locker : hub.getLocker(pckge.size)) {
                if(locker.isEmpty()){
                    LockerOccupied occupied = new LockerOccupied();
                    occupied.code = codeGenerationService.generateCode();
                    occupied.lPackage = pckge;
                    occupied.locker = locker;
                    occupied.hub = hub;
                    occupied.noDays = 10;
                    occupiedLockers.put(locker,occupied);
                    locker.occupyLocker();
                }
            }

            return null;
        }
        return null;
    }

    @Override
    public void deallocate(Locker locker) {
        LockerOccupied occupied = occupiedLockers.get(locker);
        if(occupied==null)
            return ;
        locker.emptyLocker();
        occupiedLockers.remove(locker);
    }

    @Override
    public LPackage openLocker(int code, Locker locker) {
        if(!occupiedLockers.containsKey(locker) && occupiedLockers.get(locker).code!=code) return null;
        LPackage lPackage =  occupiedLockers.get(locker).lPackage;
        deallocate(locker);
        return lPackage;
    }
}
interface ICodeGenerationService{
    int generateCode();
}
class CodeGenerationService implements ICodeGenerationService{
    public int generateCode(){
        return 0;
    }
}


enum Size{
    SMALL,
    MEDIUM,
    LARGE
}
enum LockerStatus{
    OCCUPIED,
    EMPTY,
    NOT_FUNCTIONAL
}

//interface LockerState{
//
//    abstract void promoteState();
//    abstract boolean canPr
//}
//class EmptyLockerState implements LockerState{
//    Locker locker;
//    EmptyLockerState(Locker locker){
//        this.locker = locker;
//    }
//    public void promoteState(){
//        locker.setState(locker.getOccupiedState());
//    }
//}
//class OccupiedState implements LockerState{
//    Locker locker;
//    OccupiedState(Locker locker){
//        this.locker = locker;
//    }
//    public void promoteState(){
//        locker.setState(locker.getEmptyState());
//    }
//}

class Locker{
    int lockerId;
    int hubId;
    LockerStatus status;
    Size size;
    Locker(){
        status= LockerStatus.EMPTY;
        lockerId = RandomGenerator.getDefault().nextInt();
    }
    private void setEmpty(){
        this.status = LockerStatus.EMPTY;
    }
    boolean isEmpty(){
        return this.status==LockerStatus.EMPTY;
    }
    private void occupied(){
        this.status = LockerStatus.OCCUPIED;
    }
    void occupyLocker(){
        this.occupied();
    }
    void emptyLocker(){
        this.setEmpty();
    }

}
class SmallLocker extends Locker{
    SmallLocker(){
        this.size=Size.SMALL;
    }

}
class MediumLocker extends Locker{
    MediumLocker(){
        this.size=Size.MEDIUM;
    }
}
class LargeLocker extends Locker{
    LargeLocker(){
        this.size=Size.LARGE;
    }

}
class Location{
    String country;
    String city;
    String st1;
    String st2;
    int zipcode;
}
class Hub{
    int id;
    Location location;
    TreeMap<Size, ArrayDeque<Locker>> lockers;
    private HashMap<Size, Integer> maxSpots;

     void addLocker(Size size){

     }
     ArrayDeque<Locker> getLocker(Size size){
        return lockers.get(size);
     }
}
class LockerOccupied{
    Locker locker;
    LPackage lPackage;
    Hub hub;
    int noDays;
    LocalDate date;
    int code;
}

class LPackage{
    int id;
    int userId;
    Size size;
}


public class Main {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        // Press Shift+F10 or click the green arrow button in the gutter to run the code.
        for (int i = 1; i <= 5; i++) {

            // Press Shift+F9 to start debugging your code. We have set one breakpoint
            // for you, but you can always add more by pressing Ctrl+F8.
            System.out.println("i = " + i);
        }
    }
}