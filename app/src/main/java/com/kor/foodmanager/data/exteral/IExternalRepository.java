package com.kor.foodmanager.data.exteral;

import com.kor.foodmanager.data.model.HebcalDto;

import java.io.IOException;

public interface IExternalRepository {
    HebcalDto getIsrHolidays(int month) throws IOException;
}
