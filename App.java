import java.util.*;
import java.io.*; 

class Patient{ 
	String name;
	Doctor doctor;
	boolean hasDoctor = false;
	
	public Patient(){
		
	}
	
	public Patient(String name){
		this.name = name;
	}
	
	public Patient(String name, Doctor doctor){
		this.name = name;
		this.doctor = doctor;
		hasDoctor = true;
		doctor.addPatient(this);
	}
	
	public void setDoctor(Doctor doctor){
		if(!hasDoctor){
			this.doctor = doctor;
			hasDoctor = true;
			doctor.addPatient(this);
			System.out.println("Doctor "+doctor.name + " has been set for patient "+ name);
		}
		else System.out.println("This patient ("+ name +") already has a doctor which is "+this.doctor.name);
	}
	
	public void changeDoctor(Doctor doctor){
		if(hasDoctor){
			this.doctor.deletePatient(this);
			this.doctor = doctor;
			doctor.addPatient(this);
		} else System.out.println("This patient (" + name + ") doesn't have a doctor");
		
	}
	
	public void deleteDoctor(){
		if(hasDoctor){
			doctor.deletePatient(this); 
			this.doctor = null;
			hasDoctor = false;
		} 
		else System.out.println("Patient " + name + " doesn't have a doctor");
	}
	
	public void showDoctor(){
		if(hasDoctor)
			System.out.println("Doctor of patient " + name + " is "+doctor.name);
		else System.out.println("This patient ("+ name +") doesn't have a doctor");
	}
	
	public void info(){
		System.out.println("Name of patients: " + name);
		if(hasDoctor)System.out.println("He's patient of doctor " + doctor.name);
		else System.out.println("He doesn't have a doctor");
	}
}


class Doctor{ 
	String name;
	Patient patient;
	ArrayList<Patient> listOfPatients;
	boolean hasPatients = false;
	public Doctor(){
		
	}
	public Doctor(String name){
		this.name = name;
		listOfPatients = new ArrayList<Patient>();
	}
	public Doctor(String name, Patient patient){
		patient.doctor = this;
		this.name = name;
		this.patient = patient;
		listOfPatients = new ArrayList<Patient>();
		addPatient(patient);
	}
	public void info(){
		System.out.println("Name of doctor: " + name);
		if(hasPatients){
			System.out.println("List of patients: ");
			showListOfPatients();
		} else System.out.println("List of patients is empty");
	}
	
	public void addPatient(Patient patient){
		boolean hasThisPatient = false;
		for (Patient p : listOfPatients){
			if(p == patient) {
				hasThisPatient = true;
				break;
			}
		}
		if(!hasThisPatient){
			listOfPatients.add(patient);
			System.out.println("Patient " + patient.name + " has been added on doctor's " + name + " list");
			hasPatients = true;
			patient.hasDoctor = true;
		} else System.out.println("Patient " + patient.name + " already exists on the doctor's "+name+" list");
	}
	
	public void deletePatient(Patient patient){
		boolean hasThisPatient = false;
		for (Patient p : listOfPatients){
			if(p == patient) {
				hasThisPatient = true;
				break;
			}
		}
		if(!hasThisPatient)
			System.out.println("Doctor " + name + " doesn't recognize patient " + patient.name);
		else{
			listOfPatients.remove(patient);
			System.out.println("Patient " + patient.name + " has been deleted from doctor's "+name+" list");
			if(listOfPatients.size()==0)
				hasPatients = false;
		}
	}
	
	public void showListOfPatients(){
		if(hasPatients){
			System.out.println("Patients of doctor "+name+" are: ");
			for(Patient patient : listOfPatients)
				System.out.println(patient.name);
		}
		else System.out.println("This doctor ("+name+") doesn't have any patients");
	}
}

class Console{
	List<Doctor>doctors;
	List<Patient>patients;
	

	public Console(){
		doctors = new ArrayList<Doctor>();
		patients = new ArrayList<Patient>();
		start();
	}
	public boolean checkDoctorExistence(String string_doctor){
		boolean existsDoctor = false;
		
		for (Doctor doctor : doctors){
					if(doctor.name.equals(string_doctor)){
						existsDoctor = true;
						break;
					}
			}
		if(existsDoctor) return true;
		else return false;
	}
	public boolean checkPatientExistence(String string_patient){
			boolean existsPatient = false;

			for (Patient patient : patients){
					if(patient.name.equals(string_patient)){
						existsPatient = true;
						break;
					}
				}
			if(existsPatient) return true;
			else return false;
		}
		
	public Patient getPatient(String string_patient){
		Patient copy_patient = new Patient();
		for (Patient patient : patients){
					if(patient.name.equals(string_patient)){
						copy_patient = patient;
						break;
					}
			}
		return copy_patient;
	}
	public Doctor getDoctor(String string_doctor){
		Doctor copy_doctor = new Doctor();
		for (Doctor doctor : doctors){
					if(doctor.name.equals(string_doctor)){
						copy_doctor = doctor;
						break;
					}
			}
		return copy_doctor;
	}
	public boolean wrong_syntax(int wordsNumber, String line){
		String[] words = line.split(" ");
		if(wordsNumber != words.length) return true;
		else return false;
	}
	public void start(){
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		String copy_line = null;
		String string_patient = null;
		String string_doctor = null;
		System.out.println("Type \'help\' for help");
		while(true){ 
		try{line=br.readLine();} catch(IOException e){}
			if (line.startsWith("patient")) {
				if(!wrong_syntax(2, line)){
					line = line.substring(8);
					if(!checkPatientExistence(line)){
						Patient patient = new Patient(line);
						System.out.println("new Patient: " + patient.name);
						patients.add(patient);
					} else System.out.println("This patient already exists");
				} else System.out.println("Syntax error");
			} 
			else if (line.startsWith("doctor")) {
				if(!wrong_syntax(2, line)){
					line = line.substring(7);
					if(!checkDoctorExistence(line)){
						Doctor doctor = new Doctor(line);
						System.out.println("new Doctor: " + doctor.name);
						doctors.add(doctor);
					} else System.out.println("This doctor already exists");
				} else System.out.println("Syntax error");
			}
			else if (line.equals("show doctors")){
				if(!wrong_syntax(2, line)){
					for(Doctor doctor : doctors)
						System.out.println(doctor.name);
					if(doctors.size() == 0) System.out.println("List of doctors is empty");
				} else System.out.println("Syntax error");
			}
			else if (line.equals("show patients")){
				if(!wrong_syntax(2, line)){
					for(Patient patient : patients)
						System.out.println(patient.name);
					if(patients.size() == 0) System.out.println("List of patients is empty");
				} else System.out.println("Syntax error");
			}
			else if (line.startsWith("setDoctor")){ 
				if(!wrong_syntax(3, line)){
					line = line.substring(10);
					if(line.contains(" ")){
						string_doctor = line.substring(0, line.indexOf(" "));
						string_patient = line.substring(line.indexOf(" ")+1);
					}
					if(checkDoctorExistence(string_doctor) && checkPatientExistence(string_patient)){
						getPatient(string_patient).setDoctor(getDoctor(string_doctor));
					} else System.out.println("Doctor " + string_doctor + " or patient " + string_patient + " doesn't exists");
				} else System.out.println("Syntax error");
			}
			else if(line.startsWith("deleteDoctor")){
				if(!wrong_syntax(2, line)){
					string_patient = line.substring(13);
					if(checkPatientExistence(string_patient)){
						getPatient(string_patient).deleteDoctor();
					} else System.out.println("Patient " + string_patient + " doesn't exists");
				} else System.out.println("Syntax error");
			}
			else if(line.startsWith("changeDoctor")){
				if(!wrong_syntax(3, line)){
					line = line.substring(13);
					if(line.contains(" ")){
						string_doctor = line.substring(0, line.indexOf(" "));
						string_patient = line.substring(line.indexOf(" ")+1);
					}
					if(checkDoctorExistence(string_doctor) && checkPatientExistence(string_patient)){
						getPatient(string_patient).changeDoctor(getDoctor(string_doctor));
					} else System.out.println("Doctor " + string_doctor + " or patient " + string_patient + " doesn't exists");
				} else System.out.println("Syntax error");
			}
			else if(line.startsWith("showDoctor")){
				if(!wrong_syntax(2, line)){
					string_patient = line.substring(11);
					if(checkPatientExistence(string_patient)){
						getPatient(string_patient).showDoctor();
					} else System.out.println("Patient " + string_patient + " doesn't exists");
				} else System.out.println("Syntax error");
			}
			else if(line.startsWith("info")){
				if(!wrong_syntax(2, line)){
					line = line.substring(5); //patient or doctor
					if(checkPatientExistence(line)){
						getPatient(line).info();
					} else if (checkDoctorExistence(line)){
						getDoctor(line).info();
					}else System.out.println("Doctor or patient " + line + " doesn't exists");
				} else System.out.println("Syntax error");
			}
			else if(line.startsWith("addPatient")){
				if(!wrong_syntax(3, line)){
					line = line.substring(11);
					if(line.contains(" ")){
						string_patient = line.substring(0, line.indexOf(" "));
						string_doctor = line.substring(line.indexOf(" ")+1);
					}
					if(checkDoctorExistence(string_doctor) && checkPatientExistence(string_patient)){
						getDoctor(string_doctor).addPatient(getPatient(string_patient));
					} else System.out.println("Doctor " + string_doctor + " or patient " + string_patient + " doesn't exists");
				} else System.out.println("Syntax error");
			}
			else if(line.startsWith("deletePatient")){
				if(!wrong_syntax(3, line)){
					line = line.substring(14);
					if(line.contains(" ")){
						string_patient = line.substring(0, line.indexOf(" "));
						string_doctor = line.substring(line.indexOf(" ")+1);
					}
					if(checkDoctorExistence(string_doctor) && checkPatientExistence(string_patient)){
						getDoctor(string_doctor).deletePatient(getPatient(string_patient));
					} else System.out.println("Doctor " + string_doctor + " or patient " + string_patient + " doesn't exists");
				} else System.out.println("Syntax error");
			}
			else if(line.startsWith("showListOfPatients")){
				if(!wrong_syntax(2, line)){
					string_doctor = line.substring(19);
					if(checkDoctorExistence(string_doctor)){
						getDoctor(string_doctor).showListOfPatients();
					} else System.out.println("Doctor " + string_doctor + " or patient " + string_patient + " doesn't exists");
				} else System.out.println("Syntax error");
			}
			else if(line.equals("exit")) break; 
			else if(line.equals("help")){
				System.out.println("==========================================================================================");
				System.out.println("A doctor can have many patients and a patient can have only a doctor");
				System.out.println("doctor doctorName // declare a doctor object named doctorName");
				System.out.println("patient patientName // declare a patient object named patientName");
				System.out.println("show doctors // will appear a list of doctors");
				System.out.println("show patients // will appear a list of patients");
				System.out.println("setDoctor doctorName patientName //set the doctor doctorName to patient patientName");
				System.out.println("deleteDoctor patientName // delete the patientName's doctor");
				System.out.println("changeDoctor doctorName patientName // change old doctor of patientName's with new doctor doctorName");
				System.out.println("showDoctor patientName // show the patientName's doctor");
				System.out.println("info patientName or doctorName // show details about patientName or doctorName");
				System.out.println("addPatient patientName doctorName // set the doctor doctorName for patient patientName ");
				System.out.println("deletePatient patientName doctorName // delete patient patientName from doctor doctorName's list");
				System.out.println("showListOfPatients doctorName // show list of doctorName's patients");
				System.out.println("help // info about avalaible commands");
				System.out.println("exit // close the program");
				System.out.println("==========================================================================================");
			}
			else System.out.println("Unknown command");	
			
		}
	}
	
}

public class App{
	public static void main(String[] args){
		new Console();
	}
}
