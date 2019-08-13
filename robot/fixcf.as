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
CALL tcp_recv;Receiving the result of processing 1 
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
TWAIT 4
eret = 0
$sdata[1] = "OK\n"
CALL tcp_send(eret,$sdata[1])
END
GOTO cyc_begin
;END    
exit:
CALL close_socket;Closing communication 
exit_end:
.END
