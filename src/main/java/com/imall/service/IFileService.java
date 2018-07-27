package com.imall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * author:  ywy
 * date:  2018-07-26
 * desc:
 *
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}