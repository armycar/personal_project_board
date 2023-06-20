package com.example.mini_project_cm.vo.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public interface ArticleReadVO {
    public String getAiTitle();
    public String getMiNickname();
    public Date getAiRegDt();
    public String getAcName();
    public Integer getAiView();
    public Integer getComment();
    public Integer getRecommend();
    public Long getAiSeq();
}
