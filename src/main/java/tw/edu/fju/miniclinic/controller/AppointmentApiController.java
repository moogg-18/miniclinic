package tw.edu.fju.miniclinic.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tw.edu.fju.miniclinic.model.Appointment;
import tw.edu.fju.miniclinic.model.AppointmentRepository;
import tw.edu.fju.miniclinic.model.Doctor;
import tw.edu.fju.miniclinic.model.DoctorRepository;

@RestController
public class AppointmentApiController {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @GetMapping("/api/appointments")
    public List<Appointment> getAppointments(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String doctorId) {

        if (date != null && !date.isBlank()) {
            return appointmentRepo.findByApptDate(LocalDate.parse(date));
        }

        if (doctorId != null && !doctorId.isBlank()) {
            Doctor doctor = doctorRepo.findById(doctorId).orElse(null);
            if (doctor != null) {
                return appointmentRepo.findByDoctor(doctor);
            }
        }

        return appointmentRepo.findAll();
    }

    @GetMapping("/api/appointments/count")
    public Map<String, Long> countAppointments() {
        return Map.of("count", appointmentRepo.count());
    }

    @PutMapping("/api/appointments/{apptId}/status")
    public ResponseEntity<Appointment> updateStatus(
            @PathVariable Long apptId,
            @RequestBody Map<String, String> payload,
            HttpSession session) {

        String loggedInDoctorId =
                (String) session.getAttribute("loggedInDoctorId");

        Appointment appt =
                appointmentRepo.findById(apptId).orElse(null);

        if (appt == null) {
            return ResponseEntity.notFound().build();
        }

        if (!appt.getDoctor().getDoctorId().equals(loggedInDoctorId)) {
            return ResponseEntity.status(403).build();
        }

        String newStatus = payload.get("status");

        if (!List.of("BOOKED", "COMPLETED", "CANCELLED")
                .contains(newStatus)) {
            return ResponseEntity.badRequest().build();
        }

        appt.setStatus(newStatus);

        Appointment saved =
                appointmentRepo.save(appt);

        return ResponseEntity.ok(saved);
    }
}