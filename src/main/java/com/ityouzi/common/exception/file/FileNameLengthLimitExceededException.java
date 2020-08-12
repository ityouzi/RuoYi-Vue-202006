package com.ityouzi.common.exception.file;

/**
 * @Auther: lizhen
 * @Date: 2020-06-22 16:28
 * @Description: 文件名称超长限制异常类
 */
public class FileNameLengthLimitExceededException extends FileException {
    private static final long serialVerSionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super("pload.filename.exceed.length", new Object[]{defaultFileNameLength});
    }
}
