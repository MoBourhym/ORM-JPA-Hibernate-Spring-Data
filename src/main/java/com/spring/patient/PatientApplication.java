package com.spring.patient;

import com.spring.patient.entities.Patient;
import com.spring.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootApplication
public class PatientApplication {
    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(PatientApplication.class, args);
    }

    @Bean
    CommandLineRunner start(PatientRepository patientRepository) {
        return args -> {
            // 1. Ajouter des patients
            Stream.of("Line", "Omar", "Hiba").forEach(name -> {
                patientRepository.save(
                        new Patient(null, name + " Morsi", new Date(), false, 200)
                );
            });

            Stream.of("Khaled", "Yasmine", "Hajar").forEach(name -> {
                patientRepository.save(
                        new Patient(null, name + " Nouri", new Date(), true, 200)
                );
            });

            // 2. Consulter tous les patients
            System.out.println("\n--- Tous les patients ---");
            List<Patient> patients = patientRepository.findAll();
            patients.forEach(System.out::println);

            // 3. Consulter un patient par ID
            Optional<Patient> optionalPatient = patientRepository.findById(1L);
            if (optionalPatient.isPresent()) {
                Patient patient = optionalPatient.get();
                System.out.println("\n--- Patient avec ID = 1 ---");
                System.out.println(patient);

                // 4. Mettre à jour un patient
                patient.setName("Issam");
                patientRepository.save(patient);
                System.out.println("\n--- Après mise à jour ---");
                System.out.println(patient);
            }

            // 5. Supprimer un patient
            patientRepository.deleteById(1L);
            System.out.println("\n--- Après suppression du patient ID = 1 ---");
            patientRepository.findAll().forEach(System.out::println);

            // 6. Chercher des patients (par exemple: les patients malades)
            System.out.println("\n--- Patients avec maladie = false ---");
            List<Patient> filteredPatients = patientRepository.findBySickness(false);
            filteredPatients.forEach(System.out::println);
        };
    }
}
