package code.pkdev.patientservice.controller;

import code.pkdev.patientservice.dto.PatientRequestDTO;
import code.pkdev.patientservice.dto.PatientResponseDTO;
import code.pkdev.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class patientController {
    private final PatientService patientService;

    public patientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patientResponseDTOS = patientService.getPatients();
        return ResponseEntity.ok().body(patientResponseDTOS);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createpatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }
}
