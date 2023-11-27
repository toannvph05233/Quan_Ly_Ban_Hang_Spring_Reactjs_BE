package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    private HttpStatus httpStatus;
    private String message;

}
