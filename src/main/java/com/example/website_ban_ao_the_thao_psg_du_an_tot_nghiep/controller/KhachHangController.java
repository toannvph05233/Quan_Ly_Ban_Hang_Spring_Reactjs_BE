package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.controller;


import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.ChangeEmailRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.ChangePasswordRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.KhachHangDto;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.LoginMesage;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.KhachHang;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateKhachHangRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateKhachHangRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository.KhachHangRepository;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.KhachHangService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant.TrangThaiTaiKhoan.ACTIVE;
import static com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant.TrangThaiTaiKhoan.INACTIVE;

@RequestMapping("/api/khach-hang")
@RestController
@CrossOrigin(origins = "*", maxAge = -1)
public class KhachHangController {
    @Autowired
    KhachHangService khachHangService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(khachHangService.pageKhachHang(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search",required = false) String search,
                                    @RequestParam(name = "trangThai",required = false) ApplicationConstant.TrangThaiTaiKhoan trangThai,
                                    @RequestParam(name = "thuHangId",required = false) Integer thuHangId,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(khachHangService.pageSearchKhachHang(pageNo, pageSize, search, trangThai, thuHangId));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listKhachHangAcitve() {
        return ResponseEntity.ok(khachHangService.listKhachHangResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKhachHangId(@PathVariable("id") Integer id, HttpSession session) {
        return ResponseEntity.ok(khachHangService.getOne(id));
    }

    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        byte[] excelFile = khachHangService.exportExcelKhachHang();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");
        response.getOutputStream().write(excelFile);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createKhachHang(@Valid @RequestBody CreateKhachHangRequest createKhachHangRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        createKhachHangRequest.setAnh("https://file3.qdnd.vn/data/images/0/2023/05/03/vuhuyen/khanhphan.jpg");
        createKhachHangRequest.setDiaChi("Việt Nam");
        createKhachHangRequest.setGioiTinh(false);
        createKhachHangRequest.setMatKhau(passwordEncoder.encode(createKhachHangRequest.getMatKhau()));
        return ResponseEntity.ok(khachHangService.create(createKhachHangRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateKhachHang(@Valid @RequestBody UpdateKhachHangRequest updateKhachHangRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(khachHangService.update(updateKhachHangRequest, id));
    }


    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertKhachHang(@PathVariable("id") Integer id) {
        khachHangService.removeOrRevert(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeKhachHang(@PathVariable("id") Integer id) {
        khachHangService.removeOrRevert(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }

    @PostMapping(path = "/save-khach-hang")
    public Integer saveKhachHang(@RequestBody KhachHangDto khachHangDto, HttpSession session) {
        Integer id = khachHangService.addKhachHang(khachHangDto);
        return id.intValue();
    }

    @PostMapping(path = "/login-khach-hang")
    public ResponseEntity<?> loginKhachHang(@RequestBody KhachHangDto khachHangDto, HttpSession session) {
        LoginMesage loginMesage = khachHangService.loginKhachHang(khachHangDto);
        KhachHang khachHang = khachHangRepository.findByEmail(khachHangDto.getEmail());
        if (khachHang != null) {
            Integer khachHangID = khachHang.getId();

            session.setAttribute("khachHangID", khachHangID);
            loginMesage.setId(khachHangID);
            System.out.println(khachHangID);
            return ResponseEntity.ok(loginMesage);
        } else {
            return ResponseEntity.badRequest().body("Tài khoản không tồn tại");
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        KhachHangDto khachHangDto = new KhachHangDto();
        khachHangDto.setEmail(changePasswordRequest.getEmail());
        khachHangDto.setMatKhau(changePasswordRequest.getCurrentPassword());
        boolean isPasswordChanged = khachHangService.changePassword(khachHangDto, changePasswordRequest.getNewPassword());
        if (isPasswordChanged) {
            return ResponseEntity.ok().body("Đổi mật khẩu thành công");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Đổi mật khẩu không thành công. Vui lòng kiểm tra lại email và mật khẩu cũ.");
        }
    }

    @PutMapping("/update-info")
    public ResponseEntity<String> updatePersonalInfo(HttpSession session, @RequestBody KhachHangDto khachHangDto, @RequestHeader("Authorization") String authorization) {
        try {
            Integer khachHangID;
            if (session.getAttribute("khachHangID") != null) {
                khachHangID = (Integer) session.getAttribute("khachHangID");
            } else {
                khachHangID = Integer.parseInt(authorization.substring(7));
                session.setAttribute("khachHangID", khachHangID);
            }
            boolean success = khachHangService.suaThongTin(khachHangDto, khachHangID);
            if (success) {
                return new ResponseEntity<>("Thông tin khách hàng đã được cập nhật thành công.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Không thể cập nhật thông tin khách hàng. Người dùng không tồn tại.", HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Không thể xác định người dùng hiện tại.", HttpStatus.UNAUTHORIZED);
        }
    }



    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        khachHangService.sendForgotPasswordEmail(email);
        return ResponseEntity.ok().body("Mật khẩu mới đã được gửi đến địa chỉ email của bạn.");
    }


    @PostMapping("/changeEmail")
    public ResponseEntity<String> changeEmail(@RequestBody KhachHangDto khachHangDto, @RequestParam String newEmail) {
        boolean isChanged = khachHangService.changeEmail(khachHangDto, newEmail);
        if (isChanged) {
            return ResponseEntity.ok("Thay đổi email thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vui lòng nhập lại mật khẩu của mình");
        }
    }



}
