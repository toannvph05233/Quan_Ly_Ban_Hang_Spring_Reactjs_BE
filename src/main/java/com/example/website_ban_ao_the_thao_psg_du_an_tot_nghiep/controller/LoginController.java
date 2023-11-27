package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.controller;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.KhachHang;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.NhanVien;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.KhachHangService;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.NhanVienService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {
    @Autowired
    KhachHangService khachHangService;
    @Autowired
    private HttpSession session;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    NhanVienService nhanVienService;

    @PostMapping("/khach-hang")
    public ResponseEntity<?> loginKH(@RequestBody KhachHang khachHang) {
        KhachHang khachHangLogin = khachHangService.findByEmail(khachHang.getEmail());
        if (khachHangLogin != null && passwordEncoder.matches(khachHang.getMatKhau(), khachHangLogin.getMatKhau())) {
            session.setAttribute("khachHangLogin", khachHangLogin);
            return new ResponseEntity<>(khachHangLogin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/nhan-vien")
    public ResponseEntity<?> loginNV(@RequestBody NhanVien nhanVien, HttpServletResponse response) {
        NhanVien nhanVienLogin = nhanVienService.findByEmail(nhanVien.getEmail());
        if (nhanVienLogin != null && passwordEncoder.matches(nhanVien.getMatKhau(), nhanVienLogin.getMatKhau())) {
            Cookie cookie = new Cookie("email", nhanVienLogin.getEmail());
            response.addCookie(cookie);
            return new ResponseEntity<>(nhanVienLogin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
