package dubbo.service;

import dubbo.dto.ConnectDTO;
import dubbo.dto.ResultDTO;

/**
 * Created by admin on 2018/12/5.
 */
public interface TelnetService {

    /**
     * send message with telnet client.
     * @param dto
     * @return
     */
    ResultDTO<Object> send(ConnectDTO dto);
}
