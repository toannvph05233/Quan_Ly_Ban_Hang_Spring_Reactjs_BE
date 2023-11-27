package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Integer> {

}
