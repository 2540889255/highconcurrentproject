package com.aynu.project.highconcurrentproject.controller;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @Auther: LC
 * @Date : 2021 03 21 15:42
 * @Description : com.aynu.redis.controller
 * @Version 1.0
 * 对于客户端的请求，对于相同资源的请求，当被请求的资源未进行修改，则返回未修改的响应状态码，客户端从本地缓存中读取，
 * 对于hash值的计算，使用请求文件+修改时间 生成hash状态码
 */
@RestController
@RequestMapping("/cache")
public class CacheController {




    private MyFile file= MyFile.getInstance();

    @RequestMapping("/cacheControl")
    public ResponseEntity<String> lastCacheControl(@RequestHeader(value = "IF-Modified-Since", required = false) String ifmodifiedTime) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);

        //系统时间
        long end = System.currentTimeMillis() / 1000 * 1000;

        //缓存时间
        long maxAge = 20;

        HttpHeaders httpHeaders = new HttpHeaders();
        System.out.println(file.getModifiedTime());


        System.out.println(simpleDateFormat.format(new Date(file.getModifiedTime())));
        if (simpleDateFormat.format(new Date(file.getModifiedTime())).equals(ifmodifiedTime)){
            System.out.println("304");
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        httpHeaders.set("Date", simpleDateFormat.format(new Date(end)));

        //设置缓存的过期时间
        httpHeaders.set("Expires", simpleDateFormat.format(new Date(end + maxAge * 10)));

        httpHeaders.set("Cache-Control","max-ages="+maxAge);

        httpHeaders.add("Last-Modified",simpleDateFormat.format(new Date(file.getModifiedTime())));
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }



    @RequestMapping("/")
    public ResponseEntity<String> last(@RequestHeader(value = "If-None-Match", required = false) String ifModified) {

        System.out.println("ifmodified " + ifModified);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);

        long end = System.currentTimeMillis() / 1000 * 1000;

        HttpHeaders httpHeaders = new HttpHeaders();

        //设置实体baody的md5值
        String body = "<h1> hello</h1>";

        String Etag = getMd5(body);
        //判断是否为相同的
        if (Etag.equals(ifModified)) {
            System.out.println("1");
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }


        System.out.println("ETag " + Etag);

        //httpHeaders.add("Date",simpleDateFormat.format(new Date(end)));
        httpHeaders.add("Last-Modified", "Mon, 22 Mar 2021 15:06:32 GMT");

        httpHeaders.add("ETag", Etag);


        return new ResponseEntity<>(body, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping("/2")
    protected void doGets(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 这里的时间是从1970开始，所以要设置当前时间+缓存的时间,时间单位为毫秒，这里缓存的时间为15秒
        response.setDateHeader("expires", System.currentTimeMillis() + 1000 * 15);
        // 模拟的大数据
        String bigData = "this is a big data,but it is not change. " + (new Date().toString());
        response.getWriter().print(bigData);
    }


    /**
     * 字符串转MD5
     *
     * @param body
     * @return
     */
    public String getMd5(String body) {
        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] bytes = body.getBytes();
        //使用指定的字节数组更新摘要
        digest.update(bytes);
        //通过添加诸如填充之类的最终操作完成hash计算，在进行digest计算值后，摘要将要被删除
        byte[] resultdigest = digest.digest();//digest消化的意思

        //
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < resultdigest.length; i++) {

            if (((int) resultdigest[i] & 0xff) < 0x10) {  //(int)resultdigest[i]&0xff转换成无符号整形
                stringBuilder.append("0");
            }
            stringBuilder.append(Long.toHexString((int) resultdigest[i] & 0xff));


        }
        return stringBuilder.toString();
    }


    /**
     *
     **/
    @Test
    public void test() {
        String body = "<h1> hello</h1>";
        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] bytes = body.getBytes();
        //使用指定的字节数组更新摘要
        digest.update(bytes);
        //通过添加诸如填充之类的最终操作完成hash计算，在进行digest计算值后，摘要将要被删除
        byte[] resultdigest = digest.digest();//digest消化的意思

        //
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < resultdigest.length; i++) {

            System.out.println("digest : " + ((int) resultdigest[i] & 0xff) + "  number : " + Long.toHexString((int) resultdigest[i] & 0xff));

            stringBuilder.append(Long.toHexString((int) resultdigest[i] & 1));


        }
    }


    @Test
    public void test2() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE ,d MMM yyyy HH:mm:ss ");
        long start = System.currentTimeMillis();
        System.out.println(start);
        System.out.println(simpleDateFormat.format(start));

        long end = System.currentTimeMillis() / 1000 * 1000;
        System.out.println(end);
        System.out.println(simpleDateFormat.format(end));

    }

    @Test
    public void testloadCache() throws ExecutionException {

        //类似于一个ConcurrentHashMap
        LoadingCache<Long, AtomicLong> cache = CacheBuilder.newBuilder()
                //设置失效时间，两秒后cache里的元素就会进行一次清空
                .expireAfterWrite(2L, TimeUnit.SECONDS)
                .build(new CacheLoader<Long, AtomicLong>() {

                    @Override
                    public AtomicLong load(Long secend) throws Exception {
                        return new AtomicLong(0);
                    }
                });
        long max=100;
        if (cache.get(1L).incrementAndGet()> max){
            //当连接数大于max最大值，自定义拒绝策略
        }else {
            //放行
        }
    }


    //测试令牌桶算法
    @Test
    public void test3(){
        //每秒产生的令牌数
        RateLimiter limiter= RateLimiter.create(2);

        //limitter.acquire阻塞方法
        System.out.println(limiter.acquire());

        limiter.tryAcquire(20,Duration.ofSeconds(2000));


    }
    static class MyFile{

        static {
            System.out.println("MyFile静态类已经创建");
        }
        private static volatile MyFile myFile;

        public static MyFile getInstance(){
            if (myFile==null){
                synchronized (MyFile.class){
                    if (myFile==null){
                        return new MyFile();
                    }
                }
            }
            return myFile;
        }

        public long getModifiedTime(){
            long end = System.currentTimeMillis();

            return 1616497147016L;
        }
    }


}
