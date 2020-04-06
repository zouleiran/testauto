package cn.boss.platform.service.tools;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
@Service
public class RedisService {
         public static void main(String[] args) {
                 Jedis jedis = new Jedis("192.168.1.26", 6479);
                 String hello = jedis.get("zb_qa:b_extra_6461447");
//                 jedis.del("zp_geek_qa:GEXTK_1061831768");
                 System.out.println(hello);
                 jedis.close();
             }
    public void delkey(String env,String key){
             String ip=null;
             int port=0;
             if(env.equals("QA"))
             {
                 ip="192.168.1.26";
                 port=6479;
             }
             else if(env.equals("RD"))
            {
                ip="192.168.1.31";
                port=6479;
            }
             Jedis jedis = new Jedis(ip, port);
             jedis.del(key);
             jedis.close();
    }
    public String getkey(String env,String key){
        String ip=null;
        int port=0;
        if(env.equals("QA"))
        {
            ip="192.168.1.26";
            port=6479;
        }
        else if(env.equals("RD"))
        {
            ip="192.168.1.31";
            port=6479;
        }
        Jedis jedis = new Jedis(ip, port);
        String result=jedis.get(key);
        jedis.close();
        return result;
    }
}
