package jj.fly.user.thrift;

import com.jj.fly.thrift.message.MessageService;
import com.jj.fly.thrift.user.UserService;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author: jiangjun
 * Date: 2018/9/25
 * Time: 上午11:11
 * Description:
 */
@Component
public class ServiceProvider {

    @Value("${thrift.user.ip}")
    private String serverIp;

    @Value("${thrift.user.port}")
    private Integer serverPort;

    @Value("${thrift.message.ip}")
    private String msgServerIp;

    @Value("${thrift.message.port}")
    private Integer msgServerPort;

    private enum  ServiceType{
        USER,
        MESSAGE,
        ;
    }

    public UserService.Client getUserService(){

        return getService(serverIp,serverPort, ServiceType.USER);
    }

    public MessageService.Client getMessageService(){
        return getService(msgServerIp,msgServerPort, ServiceType.MESSAGE);

    }

    public <T> T getService(String ip, Integer port, ServiceType type){
        TSocket socket = new TSocket(ip, port, 3000);
        TTransport tTransport = new TFramedTransport(socket);
        try {
            tTransport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
        TProtocol protocol = new TBinaryProtocol(tTransport);
        TServiceClient client = null;
        switch (type){
            case USER:
                client = new UserService.Client(protocol);
                break;
            case MESSAGE:
                client = new MessageService.Client(protocol);
        }
        return (T) client;
    }

}
