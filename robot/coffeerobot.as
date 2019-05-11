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
.program coffeeroboth1()
	DRIVE 4,10
	DRIVE 4,-10
.end

.program coffeeroboth2()
	DRIVE 4,10
	DRIVE 4,-10
.end

.PROGRAM coffeerobotpc()
  port = 49152 
  max_length = 255 
  tout_open = 5 
  tout_rec = 5 
  text_id = 0 
  tout = 60 
  eret = 0 
  rret = 0
  stopCon = FALSE;
  ;WHILE TRUE DO
con_begin:
  CALL open_socket     ;Connecting communication 
  IF sock_id < 0    THEN 
	IF stopCon == TRUE THEN
		GOTO exit
	END
    GOTO  con_begin 
  END
  PRINT  "Connection established"
cyc_begin:
  IF stopCon == TRUE THEN
    GOTO exit
  END
  tout_rec = 5
  CALL recv        ;Receiving the result of processing 1 
  IF rret == -34024  THEN 
    PRINT  "Recieve timeout" 
    GOTO  cyc_begin 
  END
  IF rret == -34025  THEN 
    PRINT  "Connection error" 
    CALL close_socket
    GOTO  con_begin 
  END
  IF rret == 0 THEN
    
    PRINT $recv_buf[1]
    $drink = ""
    IF $recv_buf[1] == "Cappuccino" THEN
		$drink = $recv_buf[1]
    END
  
	IF $recv_buf[1] == "Espresso" THEN
		$drink = $recv_buf[1]
    END
	
	IF $recv_buf[1] == "Americano" THEN
		$drink = $recv_buf[1]
    END
	
	IF $recv_buf[1] == "Latte" THEN
		$drink = $recv_buf[1]
    END
  
	IF $drink !+ "" THEN
		MC EXECUTE coffeeroboth1
		MC EXECUTE coffeeroboth2
	END
  END
  
  GOTO cyc_begin
    ;END    
exit: 
    CALL    close_socket   ;Closing communication 
exit_end:
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
.PROGRAM recv() ;Communication  Receiving data 
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
.PROGRAM send(.ret,.$data)    ;Communication  Sending data 
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

