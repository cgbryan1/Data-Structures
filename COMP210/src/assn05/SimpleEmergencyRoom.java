package assn05;

import java.util.ArrayList;
import java.util.List;

public class SimpleEmergencyRoom {
    private List<Patient> patients;

    public SimpleEmergencyRoom() {
        patients = new ArrayList<>();
    }

    public Patient dequeue() {
        int index = 0;
        Patient highest = patients.get(0);
        for (int i = 1; i < patients.size(); i++) {
            if (patients.get(i).compareTo(highest) > 0) {
                highest = patients.get(i);
                index = i;
            }
        }
        patients.remove(index);
        return highest;
    }

    public void patientInfo() {
        for (int i = 0; i < patients.size() - 1; i++) {
            System.out.print(i + ". value: " + patients.get(i).getValue() + ", priority: " + patients.get(i).getPriority());
        }
    }

    public <V, P> void addPatient(V value, P priority) {
        Patient patient = new Patient(value, (Integer) priority);
        patients.add(patient);
    }

    public <V> void addPatient(V value) {
        Patient patient = new Patient(value);
        patients.add(patient);
    }

    public List getPatients() {
        return patients;
    }

    public int size() {
        return patients.size();
    }

}
