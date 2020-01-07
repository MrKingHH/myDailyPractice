package com.github.mrkinghh.rpc.server;

import com.github.mrkinghh.rpc.framework.Config;
import com.github.mrkinghh.rpc.framework.RpcFramework;

import java.io.IOException;

public class ServerMain {
  public static void main(String[] args) throws IOException {
    Config config = Config.builder()
                    .ip("127.0.0.1")
                    .port(1234).build();

    RpcFramework rpc = new RpcFramework(config);
    rpc.exportService(new HelloWorldServiceImpl());
  }
}
