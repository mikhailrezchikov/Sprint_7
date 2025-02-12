package api.models;

import lombok.Data;

@Data
public class OrderResponse {
    private int id;
    private String address;
    private int track;
}
