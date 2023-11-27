package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.ChatLieu;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateChatLieuRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateChatLieuRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.ChatLieuResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatLieuMapper {

    ChatLieu chatLieuResponseToChatLieuEntity(ChatLieuResponse chatLieuResponse);

    ChatLieuResponse chatLieuEntityToChatLieuResponse(ChatLieu chatLieu);

    ChatLieu createChatLieuRequestToChatLieuEntity(CreateChatLieuRequest createChatLieuRequest);

    ChatLieu updateChatLieuRequestToChatLieuEntity(UpdateChatLieuRequest updateChatLieuRequest);

    List<ChatLieuResponse> listChatLieuEntityToChatLieuResponse(List<ChatLieu> chatLieuList);
}
