<template>
<b-navbar toggleable="lg" type="dark" variant="info">
    <b-navbar-brand href="/">Board</b-navbar-brand>
<b-navbar-nav>
        <a v-if="isLoggedIn" class="username">{{ username }}님 안녕하세요</a>
      </b-navbar-nav>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
    </b-collapse>
  </b-navbar>
  <br><br>
<span style="font-size: 18px">나의활동</span> 
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
        <el-tab-pane label="스크랩" name="scraps">
            <el-table @row-click="rowClickedScrap" v-if="activeTab" :data="scraps" style="width: 100%">
                <el-table-column prop="aiTitle" label="제목" align="center"></el-table-column>
                <el-table-column prop="aiRegDt" label="날짜" align="center"></el-table-column>
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
            scraps: null,
            activeTab: 'articles'
        }
    },
    computed: {
         isLoggedIn() {
      return !!sessionStorage.getItem('token');
    },
    username() {
      return sessionStorage.getItem('username');
    }
    },
    mounted() {
        this.seq = sessionStorage.getItem('token');
        this.loadArticleInfo();
        this.loadCommentInfo();
        this.loadScrapInfo();
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
        loadScrapInfo() {
            apiMember.getMemberScrap(this.seq)
            .then(response => {
                this.scraps = response.data.articles.map((scrap) => {
                    scrap.aiRegDt = moment(scrap.aiRegDt).format('YYYY-MM-DD HH:mm:ss');
                    scrap.aiTitle
                    return scrap;
                });

                this.scraps.sort((a, b) => new Date(b.aiRegDt) - new Date(a.aiRegDt));
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
        },
        rowClickedScrap(row) {
            this.$router.push({
                path: `/api/article/detail/${row.aiSeq}`
            });
        }
    }
};
</script>
<style scoped>
.username {
  margin-right: 16px;
  padding: 8px 16px;
  font-size: 14px;
  text-decoration: none;
  cursor: pointer;
}
</style>
