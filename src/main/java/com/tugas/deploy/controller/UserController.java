package com.tugas.deploy.controller;

import com.tugas.deploy.model.Mahasiswa;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    // Data mahasiswa disimpan di memory (temporary)
    private final List<Mahasiswa> mahasiswaList = new ArrayList<>();

    // ==================== LOGIN ====================

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/login")
    public String loginPageAlias() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        // Kredensial hardcoded (temporary)
        if ("admin".equals(username) && ("admin").equals(password)) {
            session.setAttribute("loggedIn", true);
            session.setAttribute("username", username);
            return "redirect:/home";
        }
        redirectAttributes.addFlashAttribute("error", "Username atau password salah!");
        return "redirect:/login";
    }

    // ==================== HOME ====================

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("mahasiswaList", mahasiswaList);
        model.addAttribute("totalMahasiswa", mahasiswaList.size());
        model.addAttribute("totalLaki", mahasiswaList.stream()
                .filter(m -> "Laki-laki".equals(m.getJenisKelamin())).count());
        model.addAttribute("totalPerempuan", mahasiswaList.stream()
                .filter(m -> "Perempuan".equals(m.getJenisKelamin())).count());
        return "index";
    }

    // ==================== FORM ====================

    @GetMapping("/form")
    public String formPage(HttpSession session, Model model) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", session.getAttribute("username"));
        return "form";
    }

    @PostMapping("/form")
    public String handleForm(@RequestParam String nama,
                             @RequestParam String nim,
                             @RequestParam String jenisKelamin,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        mahasiswaList.add(new Mahasiswa(nama, nim, jenisKelamin));
        redirectAttributes.addFlashAttribute("success", "Data mahasiswa berhasil ditambahkan!");
        return "redirect:/home";
    }

    // ==================== LOGOUT ====================

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
