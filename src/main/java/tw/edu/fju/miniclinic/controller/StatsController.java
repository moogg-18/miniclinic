package tw.edu.fju.miniclinic.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tw.edu.fju.miniclinic.model.Appointment;
import tw.edu.fju.miniclinic.model.AppointmentRepository;
import tw.edu.fju.miniclinic.model.DoctorRepository;
import tw.edu.fju.miniclinic.model.PatientRepository;

@Controller
public class StatsController {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @GetMapping("/stats")
    public String statsPage(Model model) {

        model.addAttribute("doctorCount", doctorRepo.count());
        model.addAttribute("patientCount", patientRepo.count());
        model.addAttribute("appointmentCount", appointmentRepo.count());

        List<Appointment> appointments = appointmentRepo.findAll();

        Map<String, Integer> deptStats = new HashMap<>();

        for (Appointment appt : appointments) {

            String dept = appt.getDoctor().getDepartment();

            deptStats.put(
                    dept,
                    deptStats.getOrDefault(dept, 0) + 1
            );
        }

        model.addAttribute("deptStats", deptStats);

        return "stats";
    }
}