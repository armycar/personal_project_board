<template>
<div class="top-nav">
<a v-if="isLoggedIn" class="username">{{ username }}님 안녕하세요</a>
<router-link v-if="isLoggedIn" to="/mypage" class="mypage_btn">마이페이지</router-link>
<a v-if="isLoggedIn" href="#" class="logout_btn" @click="logout">로그아웃</a>
<a v-if="!isLoggedIn" href="/api/member/join" class="join_btn">회원가입</a> 
<a v-if="!isLoggedIn" href="/api/member/login " class="login_btn">로그인</a>
</div>

 <el-table @row-click="rowClicked" :data="articles" style ="width: 100%" >
<el-table-column prop="aiSeq" label="번호" align="center"></el-table-column>
<!-- <el-table-column prop="acName" label="카테고리" align="center"></el-table-column> -->
<el-table-column prop="aiTitle" label="제목" align="center"></el-table-column>
<el-table-column prop="miNickname" label="작성자" align="center"></el-table-column>
<!-- <el-table-column prop="comment" label="댓글수" align="center"></el-table-column> -->
<!-- <el-table-column prop="recommend" label="추천수" align="center"></el-table-column> -->
<el-table-column prop="aiRegDt" label="작성일" align="center"></el-table-column>
<el-table-column prop="aiView" label="조회수" align="center"></el-table-column>
</el-table>
<a href="/api/article/write" class="write_btn">
<img src="/images/write.png">
</a>
</template>

<script>
import moment from 'moment';
import apiBoard from '@/api/board';

export default {
  data() {
    return {
      articles : null,
      page: 0,
    };
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
    apiBoard.getArticles("all")
    .then((response) => {
      console.log("getArticles", response);
       this.articles = response.data.map(article => {
        article.aiRegDt = moment(article.aiRegDt).format('YYYY-MM-DD HH:mm:ss');
        return article;
      });
    })
    .catch((e) => {
      console.log(e);
    });
  },

  methods: {
    rowClicked(row) {
      this.$router.push({
        path: `/api/article/detail/${row.aiSeq}`,
      })
    },
    logout() {
      sessionStorage.removeItem('token');
      this.$router.go();
      this.$message({
        message: '로그아웃 되었습니다',
        type: 'success'
      });
    }
  }
}
</script>

<style scoped>
.top-nav {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 16px;
}

.write_btn {
    position: fixed;
    bottom: 40px;
    right: 40px;
    width: 48px;
    height: 48px;
    border-radius: 50px;
    background: #fc1f49;
    box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.05);
    z-index: 10;
    display: inline-block;
}
.write_btn img {
    position: relative;
    top: 50%;
    left: 25%;
    transform: translate(-50%, -55%);
}

.username {
  position: fixed;
  top: 20px;
  right: 50px; /* 로그아웃 버튼 너비(64px) + 간격(4px) */
  z-index: 10;
  display: inline-block;
}
.login_btn,
.join_btn,
.logout_btn,
.mypage_btn {
  margin-right: 16px;
  padding: 8px 16px;
  border-radius: 24px;
  background-color: #911ffc;
  color: #fff;
  font-size: 14px;
  text-decoration: none;
  cursor: pointer;
}
</style>
