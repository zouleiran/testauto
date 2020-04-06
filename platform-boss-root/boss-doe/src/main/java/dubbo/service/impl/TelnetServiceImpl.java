package dubbo.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import dubbo.dto.ConnectDTO;
import dubbo.dto.ResultDTO;
import dubbo.model.PointModel;
import dubbo.service.TelnetService;
import dubbo.util.ParamUtil;
import dubbo.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.apache.commons.net.telnet.TelnetClient;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;

/**
 * Created by admin on 2018/12/5.
 */
@Service
public class TelnetServiceImpl implements TelnetService {

    private static final Logger log = LoggerFactory.getLogger(TelnetServiceImpl.class);




    @Override
    public ResultDTO<Object> send(ConnectDTO dto) {
        //分开ip和端口
        PointModel model = ParamUtil.parsePointModel(dto.getConn());

        TelnetClient telnetClient = null;
        try {
            telnetClient = new TelnetClient("VT220");  // 指明Telnet终端类型，否则会返回来的数据中文会乱码
            telnetClient.setDefaultTimeout(dto.getTimeout() <= 0 ? 50000 : dto.getTimeout()); // socket延迟时间：5000ms
            telnetClient.connect(model.getIp(), model.getPort());  // 建立一个连接,默认端口是23
            InputStream in = telnetClient.getInputStream(); // 读取命令的流
            PrintStream out = new PrintStream(telnetClient.getOutputStream());  // 写命令的流

            //发送命令 invoke
            String command = makeCommand(dto.getServiceName(), dto.getMethodName(), dto.getJson());

            log.info("send: {}", command);

            out.println("\r\n");
            out.println(command);
            out.println("\r\n");
            out.flush();

            // handle inputStream
            StringBuilder sb = new StringBuilder();
            BufferedInputStream bi = new BufferedInputStream(in);

            while (true) {
                byte[] buffer = new byte[1024];
                int len = bi.read(buffer);
                if (len <= -1) {
                    break;
                }

                String msg = new String(buffer, 0, len, "UTF-8");
                sb.append(msg);
                if (msg.endsWith("dubbo>")) {
                    break;
                }
            }
            out.println("exit"); // 写命令
            out.flush(); // 将命令发送到telnet Server
            telnetClient.disconnect();

            String ret = sb.toString();
            String lineSeparator = System.getProperty("line.separator", "\n");
            if (StringUtils.isNotEmpty(ret)) {
                ret = ret.split(lineSeparator)[0];
            }
            log.info("receive: {}", ret);
            // format the json string
            Map jsStr =  JSON.parseObject(ret) ;
            String result = JSON.toJSONString(JSON.parse(ret), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
            System.out.println(result);
            System.out.println(jsStr);
            return ResultDTO.createSuccessResult("Success", jsStr,Object.class);

        } catch (Exception e) {
            log.error("occur an error when sending message with telnet client.", e);
            return ResultDTO.createExceptionResult(e, Object.class);
        } finally {
            try {
                if (null != telnetClient) {
                    telnetClient.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String makeCommand(String serviceName, String methodName, String json) {

        return StringUtil.format("invoke {}.{}({})", serviceName, methodName, json == null ?"":json);
    }

    public static void main(String[] args) {
        ConnectDTO dto = new ConnectDTO();
        dto.setConn("192.168.2.71:18289");
        dto.setServiceName("com.zhipin.passport.api.PassportApi");
        dto.setJson("\"12\"");
        dto.setMethodName("getPassportByTicket");

        TelnetServiceImpl test = new TelnetServiceImpl();
        test.send(dto);

    }


}
