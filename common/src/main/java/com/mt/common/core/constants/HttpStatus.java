package com.mt.common.core.constants;
/*
 *
 * */
public class HttpStatus {
    /**
     * 操作成功
     */
    public static final Long SUCCESS = 200L;

    /**
     * 对象创建成功
     */
    public static final Long CREATED = 201L;

    /**
     * 请求已经被接受
     */
    public static final Long ACCEPTED = 202L;

    /**
     * 操作已经执行成功，但是没有返回数据
     */
    public static final Long NO_CONTENT = 204L;

    /**
     * 资源已被移除
     */
    public static final Long MOVED_PERM = 301L;

    /**
     * 重定向
     */
    public static final Long SEE_OTHER = 303L;

    /**
     * 资源没有被修改
     */
    public static final Long NOT_MODIFIED = 304L;

    /**
     * 参数列表错误（缺少，格式不匹配）
     */
    public static final Long BAD_REQUEST = 400L;

    /**
     * 未授权
     */
    public static final Long UNAUTHORIZED = 401L;

    /**
     * 访问受限，授权过期
     */
    public static final Long FORBIDDEN = 403L;

    /**
     * 资源，服务未找到
     */
    public static final Long NOT_FOUND = 404L;

    /**
     * 不允许的http方法
     */
    public static final Long BAD_METHOD = 405L;

    /**
     * 资源冲突，或者资源被锁
     */
    public static final Long CONFLICT = 409L;

    /**
     * 不支持的数据，媒体类型
     */
    public static final Long UNSUPPORTED_TYPE = 415L;

    /**
     * 系统内部错误
     */
    public static final Long ERROR = 500L;

    /**
     * 接口未实现
     */
    public static final Long NOT_IMPLEMENTED = 501L;
}

