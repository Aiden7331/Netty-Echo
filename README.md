# Netty-Echo 
## 네티 연습 - 네티 공부용도로 생성한 저장소입니다.

- Channel  
  Netty는 상대방과 연결되는 통로를 Channel이라 부릅니다. 통신을 하기 위해선 Channel을 사용해서 데이터를 
  주고받아야합니다.

- ChannelHandler  
  Channel이 연결되었을때 데이터를 어떻게 처리할 것인지 명세합니다. 비즈니스 로직이 들어가는 부분입니다.

- EventLoop  
데이터를 주고 받는데, 이때 여러 클라이언트가 하나의 서버에 연결되어 있다면 여러개 생성된 Channel을 효과적으로
처리할 실행흐름이 필요합니다. 스레드의 개념과 유사합니다.

### EchoServer.java / EchoClient.java

- Bootstrap 설정  
 소켓 프로그래밍에서의 bind/ connect의 과정을 담고있고 추가적으로 연결이 되었을때 어떤 작업을 수행할 것인지, 
 블로킹/논블로킹 은 어떻게 할것인지 설정할 수 있습니다. 리눅스의 멀티 프로세스 프로그래밍 과정에서 서버 프로세스가 
 담당하는 클라이언트의 연결관리 역할과 비슷한 역할을 수행합니다.
 
- ChannelPipe 구성  
 Netty는 송수신된 데이터에 대해서 리눅스의 pipe 처럼 Pipe Layer Architecture를 적용하고 있습니다.
 인바운드 데이터(수신된 데이터)에 대한 pipe를 구성해서 단계별로 데이터 처러를 수행합니다. 그리고 
 아웃바운드 데이터(송신할 데이터)에 대해서도 pipe를 구성해서 단계별로 데이터 처러를 수행합니다.

### EchoHandler.java
- ChannelHanlder 작성  
  Channel은 다음과 같은 4가지의 상태를 갖습니다.  
 
  channelRegistered   : 채널이 eventloop에 등록된 상태  
  channelActive       : 채널이 활성화된 상태  
  channelInactive     : 채널이 비활성화된 상태  
  channelUnregistered : 채널이 eventloop에 등록되지 않은 상태  
  
  그리고 channelRead(상대편으로 부터 데이터를 수신할때 수행)/ channelReadComplete(데이터 수신이 끝난 후 수행)등  
  Adaptor를 이용해 Override한 여러 메소드를 사용할 수 있습니다.
 
 
