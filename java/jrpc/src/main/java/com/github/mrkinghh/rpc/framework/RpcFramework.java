package com.github.mrkinghh.rpc.framework;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;


public class RpcFramework {
  public Config config;

  public RpcFramework() {
  }

  public RpcFramework(Config config) {
    this.config = config;
  }

  public Config getConfig() {
    return config;
  }


  /**
   * This method will be called by server endpoint.
   *
   * @param service service implements by server endpoint
   */
  public void exportService(final Object service) throws IOException {
    if (service == null) {
      throw new IllegalArgumentException("services instance == null");
    }

    if (config.port < 0 || config.port > 65535) {
      throw new IllegalArgumentException("Invalid port" + config.port);
    }

    System.out.println("Export service " + service.getClass().getName() + " on port " + config.port);

    ServerSocket serverSocket = new ServerSocket(config.port);

    while (true) {
      try {
        Socket socket = serverSocket.accept();
        new Thread(() -> {
          try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            try {
              String methodName = objectInputStream.readUTF();
              Class<?>[] parameterTypes = (Class<?>[]) objectInputStream.readObject();
              Object[] arguments = (Object[]) objectInputStream.readObject();
              ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
              try {
                Method method = service.getClass().getMethod(methodName, parameterTypes);
                Object result = method.invoke(service, arguments);
                outputStream.writeObject(result);
              } catch (Throwable t) {
                outputStream.writeObject(t);
              } finally {
                outputStream.close();
              }
            } finally {
              objectInputStream.close();
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }).start();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * This method will be called by client endpoint.
   *
   * @param interfaceClass the class of interface
   * @param ip             the ip of remote machine
   * @param port           the port of remote machine
   * @return the remote services
   */
  @SuppressWarnings("unchecked")
  public <T> T getReference(Class<T> interfaceClass, String ip, int port) {
    if (interfaceClass == null) {
      throw new IllegalArgumentException("interfaceClass == null");
    }

    if (!interfaceClass.isInterface()) {
      throw new IllegalArgumentException("The" + interfaceClass.getName() + "must be interface class!");
    }

    if (ip == null || ip.isEmpty()) {
      throw new IllegalArgumentException("ip == null");
    }

    if (port > 65535 || port < 0) {
      throw new IllegalArgumentException("Invalid port " + port);
    }

    System.out.println("Get remote service reference " + interfaceClass.getName() + "from server:" + ip + ":" + port);

    return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Socket socket = new Socket(ip, port);
        try {
          ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
          try {
            outputStream.writeUTF(method.getName());
            outputStream.writeObject(method.getParameterTypes());
            outputStream.writeObject(args);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            try {
              Object result = objectInputStream.readObject();
              if (result instanceof Throwable) {
                throw (Throwable) result;
              }
              return result;
            } finally {
              objectInputStream.close();
            }

          } finally {
            outputStream.close();
          }
        } finally {
          socket.close();
        }
      }
    });
  }
}
