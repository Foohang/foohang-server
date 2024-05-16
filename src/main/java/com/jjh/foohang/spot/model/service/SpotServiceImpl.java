package com.jjh.foohang.spot.model.service;

import com.jjh.foohang.spot.dto.SidoGugun;
import com.jjh.foohang.spot.dto.Spot;
import com.jjh.foohang.spot.model.mapper.SpotMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {

    private final SpotMapper spotMapper;

    @Override
    public List<SidoGugun> getSidoList() {
        return spotMapper.getSidoList();
    }

    @Override
    public List<SidoGugun> findSido(String sidoName) {
        return spotMapper.findSidoBySidoName(sidoName);
    }

    @Override
    public SidoGugun findSido(int sidocode) {
        return spotMapper.findSidoBySidoCode(sidocode);
    }

    @Override
    public List<SidoGugun> getGugunList(int sidoCode) {
        return spotMapper.getGugunList(sidoCode);
    }

    @Override
    public List<Spot> getSpotList(SidoGugun sidoGugun) {
        return spotMapper.getSpotList(sidoGugun);
    }

    @Override
    public Spot getSpotDetail(int contentId) {
        return spotMapper.getSpotDetail(contentId);
    }

}
