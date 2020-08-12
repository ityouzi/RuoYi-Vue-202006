package com.ityouzi.common.exception.file;

import com.ityouzi.common.exception.BaseException;

/**
 * @Auther: lizhen
 * @Date: 2020-06-22 16:25
 * @Description: 文件信息异常类
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException( String code, Object[] args) {
        super("file", code, args, null);
    }
}
