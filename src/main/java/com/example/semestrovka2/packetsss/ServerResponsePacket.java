package com.example.semestrovka2.packetsss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class ServerResponsePacket implements Serializable {

    // entry-init point
    private final byte HEADER = 0x21;
    private String content;
    private final byte FOOTER = 0x22;

}
