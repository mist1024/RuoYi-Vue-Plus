<template>
<div>
</div>
</template>

<script>
import { getToken } from '@/utils/auth'

export default {
  name: "WebSocket",
  data() {
    return {
      webSocket: undefined,
      wsUri: undefined,
      lockReconnect: false,//是否真正建立连接
      timeout: 60 * 1000,//60秒一次心跳
      timeoutObj:undefined,//时间间隔对象
      serverTimeoutObj:undefined,//心跳倒计时对象
      timeoutNum:undefined,//重连对象

    };
  },
  created() {
    this.initWebSocket();
  },
  methods: {
    initWebSocket() {
      this.initWebSocketUri();
      //建立连接
      if (typeof (WebSocket) == "undefined") {
        console.log("你的浏览器不支持WebSocket");
      }else {
        this.webSocket = new WebSocket(this.wsUri);
        console.log("this.websocket", this.webSocket);
        this.webSocket.onopen = this.webSocketOnopen;
        this.webSocket.onerror = this.webSocketOnerror;
        this.webSocket.onmessage = this.webSocketOnmessage;
        this.webSocket.onclose = this.webSocketOnclose;
      }
    },
    initWebSocketUri() {
      this.wsUri = "ws://127.0.0.1:8080/webSocket/" + getToken();
    },
    webSocketOnopen() {
      console.log("连接成功");

      this.start();
    },
    webSocketOnerror() {
      console.log("出错")
      this.reconnect();
    },
    webSocketOnmessage(res) {
      console.log("接收消息",res)

      this.reset();
    },
    webSocketOnclose() {
      console.log("关闭连接")
      this.reconnect();
    },
    reset() {
      this.timeoutObj && clearTimeout(this.timeoutObj);
      this.serverTimeoutObj && clearTimeout(this.serverTimeoutObj);
      //重启心跳
      this.start();
    },
    //开启心跳
    start() {
      this.timeoutObj && clearTimeout(this.timeoutObj);
      this.serverTimeoutObj && clearTimeout(this.serverTimeoutObj);

      this.timeoutObj=  setTimeout(() => {
        if (this.webSocket.readyState === 1) {
          this.webSocketSend("heartCheck");
        }else {
          this.reconnect();
        }
        this.serverTimeoutObj = setTimeout(() => {
          this.webSocket.close();
        }, this.timeout);

      }, this.timeout);
    },
    webSocketSend(msg) {
      this.webSocket.send(msg)
    },
    reconnect() {
      if (this.lockReconnect) {
        return;
      }
      this.lockReconnect = true;
      this.timeoutNum && clearTimeout(this.timeoutNum);
      this.timeoutNum = setTimeout(() => {
        this.initWebSocket();
        this.lockReconnect = false;
      }, 5000);
    },

  },


}
</script>

<style scoped lang="scss">

</style>
