package com.hw.springbootrestful.dto.code;

/**
 * @Description
 * @Author hw
 * @Date 2018/6/5 0005
 */
public class BaseRestCode {
    /**
     * 100200  请求成功
     */
    public static final Integer REST_OK = 100200;                 //正确的请求返回正确的结果，如果不想细分正确的请求结果都可以直接返回200。
    public static final Integer REST_OK_CREATED = 100201;         //表示资源被正确的创建。比如说，我们 POST 用户名、密码正确创建了一个用户就可以返回 201。
    public static final Integer REST_OK_ACCEPTED = 100202;        // 请求是正确的，但是结果正在处理中，没法返回对应的结果。比如说，我们请求一个需要大量计算的结果，但是并没有计算结束时，可以返回这个，这时候客户端可以通过轮询等机制继续请求。
    /**
     * 100300  重定向
     */
    public static final Integer REST_REDIRECT_MULTIPLE_CHOICES = 100300;         // 请求成功，但结果有多种选择。比如说，我们下载一部片，服务器有 avi、mp4 等格式，这时候可以返回 300，并在 body 里告知有哪些格式，然后用户可以根据这些格式再次请求。
    public static final Integer REST_REDIRECT_MOVED_PERMANENTLY = 100301;        // 请求成功，但是资源被永久转移。对迁移的API，返回 301重定向。
    public static final Integer REST_REDIRECT_FOUND = 100302;                    // 请求成功，但是资源被临时转移了。和 301 不同的是，除非是 HEAD 请求，否则新地址的信息应当在 body 中返回，并且资源只是临时转移，以后不应当通过新地址来下载。
    public static final Integer REST_REDIRECT_SEE_OTHER = 100303;                // 类似 302，但要求使用 GET 来访问新的地址来获取资源。
    public static final Integer REST_REDIRECT_TEMPORARY_REDIRECT = 100307;       // 类似 302，但要求使用原有的请求方式来通过新地址获取资源。
    /**
     * 100400   客户端错误
     */
    public static final Integer REST_BAD_REQUEST = 100400;                                // 请求出现错误，比如请求头不对等，所有不想明确区分的客户端请求出错都可以返回 400。
    public static final Integer REST_BAD_REQUEST_UNAUTHORIZED = 100401;                   // 没有提供认证信息。比如说，请求的时候没有带上 Token 等。
    public static final Integer REST_BAD_REQUEST_FORBIDDEN = 100403;                      // 请求的资源不允许访问。比如说，你使用普通用户的 Token 去请求管理员才能访问的资源。
    public static final Integer REST_BAD_REQUEST_NOT_FOUND = 100404;                      // 请求的内容不存在。
    public static final Integer REST_BAD_REQUEST_METHOD_NOT_ALLOWED = 100405;             // 请求的方法不允许使用。比如说，服务器只实现了 PATCH 了局部更新资源，并没有实现 PUT 来替换资源，而我们使用了 PUT，这时候服务器可以返回 405 来告知并没有实现对 PUT 的相关处理。
    public static final Integer REST_BAD_REQUEST_NOT_ACCEPTABLE = 100406;                 // 请求的资源并不符合要求。比如说，我们 header 里请求 JSON 格式的数据，但是服务器只有 XML 格式的数据，这时候可以返回 406 告知。
    public static final Integer REST_BAD_REQUEST_REQUEST_TIMEOUT = 100408;                // 客户端请求超时。我们想 POST 创建一个用户，虽然建立了连接，但是网络不好，服务器在规定时间内没有得到我们的请求信息，这时候服务器可以返回 408 告诉我们超时了。然后我们可以重新发送请求。
    public static final Integer REST_BAD_REQUEST_CONFLICT = 100409;                       // 请求冲突。比如说，服务器要求不同用户不能重名，服务器已经有了一个名叫小伟的用户，这时候我们又想创建一个名叫小伟的用户，服务器可以返回 409，告诉我们冲突了，也可以在 body 中明确告知是什么冲突了。
    public static final Integer REST_BAD_REQUEST_REQUEST_ENTITY_TOO_LARGE = 100413;       // 请求体过大。比如说，服务器要求上传文件不能超过 5M，但是我们 POST 了 10M，这时候就返回 413。
    public static final Integer REST_BAD_REQUEST_REQUEST_URI_TOO_LONG = 100414;           // 请求的 URI 太长了。比如说，我们提供了太多的 Query 参数，以至于超过了服务器的限制，这时候可以返回 414。
    public static final Integer REST_BAD_REQUEST_UNSUPPORTED_MEDIA_TYPE = 100415;         // 不支持的媒体类型。比如说，我们上传了一张七娃的 GIF 动图，而服务器只允许你上传 PNG 图片，这时候就返回 415。
    /**
     * 100500   服务器错误
     */
    public static final Integer REST_SERVER_ERROR = 100500;                                   // 服务器错误。没法明确定义的服务器错误都可以返回这个。
    public static final Integer REST_SERVER_ERROR_NOT_IMPLEMENTED = 100501;                   // 请求还没有被实现。比如说，我们请求一个接口来自动拒绝项目经理的要求，但是这个接口只是美好的想象，并没有被实现，这时候可以返回 501。
    public static final Integer REST_SERVER_ERROR_BAD_GATEWAY = 100502;                       // 网关错误。比如说，我们向服务器 A 请求下载葫芦娃，但是 A 其实只是一个代理服务器，他得向 B 请求葫芦娃，但是不知道为啥 B 不理他或者给他错误，这时候哦可以 A 返回 502 用来表示 B 这家伙傲娇了。
    public static final Integer REST_SERVER_ERROR_SERVICE_UNAVAILABLE = 100503;               // 服务暂时不可用。比如说，服务器正好在更新代码重启。
    public static final Integer REST_SERVER_ERROR_GATEWAY_TIMEOUT = 100504;                   // 类似 502，但是这时候是 B 不理 A，超时了 。
    public static final Integer REST_SERVER_ERROR_HTTP_VERSION_NOT_SUPPORTED = 100505;        // 请求的 HTTP 版本不支持。比如说，现在强行根据 HTTP 1000 来请求。


}
