package assn05;

public class Main {

    public static void main(String[] args) {
        // testP1();
        // testP2();
        // testP3();
        testP4();
    }

    public static void testP1() {

        SimpleEmergencyRoom test = new SimpleEmergencyRoom();
        for (int i = 0; i < 5; i++) {
            test.addPatient(i);
        }

        test.patientInfo();
        System.out.println();
        System.out.println("After removing value: ");
        test.patientInfo();
    }

    public static void testP2() {
        MaxBinHeapER test = new MaxBinHeapER();

        // test enqueue with and without priority
        // test dequeue
        // try getMax

        test.enqueue(5, 50);
        test.enqueue(4, 40);
        test.enqueue(3, 30);
        System.out.println(test.dequeue());

    }

    public static void testP3() {
        MaxBinHeapER transfer = new MaxBinHeapER(makePatients());
        Prioritized[] arr = transfer.getAsArray();
        for (int i = 0; i < transfer.size(); i++) {
            System.out.println("Value: " + arr[i].getValue()
                    + ", Priority: " + arr[i].getPriority());
        }
    }

    public static void testP4() {
    }

    public static void fillER(MaxBinHeapER complexER) {
        for (int i = 0; i < 100000; i++) {
            complexER.enqueue(i);
        }
    }

    public static void fillER(SimpleEmergencyRoom simpleER) {
        for (int i = 0; i < 100000; i++) {
            simpleER.addPatient(i);
        }
    }

    public static Patient[] makePatients() {
        Patient[] patients = new Patient[10];
        for (int i = 0; i < 10; i++) {
            patients[i] = new Patient(i);
        }
        return patients;
    }

    public static double[] compareRuntimes() {
        // Array which you will populate as part of Part 4
        double[] results = new double[4];

        // create a simple ER w 10k patients:
        SimpleEmergencyRoom simplePQ = new SimpleEmergencyRoom();
        fillER(simplePQ);

        // Code for (Task 4.1) Here
        double start = System.nanoTime(); // time it
        for (int i = 0; i < 100000; i++) {
            simplePQ.dequeue(); // dq everything
        }
        double end = System.nanoTime();
        results[0] = end - start;

        results[1] = (end - start) / 100000; // save difference in time


        // create maxheap with 10k patients:
        MaxBinHeapER binHeap = new MaxBinHeapER();
        fillER(binHeap);

        // Code for (Task 4.2) Here
        start = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            binHeap.dequeue(); // dequeue all 10k patients
        }
        end = System.nanoTime();
        results[2] = end - start;

        results[3] = (end - start) / 100000;

        return results;
    }

}
