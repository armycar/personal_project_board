<template>
<span style="font-size: 18px">작성글 관리</span> 
<br><br>
    <div>
    <el-tabs v-model="activeTab" @tab-click="handleTabClick">
        <el-tab-pane label="게시글" name="articles">
            <el-table @row-click="rowClickedArticle" v-if="activeTab" :data="articles" style="width: 100%">
            <el-table-column prop="aiTitle" label="제목" align="center"></el-table-column>
            <el-table-column prop="aiRegDt" label="날짜" align="center"></el-table-column>
            </el-table>
        </el-tab-pane>
        <el-tab-pane label="댓글" name="comments">
            <el-table @row-click="rowClickedComment" v-if="activeTab" :data="comments" style="width: 100%">
                <el-table-column prop="ciDetail" label="내용" align="center"></el-table-column>
                <el-table-column prop="ciRegDt" label="날짜" align="center"></el-table-column>
            </el-table>
        </el-tab-pane>
    </el-tabs>
    </div>
</template>

<script>
import moment from 'moment';
import apiMember from '@/api/member';
export default {
    data() {
        return {
            seq: '',
            articles: null,
            comments: null,
            activeTab: 'articles'
        }
    },
    mounted() {
        this.seq = sessionStorage.getItem('token');
        this.loadArticleInfo();
        this.loadCommentInfo();
    },
    methods: {
        loadArticleInfo() {
            apiMember.getMemberArticle(this.seq)
            .then(response => {
                this.articles = response.data.articles.map((article) => {
                    article.aiRegDt = moment(article.aiRegDt).format('YYYY-MM-DD HH:mm:ss');
                    article.aiTitle
                    return article;
                });

                this.articles.sort((a, b) => new Date(b.aiRegDt) - new Date(a.aiRegDt));
            })
            .catch((e) => {
                console.log(e);
            });
        },
        loadCommentInfo() {
            apiMember.getMemberComment(this.seq)
            .then(response => {
                this.comments = response.data.comments.map((comment) => {
                    comment.ciRegDt = moment(comment.ciRegDt).format('YYYY-MM-DD HH:mm:ss');
                    comment.ciDetail
                    return comment;
                });

                this.comments.sort((a, b) => new Date(b.ciRegDt) - new Date(a.ciRegDt));
            })
            .catch((e) => {
                console.log(e);
            });
        },
        handleTabClick(tab) {
            if(tab.name === 'articles') {
                this.loadArticleInfo();
            } else if(tab.name === 'comments') {
                this.loadCommentInfo();
            }
        },
        rowClickedArticle(row) {
            this.$router.push({
                path: `/api/article/detail/${row.aiSeq}`
            });
        },
        rowClickedComment(row) {
            this.$router.push({
                path: `/api/article/detail/${row.ciAiSeq}`
            });
        }
    }
};
</script>
<style scoped>

</style>
