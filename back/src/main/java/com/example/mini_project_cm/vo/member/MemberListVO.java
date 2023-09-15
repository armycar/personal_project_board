package com.example.mini_project_cm.vo.member;

import java.util.Date;

public interface MemberListVO {
    public Long getMiSeq();
    public String getMiEmail();
    public String getMiNickName();
    public Integer getMiStatus();
    public Date getMiRegDt();
    public Date getMiLeaveDt();
}
