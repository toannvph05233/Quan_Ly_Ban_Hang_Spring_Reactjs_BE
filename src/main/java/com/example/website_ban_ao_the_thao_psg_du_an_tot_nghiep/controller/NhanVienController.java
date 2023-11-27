package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.controller;


import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.ForgotPasswordRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.KhachHangDto;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.LoginMesage;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.NhanVienDto;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception.ResourceNotFoundException;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateNhanVienRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateNhanVienRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.NhanVienService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant.TrangThaiTaiKhoan.ACTIVE;
import static com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant.TrangThaiTaiKhoan.INACTIVE;

@RequestMapping("/api/nhan-vien")
@RestController
@CrossOrigin(origins = "*", maxAge = -1)
public class NhanVienController {
    @Autowired
    NhanVienService nhanVienService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(nhanVienService.pageNhanVien(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search", required = false) String search,
                                    @RequestParam(name = "trangThai", required = false) ApplicationConstant.TrangThaiTaiKhoan trangThai,
                                    @RequestParam(name = "vaiTroId", required = false) Integer vaiTroId,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(nhanVienService.pageSearchNhanVien(pageNo, pageSize, search, trangThai, vaiTroId));
    }

    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        byte[] excelFile = nhanVienService.exportExcelNhanVien();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");
        response.getOutputStream().write(excelFile);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listNhanVienAcitve() {
        return ResponseEntity.ok(nhanVienService.listNhanVienResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNhanVienId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(nhanVienService.getOne(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNhanVien(@Valid @RequestBody CreateNhanVienRequest createNhanVienRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(nhanVienService.create(createNhanVienRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNhanVien(@Valid @RequestBody UpdateNhanVienRequest updateNhanVienRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(nhanVienService.update(updateNhanVienRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertNhanVien(@PathVariable("id") Integer id) {
        nhanVienService.removeOrRevert(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeNhanVien(@PathVariable("id") Integer id) {
        nhanVienService.removeOrRevert(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }

    @PostMapping(path = "/login-nhan-vien")
    public ResponseEntity<?> loginNhanVien(@RequestBody NhanVienDto nhanVienDto) {
        LoginMesage loginMesage = nhanVienService.loginNhanVien(nhanVienDto);
        return ResponseEntity.ok(loginMesage);
    }

    @PostMapping("/changeEmailNv")
    public ResponseEntity<String> changeEmail(@RequestBody NhanVienDto nhanVienDto, @RequestParam String newEmail) {
        boolean isChanged = nhanVienService.changeEmail(nhanVienDto, newEmail);
        if (isChanged) {
            return ResponseEntity.ok("Thay đổi email thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vui lòng nhập lại mật khẩu của mình");
        }
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> sendForgotPasswordEmailForNhanVien(@RequestBody ForgotPasswordRequest request) {
        try {
            nhanVienService.sendForgotPasswordEmailForNhanVien(request.getEmail(), request.getSoCanCuocCongDan());
            return ResponseEntity.ok("Mật khẩu đã được gửi lại vào email của nhân viên.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
