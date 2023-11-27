package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.impl;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.KhachHang;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository.KhachHangRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CustomUserDetailService implements UserDetailsService {


    private final KhachHangRepository khachHangRepository;

    public CustomUserDetailService(KhachHangRepository khachHangRepository) {
        this.khachHangRepository = khachHangRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        KhachHang khachHang = khachHangRepository.findByEmail(email);
        if (khachHang == null) {
            throw new UsernameNotFoundException("khong tim thay email " + email);

        }
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singletonList(new SimpleGrantedAuthority("USER"));
            }

            @Override
            public String getPassword() {
                return khachHang.getMatKhau();
            }

            @Override
            public String getUsername() {
                return khachHang.getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }


}
