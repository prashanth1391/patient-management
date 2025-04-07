package code.pkdev.patientservice.service;

import code.pkdev.patientservice.dto.PatientRequestDTO;
import code.pkdev.patientservice.dto.PatientResponseDTO;
import code.pkdev.patientservice.mapper.PatientMapper;
import code.pkdev.patientservice.model.Patient;
import code.pkdev.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(patient -> PatientMapper.toDto(patient)).toList();
    }

    public PatientResponseDTO createpatient(PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDto(patient);
    }
}
