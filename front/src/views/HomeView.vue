<template>
<div>
  <b-navbar toggleable="lg" type="dark" variant="info">
    <b-navbar-brand href="/">Board</b-navbar-brand>

<b-navbar-nav>
        <a v-if="isLoggedIn" class="username">{{ username }}님 안녕하세요</a>
      </b-navbar-nav>

        <b-navbar-nav style="margin-left: auto;">
          <b-nav-form >
            <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>
            <b-button size="sm" class="my-2 my-sm-0" type="submit" >Search</b-button>
          </b-nav-form>
      </b-navbar-nav>


  </b-navbar>
</div>

  <div class="top-nav">
    <router-link v-if="isLoggedIn" to="/mypage" class="mypage_btn">마이페이지</router-link>
    <a v-if="isLoggedIn" href="#" class="logout_btn" @click="logout">로그아웃</a>
    <a v-if="!isLoggedIn" href="/api/member/join" class="join_btn">회원가입</a>
    <a v-if="!isLoggedIn" href="/api/member/login" class="login_btn">로그인</a>
  </div>

  <el-table @row-click="rowClicked" :data="articles" style="width: 100%">
    <el-table-column prop="aiSeq" label="번호" align="center"></el-table-column>
    <!-- <el-table-column prop="acName" label="카테고리" align="center"></el-table-column> -->
    <el-table-column prop="aiTitle" label="제목" align="center"></el-table-column>
    <el-table-column prop="miNickname" label="작성자" align="center"></el-table-column>
    <!-- <el-table-column prop="comment" label="댓글수" align="center"></el-table-column> -->
    <el-table-column prop="aiRegDt" label="작성일" align="center"></el-table-column>
    <el-table-column prop="aiView" label="조회수" align="center"></el-table-column>
    <el-table-column prop="recommend" label="추천수" align="center"></el-table-column>
  </el-table>

  <a href="/api/article/write" class="write_btn">
    <span class="write_text">글쓰기</span>
  </a>
  

  <div class="pagination-container">
  <el-pagination
    @current-change="handlePageChange"
    :current-page="page"
    :page-size="5"
    :total="totalPages * 5"
    layout="prev, pager, next"

  ></el-pagination>
  </div>
</template>

<script>
import moment from 'moment';
import apiBoard from '@/api/board';

export default {
  data() {
    return {
      articles: null,
      page: 1,
      totalPages: 0
    };
  },
  computed: {
    isLoggedIn() {
      return !!sessionStorage.getItem('token');
    },
    username() {
      return sessionStorage.getItem('username');
    },
    getPagerCount() {
      return Math.ceil(this.totalPages / 5);
    }
  },
  mounted() {
    this.loadArticles();
  },
  methods: {
    loadArticles() {
      const type = 'all';
      const size = 5;

      apiBoard
        .getArticles(type, this.page-1, size)
        .then((response) => {
          console.log('getArticles', response);
          this.articles = response.data.content.map((article) => {
            article.aiRegDt = moment(article.aiRegDt).format('YYYY-MM-DD HH:mm:ss');
            return article;
          });
          const totalCount = response.data.totalElements;
          this.totalPages = Math.ceil(totalCount / size);

          console.log(totalCount)
          console.log(this.page)
        })
        .catch((e) => {
          console.log(e);
        });
    },
    rowClicked(row) {
      this.$router.push({
        path: `/api/article/detail/${row.aiSeq}`
      });
    },
    logout() {
      sessionStorage.removeItem('token');
      this.$router.go();
      this.$message({
        message: '로그아웃 되었습니다',
        type: 'success'
      });
    },
    handlePageChange(newPage) {
      this.page = newPage;
      this.loadArticles();
    },
  }
};
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
  bottom: 56%;
  right: 2%;
  width: 88px;
  height: 35px;
  border-radius: 50px;
  background: #fc1f49;
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.05);
  z-index: 10;
  padding: 1px 30px;
}

.write_text {
  position: absolute;
  font-size: 14px;
  color: #fff;
  text-align: center;
  width: 60px;
  left: 50%;
  top: 10px;
  transform: translateX(-50%);
}

.write_btn img {
  position: relative;
  top: 50%;
  left: 2%;
  transform: translate(-50%, -55%);
}

.username {
  margin-right: 16px;
  padding: 8px 16px;
  font-size: 14px;
  text-decoration: none;
  cursor: pointer;
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

.pagination-container {
  display: flex;
  justify-content: center;
  margin: 20px 0;
}

.b-navbar-brand,
.b-nav-form,
.b-button {
  font-size: 28px;
  color: #fff;
}
</style>
