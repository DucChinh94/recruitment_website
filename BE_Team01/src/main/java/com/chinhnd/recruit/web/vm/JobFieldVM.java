package com.chinhnd.recruit.web.vm;

import com.chinhnd.recruit.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JobFieldVM {
    List<JobPosition> jobPositionList;
    List<WorkingForm> workingFormList;
    List<AcademicLevel> academicLevelList;
    List<Rank> rankList;
    List<User> userList;
    List<StatusJob> statusJobs;
}
