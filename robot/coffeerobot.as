.PROGRAM close_socket()  ;Closing communication 
    TCP_CLOSE ret,sock_id  ;Normal socket closure 
        IF ret < 0 THEN 
        PRINT "TCP_CLOSE error ERROE=(",ret,") ",$ERROR(ret) 
        TCP_CLOSE ret1,sock_id    ;Forced closure of socket (shutdown) 
        IF ret1 < 0 THEN 
            PRINT  "TCP_CLOSE error id=",sock_id 
        END 
    ELSE 
        PRINT  "TCP_CLOSE OK id=",sock_id 
    END 
    TCP_END_LISTEN ret,port 
        IF ret < 0 THEN 
        PRINT  "TCP_CLOSE error id=",sock_id 
    ELSE 
        PRINT  "TCP_CLOSE OK id=",sock_id 
    END 
.END 

.PROGRAM open_socket() ;Starting c ommunication 
    er_count =0 
listen: 
    TCP_LISTEN retl,port 
    IF retl<0  THEN 
        IF er_count >= 5  THEN 
            PRINT  "Connection with PC is failed (LISTEN). Program is stopped." 
            sock_id = -1 
            goto  exit 
        ELSE 
            er_count = er_count+1 
            PRINT  "TCP_LISTEN error=",retl,"    error count=",er_count 
            GOTO  listen 
        END 
    ELSE 
        PRINT  "TCP_LISTEN OK ",retl 
    END 
    er_count =0 
accept: 
    TCP_ACCEPT sock_id,port,tout_open,ip[1] 
    IF sock_id<0 THEN 
        IF er_count >= 5  THEN 
            PRINT  "Connection with PC is failed (ACCEPT). Program is stopped." 
            TCP_END_LISTEN  ret,port 
            sock_id = -1 
        ELSE 
            er_count = er_count+1 
            PRINT  "TCP_ACCEPT error id=",sock_id,"  error count=",er_count 
            GOTO  accept 
        END 
    ELSE 
        PRINT  "TCP_ACCEPT OK id=",sock_id 
    END 
exit: 
.END 
.PROGRAM tcp_recv() ;Communication  Receiving data 
    .num=0 
    TCP_RECV rret,sock_id,$recv_buf[1],.num,tout_rec,max_length 
    IF rret < 0  THEN 
        PRINT  "TCP_RECV error in RECV",rret 
        $recv_buf[1]="000" 
    ELSE 
        IF .num > 0  THEN 
            PRINT  "TCP_RECV OK  in RECV",rret 
        ELSE 
            $recv_buf[1]="000" 
        END 
    END 
.END 
.PROGRAM tcp_send(.ret,.$data)    ;Communication  Sending data 
    $send_buf[1] = .$data 
    buf_n = 1 
  .ret = 1 
    TCP_SEND .ret,sock_id,$send_buf[1],buf_n,tout 
    IF .ret < 0 THEN 
        .ret = -1 
        PRINT  "TCP_SEND error in SEND",.ret 
    ELSE 
        PRINT  "TCP_SEND OK  in SEND",.ret 
    END 
.END 


.PROGRAM coffeerobotpc() 
  port = 49152
  max_length = 255
  tout_open = 5
  tout_rec = 5
  text_id = 0
  tout = 60
  eret = 0
  rret = 0
  stopcon = FALSE;
;WHILE TRUE DO
con_begin:
  CALL open_socket;Connecting communication 
  IF sock_id<0 THEN
    IF stopcon==TRUE THEN
      GOTO exit
    END
    GOTO con_begin
  END
  PRINT "Connection established"
cyc_begin:
  IF stopcon==TRUE THEN
    GOTO exit
  END
  tout_rec = 5
  call tcp_recv;Receiving the result of processing 1 
  IF rret==-34024 THEN
    PRINT "Recieve timeout"
    GOTO cyc_begin
  END
  IF rret==-34025 THEN
    PRINT "Connection error"
    CALL close_socket
    GOTO con_begin
  END
  IF rret==0 THEN
    PRINT $recv_buf[1]
    $drink = ""
    IF $recv_buf[1]=="Cappuccino\n" THEN
      $drink = $recv_buf[1]
    END
    IF $recv_buf[1]=="Espresso\n" THEN
      $drink = $recv_buf[1]
    END
    IF $recv_buf[1]=="Americano\n" THEN
      $drink = $recv_buf[1]
    END
    IF $recv_buf[1]=="Latte\n" THEN
      $drink = $recv_buf[1]
    END
    coffeemade = FALSE
    IF $drink!="" THEN
      MC EXECUTE coffeeroboth1
      MC EXECUTE 2: coffeeroboth2
    END
    WAIT coffeemade==TRUE
    eret = 0
    $sdata[1] = "OK\n"
    call tcp_send(eret,$sdata[1])
  END
  ; Где-то тут  TWAIT 3
  GOTO cyc_begin
;END    
exit:
  CALL close_socket;Closing communication 
exit_end:
.END

.PROGRAM coffeeroboth1()
sg1 = 0
sg2 = 0
sg3 = 0
sg4 = 0
sg5 = 0
SPEED 16 ALWAYS
JMOVE #h1_home
JMOVE #h1_1
SPEED 4 ALWAYS
JMOVE #h1_2; Zahvat stakana
JMOVE #h1_3
JMOVE #h1_4
BREAK
sg1 = 1
WAIT sg2==1
sg3 = 1
JMOVE #h1_5
SPEED 16 ALWAYS
JMOVE #h1_6
;BREAK
JMOVE #h1_7
JMOVE #h1_8
SPEED 4 ALWAYS
JMOVE #h1_9
JMOVE #h1_10
SPEED 16 ALWAYS
JMOVE #h1_11
WAIT sg4==1
JMOVE #h1_10
SPEED 4 ALWAYS
JMOVE #h1_9
JMOVE #h1_8
JMOVE #h1_7
JMOVE #h1_6
SPEED 4 ALWAYS
JMOVE #h1_12
JMOVE #h1_13
JMOVE #h1_14
BREAK
sg5 = 1
JMOVE #h1_15
isready = TRUE
coffeemade = TRUE
.END
.PROGRAM coffeeroboth2()
sg1 = 0
sg2 = 0
sg3 = 0
sg4 = 0
SPEED 16 ALWAYS
JMOVE #h2_home
JMOVE #h2_1
PRINT sg1
WAIT sg1==1
SPEED 4 ALWAYS
JMOVE #h2_2
BREAK
sg2 = 1
WAIT sg3==1
TYPE "2: go away"
JMOVE #h2_3
SPEED 4 ALWAYS
JMOVE #h2_4
JMOVE #h2_5
SPEED 4 ALWAYS
JMOVE #h2_6
JMOVE #h2_7
SPEED 16 ALWAYS
JMOVE #h2_8
JMOVE #h2_9
JMOVE #h2_10
IF $drink=="Espresso\n" THEN
JMOVE #h2_e1
JMOVE #h2_e2
TWAIT 0.5
JMOVE #h2_e3
JMOVE #h2_11
TWAIT 40
END
IF $drink=="Americano\n" THEN
JMOVE #h2_a1
JMOVE #h2_a2
TWAIT 0.5
JMOVE #h2_a3
JMOVE #h2_11
TWAIT 55
END
IF $drink=="Cappuccino\n" THEN
JMOVE #h2_c1
JMOVE #h2_c2
TWAIT 0.5
JMOVE #h2_c3
JMOVE #h2_11
TWAIT 40
END
IF $drink=="Latte\n" THEN
JMOVE #h2_l1
JMOVE #h2_l2
TWAIT 0.5
JMOVE #h2_l3
JMOVE #h2_11
TWAIT 30
END
sg4 = 1
WAIT sg5==1
SPEED 2 ALWAYS
JMOVE #h2_12
.END
