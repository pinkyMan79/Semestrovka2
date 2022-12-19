package com.example.semestrovka2.packetsss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class ClientRequestPacket implements Serializable {

    private final byte HEADER = 0x11;
    private String code;
    private final byte FOOTER = 0x12;


}
