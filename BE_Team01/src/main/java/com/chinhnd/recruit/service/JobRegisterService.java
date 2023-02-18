package com.chinhnd.recruit.service;

import com.chinhnd.recruit.dto.BookInterviewDto;
import com.chinhnd.recruit.dto.JobRegisterDTO;
import com.chinhnd.recruit.entity.JobRegister;
import com.chinhnd.recruit.filter.JobRgfilter;
import com.chinhnd.recruit.web.vm.PageVM;
import com.chinhnd.recruit.dto.StatusJobRegisterDTO;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.Optional;

public interface JobRegisterService {
    Page<JobRegisterDTO> getAllJobRegister(PageVM pageVM, String search, String sortBy, JobRgfilter jobRgfilter);
    Optional<JobRegister> findById(Long id);
    JobRegister addJobRegister(JobRegisterDTO dto);
    void deleteById(Long id);
    JobRegister updateById(JobRegisterDTO jobRegisterDTO);

    JobRegister updateStatusJobRegister(StatusJobRegisterDTO statusJobRegisterDTO);
    JobRegisterDTO save(JobRegisterDTO jobRegisterDTO);

    /*
     * trungnd
     */
    int countAll();

    int countJobRegByStatus(Long statusId,String smallDate, String bigDate);

    int countSuccessfullJobReg(int param);

    JobRegister bookInterView(BookInterviewDto dto) throws ParseException;

}
