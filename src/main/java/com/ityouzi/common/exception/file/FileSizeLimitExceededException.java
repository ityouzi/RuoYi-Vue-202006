package com.ityouzi.common.exception.file;

/**
 * @Auther: lizhen
 * @Date: 2020-06-22 16:25
 * @Description: 文件大小限制异常类
 */
public class FileSizeLimitExceededException extends FileException{
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize", new Object[] {defaultMaxSize});
    }
}
