package com.chinhnd.recruit.service;

import com.chinhnd.recruit.dto.JobDTO;
import com.chinhnd.recruit.dto.ReasonDTO;
import com.chinhnd.recruit.dto.StatusJobDTO;
import com.chinhnd.recruit.entity.*;
import com.chinhnd.recruit.filter.JobFilter;
import com.chinhnd.recruit.web.vm.JobFieldVM;
import com.chinhnd.recruit.web.vm.PageVM;

import com.chinhnd.recruit.entity.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface JobService {

    List<Job> getAllJob();

    Optional<Job> findById(Long id);

    Job addJob(JobDTO jobDTO);

    void deleteById(Long id);

    /*
    *chinhnd
     */

    int countJobPublished();

    int countAllJobDueSoon();

    int countViewjob();

    int countJobNeedManStepMonth(int month);

    Job updateById(JobDTO jobDTO);

    Job updateStatus(StatusJobDTO statusJobDTO);

    Job updateReason(ReasonDTO reasonDTO);

    Job findJobByName(String jobName);

    JobFieldVM getFieldSelect();

    Page<JobDTO> getAllJobs(PageVM pageVM, String search, String sortBy);

    void updateViewBy(Long id);

    List<AcademicLevel> getAllAcademiclevel();
    List<JobPosition> getAllJobPosition();
    List<Rank> getAllRank();
    List<StatusJob> getAllAStatusJobs();
    List<WorkingForm> getAllWorkingform();

    Page<JobDTO> getAllJob(PageVM pageVM, String search, String sortBy, JobFilter jobFilter);
}
