package com.kor.foodmanager.data.model;

import java.util.Arrays;

public class PlaceDetailsDto {
    String[] html_attributions;
    PlaceDetailsResultDto result;
    String status;
    String error_message;

    public PlaceDetailsDto(String[] html_attributions, PlaceDetailsResultDto result, String status, String error_message) {
        this.html_attributions = html_attributions;
        this.result = result;
        this.status = status;
        this.error_message = error_message;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PlaceDetailsDto() {
    }

    public String[] getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(String[] html_attributions) {
        this.html_attributions = html_attributions;
    }

    public PlaceDetailsResultDto getResult() {
        return result;
    }

    public void setResult(PlaceDetailsResultDto result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "PlaceDetailsDto{" +
                "html_attributions=" + Arrays.toString(html_attributions) +
                ", result=" + result +
                ", status='" + status + '\'' +
                ", error_message='" + error_message + '\'' +
                '}';
    }
}
