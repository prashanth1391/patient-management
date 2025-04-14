package code.pkdev.patientservice.service;

import code.pkdev.patientservice.dto.PatientRequestDTO;
import code.pkdev.patientservice.dto.PatientResponseDTO;
import code.pkdev.patientservice.exception.EmailAlReadyExistsException;
import code.pkdev.patientservice.exception.PatientNotFoundException;
import code.pkdev.patientservice.mapper.PatientMapper;
import code.pkdev.patientservice.model.Patient;
import code.pkdev.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlReadyExistsException("Email already exists "+patientRequestDTO.getEmail());
        }
        Patient patient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDto(patient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException("Patient not found with id "+id));
        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id)){
            throw new EmailAlReadyExistsException("Email already exists "+patientRequestDTO.getEmail());
        }
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDto(updatedPatient);

    }

    public void deletePatient(UUID id){
        Patient patient = patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException("Patient not found with id "+id));
        patientRepository.delete(patient);
    }
}
