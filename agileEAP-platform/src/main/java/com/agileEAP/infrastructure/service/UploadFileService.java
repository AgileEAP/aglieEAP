package  com.agileEAP.infrastructure.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agileEAP.data.PageDataResult;
import com.agileEAP.infrastructure.entity.UploadFile;
import com.agileEAP.infrastructure.repository.UploadFileRepository;
import com.agileEAP.utils.*;

/**
* 上传文件Service
*
* @author trh
*/
@Component
@Transactional
public class UploadFileService {
    @Autowired
    private UploadFileRepository uploadFileRepository;

    public UploadFile get(String id) {
        return uploadFileRepository.get(id);
    }

    public List<UploadFile> search(Map<String, Object> parameters) {
        return uploadFileRepository.search(parameters);
    }

    public PageDataResult searchByPage(Map<String, Object> parameters) {
        PageDataResult pageDataResult=new PageDataResult();
        pageDataResult.setTotal(uploadFileRepository.count(parameters));
        pageDataResult.setData(uploadFileRepository.searchByPage(parameters));

        return pageDataResult;
    }

    public void update(UploadFile uploadFile) {
        uploadFileRepository.update(uploadFile);
    }

    public void save(UploadFile uploadFile) {
        uploadFileRepository.save(uploadFile);
    }

    public void delete(String id) {
        uploadFileRepository.delete(id);
    }

    public void batchDelete(List<String> ids) {
        uploadFileRepository.batchDelete(ids);
    }	
}
