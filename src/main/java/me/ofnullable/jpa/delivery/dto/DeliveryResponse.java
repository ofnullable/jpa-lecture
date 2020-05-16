package me.ofnullable.jpa.delivery.dto;

import lombok.Getter;
import lombok.Setter;
import me.ofnullable.jpa.delivery.domain.DeliveryStatus;
import me.ofnullable.jpa.member.domain.Address;

@Getter @Setter
public class DeliveryResponse {
    private Address address;
    private DeliveryStatus status;
}
