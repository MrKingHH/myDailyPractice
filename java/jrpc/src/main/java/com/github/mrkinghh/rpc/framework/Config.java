package com.github.mrkinghh.rpc.framework;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Config {
    public String ip;
    public int port;
}
